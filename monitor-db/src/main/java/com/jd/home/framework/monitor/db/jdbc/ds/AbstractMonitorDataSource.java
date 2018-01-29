package com.jd.home.framework.monitor.db.jdbc.ds;
import com.jd.home.framework.monitor.db.config.MonitorConfig;
import com.jd.home.framework.monitor.db.config.SystemConstans;
import com.jd.home.framework.monitor.db.core.DBMonitor;
import com.jd.home.framework.monitor.db.core.UmpDBMonitor;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;
import com.jd.home.framework.monitor.db.jdbc.connection.MonitorConnectionImpl;
import com.jd.home.framework.monitor.db.jdbc.wrapper.MonitorWrapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


/**
 * Title : 抽象的监控数据源实现
 * Description: 通过目标DataSource实现 {@link DataSource}方法</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public abstract class AbstractMonitorDataSource extends MonitorWrapperImpl implements MonitorDataSource,InitializingBean {

    /**
     * 目标数据源
     */
    protected DataSource targetDataSource;


    /**
     * DB监控
     */
    protected DBMonitor dbMonitor;



    /********************配置项Start********************/
    /**
     * SQL执行异常报警开关。
     */
    private boolean sqlExceptionEnabled;

    /**
     * 慢SQL报警开关。
     */
    private boolean slowSqlEnabled;

    /**
     * 连接数过多报警开关。
     */
    private boolean tooManyActiveConnEnabled;

    /**
     * SQL执行异常UMP报警的key。
     */
    private String sqlExceptionKey;

    /**
     * SQL执行异常UMP报警的key。
     */
    private String slowSqlKey;


    /**
     * 连接数过多UMP报警的key。
     */
    private String tooManyActiveConnKey;

    /**
     * 慢SQL执行超时时间，单位是毫秒。
     */
    private long slowSqlTimeout = 1000;


    /**
     * 连接数过多报警。活跃连接占比允许的最大值，超过该值将会报警。
     */
    private float maxActiveConRatio = 0.7f;


    /********************配置项End********************/





    @Override
    public Connection getConnection() throws SQLException {
        return new MonitorConnectionImpl(targetDataSource.getConnection(),dbMonitor);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new MonitorConnectionImpl(targetDataSource.getConnection(username,password),dbMonitor);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return targetDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        targetDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        targetDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return targetDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return targetDataSource.getParentLogger();
    }


    public DataSource getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      init();
    }


    /**
     * 初始化
     */
    public void init(){
        MonitorConfig config = new MonitorConfig(); //创建监控配置
        //设置开关
        config.setSqlExceptionEnabled(this.sqlExceptionEnabled);
        config.setSlowSqlEnabled(this.slowSqlEnabled);
        config.setTooManyActiveConnEnabled(this.tooManyActiveConnEnabled);
        //设置umpKey
        if(StringUtils.isNotBlank(this.sqlExceptionKey)){
            config.setSqlExceptionKey(this.sqlExceptionKey);
        }
        if(StringUtils.isNotBlank(this.slowSqlKey)){
            config.setSlowSqlKey(this.slowSqlKey);
        }
        if(StringUtils.isNotBlank(this.tooManyActiveConnKey)){
            config.setTooManyActiveConnKey(this.tooManyActiveConnKey);
        }
        //指标配置
        if(this.slowSqlTimeout > SystemConstans.MIN_SLOW_SQL_TIMEOUT){
            config.setSlowSqlTimeout(this.slowSqlTimeout);
        }
        if(this.maxActiveConRatio>=SystemConstans.MIN_MAX_ACTIVE_CONNECTION_RATIO){
            if(maxActiveConRatio>SystemConstans.MAX_ACTIVE_CONNECTION_RATIO){
                config.setMaxActiveConRatio(SystemConstans.MAX_ACTIVE_CONNECTION_RATIO);
            }
            else{
                config.setMaxActiveConRatio(this.maxActiveConRatio);
            }
        }
        doProcess();
        dbMonitor = new UmpDBMonitor(this,config);//创建UMPDB监控
    }

    /**
     * 加工处理,交给子类实现
     */
    protected abstract void doProcess();
}
