package com.jd.home.framework.monitor.db.core;
import com.jd.home.framework.monitor.db.config.MonitorConfig;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;
import com.jd.ump.profiler.proxy.Profiler;

import java.util.Date;

/**
 * Title : 基于UMP的DB监控
 * Description: 监控的指标发送至UMP平台</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public class UmpDBMonitor implements DBMonitor{

    /**
     * 监控配置
     */
    private MonitorConfig  monitorConfig;

    /**
     * 监控DataSource
     */
    private MonitorDataSource monitorDataSource;


    public UmpDBMonitor(){}


    public UmpDBMonitor(MonitorDataSource monitorDataSource,MonitorConfig monitorConfig){
          this.monitorConfig= monitorConfig;
          this.monitorDataSource = monitorDataSource;
    }

    @Override
    public SqlRecord SlowSqlMonitorStart(String sql) {
        return null;
    }

    @Override
    public void SlowSqlMonitorEnd(SqlRecord sqlRecord) {

    }

    @Override
    public void SqlErrorMonitor(SqlRecord sqlRecord) {

    }

    @Override
    public void activeConnMonitor() {

    }


    /**
     * 发送至UMP平台
     * @param key
     * @param info
     */
    private void sendUmp(String key,String info){
        Profiler.businessAlarm(key,new Date().getTime(),info);
    }
}
