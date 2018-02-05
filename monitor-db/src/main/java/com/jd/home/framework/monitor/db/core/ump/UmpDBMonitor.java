package com.jd.home.framework.monitor.db.core.ump;
import com.jd.home.framework.monitor.db.config.MonitorConfig;
import com.jd.home.framework.monitor.db.config.SystemConstans;
import com.jd.home.framework.monitor.db.core.DBMonitor;
import com.jd.home.framework.monitor.db.core.SqlRecord;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Title : 基于UMP的DB监控
 * Description: 监控的指标发送至UMP平台</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public class UmpDBMonitor implements DBMonitor {

    private static Logger logger = LoggerFactory.getLogger(UmpDBMonitor.class);

    /**
     * 监控配置
     */
    private MonitorConfig monitorConfig;

    /**
     * 监控DataSource
     */
    private MonitorDataSource monitorDataSource;

    /**
     * UMP存储
     */
    private UmpStore umpStore = new UmpStore();

    /**
     * 是否已经启动监控连接的任务
     */
    private volatile boolean started = Boolean.FALSE;


    /**
     * JDK任务调度
     */
    private volatile ScheduledExecutorService scheduledExecutorService;


    /**
     * 延迟多久后执行任务,毫秒
     */
    private final long initialDelay = SystemConstans.HALF_HOUR_SECOND;

    /**
     * 执行任务的周期,默认
     */
    private final long period = SystemConstans.FIVE_TEN_SECOND;

    private UmpDBMonitor() {
    }


    public UmpDBMonitor(MonitorDataSource monitorDataSource, MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
        this.monitorDataSource = monitorDataSource;
    }

    @Override
    public SqlRecord SlowSqlMonitorStart(String sql) {
        SqlRecord sqlRecord = new SqlRecord(sql);
        return sqlRecord;
    }

    @Override
    public void SlowSqlMonitorEnd(SqlRecord sqlRecord) {
        if (this.monitorConfig.isSlowSqlEnabled()) {
            Long elapsedTime = sqlRecord.getElapsedTime();
            if (elapsedTime > this.monitorConfig.getSlowSqlTimeout()) {
                String sql = sqlRecord.getSql();
                String info = new StringBuilder("数据库").append(this.monitorDataSource.getUrl()).append("执行过程中出现超时,sql:")
                        .append(sql).toString();
                if (logger.isWarnEnabled()) {
                    logger.warn(info);
                }
                sqlRecord.setProcessState(SqlRecord.STATE_TRUE);

                Ump ump = new Ump();

                sendUmp(this.monitorConfig.getSlowSqlKey(), info);
            }

        }
    }


    /**
     * sql执行错误监控
     * @param sqlRecord 监控的sql记录
     */
    @Override
    public void SqlErrorMonitor(SqlRecord sqlRecord,Throwable t) {
        if(this.monitorConfig.isSqlExceptionEnabled()){
            String sql = sqlRecord.getSql();
            String info = new StringBuilder("数据库").append(this.monitorDataSource.getUrl()).append("执行错误的sql:")
                    .append(sql).append("异常信息:").append(t).toString();
            if (logger.isWarnEnabled()) {
                logger.warn(info);
            }
            sqlRecord.setProcessState(SqlRecord.STATE_FALSE);
            sqlRecord.setThrowable(t);
            sendUmp(this.monitorConfig.getSqlExceptionKey(),info);
        }
    }


    /**
     * 打开活跃连接监控
     */
    @Override
    public void openActiveConnMonitor() {
        if(this.monitorConfig.isTooManyActiveConnEnabled()){
            if (null == monitorDataSource) {
                if (logger.isWarnEnabled()) {
                    logger.warn("No monitorDataSource");
                }
            } else {
                if (!started) {
                    synchronized (this){
                        if (!started) {
                            if (scheduledExecutorService == null) {
                                //创建单线程任务
                                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
                                    @Override
                                    public Thread newThread(Runnable r) {
                                        Thread t =  new Thread(SystemConstans.DB_MONITOR_CONNECTION_THREAD_NAME);
                                        t.setDaemon(true);
                                        t.setPriority(Thread.NORM_PRIORITY);
                                        return t;
                                    }
                                });
                                /*需要应用预热下,半小时在启动监控连接数,每10分钟监听次*/
                                scheduledExecutorService.scheduleWithFixedDelay(new MonitorActiveConnTask(),initialDelay,period, TimeUnit.MILLISECONDS);
                                started = true;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 关闭连接监控
     */
    @Override
    public synchronized void closeActiveConnMonitor() {
        if(!started){
            return;
        }
        try {
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdownNow();
                scheduledExecutorService = null;
            }
        } catch (Exception t) {
            logger.error(t.getMessage(),t);
        } finally {
            started = false;
        }
    }





    /**
     * 发送至UMP平台
     * @param key
     * @param info
     */
    private void sendUmp(String key, String info) {
        Profiler.businessAlarm(key, new Date().getTime(), info);
    }


    /**
     * 监控数据源活跃连接任务
     */
    class MonitorActiveConnTask implements Runnable {
        @Override
        public void run() {
            try {
                MonitorDataSource dataSource = UmpDBMonitor.this.monitorDataSource;
                MonitorConfig config =  UmpDBMonitor.this.monitorConfig;
                final int activeNum = dataSource.getActiveConnections();//获取活跃连接数
                final int maxNum = dataSource.getMaxActiveConnections();//获取最高活跃连接数
                float ratio = (activeNum * 1.0f) / maxNum;
                if (ratio >= config.getMaxActiveConRatio()) {
                    String info = new StringBuilder("数据库").append(dataSource.getUrl()).append("连接数过多，使用率已经超过了")
                    .append(config.getMaxActiveConRatio() * 100).append("%,")
                    .append("当前活跃连接数").append(activeNum).append(",")
                    .append("允许最大活跃连接数").append(maxNum).append(".").toString();
                    if (logger.isWarnEnabled()) {
                        logger.warn(info);
                    }
                    sendUmp(config.getTooManyActiveConnKey(),info);
                }
            }catch (Exception e){
                ;
            }
        }
    }



}
