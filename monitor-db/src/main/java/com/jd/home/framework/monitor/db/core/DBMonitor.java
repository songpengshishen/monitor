package com.jd.home.framework.monitor.db.core;


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
    void SqlErrorMonitor(SqlRecord sqlRecord,Throwable t);

    /**
     * 打开应用对于当前数据源连接数的监控
     */
    void openActiveConnMonitor();

    /**
     * 关闭连接数监控
     */
    void closeActiveConnMonitor();

}
