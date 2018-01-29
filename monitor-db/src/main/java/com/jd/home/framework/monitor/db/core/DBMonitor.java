package com.jd.home.framework.monitor.db.core;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;

/**
 * Title : DB监控接口
 * Description: 主要监控慢sql,异常sql,以及连接数</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public interface DBMonitor {

    /**
     * 慢sql监控开始
     * @param sql 监控的sql语句
     * @return 监控的sql记录
     */
    SqlRecord SlowSqlMonitorStart(String sql);

    /**
     * 慢sql监控结束
     * @param sqlRecord 监控的sql记录
     * @return
     */
    void SlowSqlMonitorEnd(SqlRecord sqlRecord);

    /**
     * 异常的sql监控
     * @param sqlRecord 监控的sql记录
     */
    void SqlErrorMonitor(SqlRecord sqlRecord);

    /**
     * 监控当前应用对于DB的活跃连接数.
     */
    void activeConnMonitor();


}
