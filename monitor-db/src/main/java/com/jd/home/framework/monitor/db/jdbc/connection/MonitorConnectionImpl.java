package com.jd.home.framework.monitor.db.jdbc.connection;

import java.sql.Connection;

/**
 * Title : 监控数据库连接实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorConnectionImpl extends AbstractMonitorConnection {

    public MonitorConnectionImpl(Connection connection){
        this.targetConnection = connection;
    }



}
