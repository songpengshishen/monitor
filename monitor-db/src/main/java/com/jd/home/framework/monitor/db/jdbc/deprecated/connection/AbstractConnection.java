package com.jd.home.framework.monitor.db.jdbc.deprecated.connection;


import com.jd.home.framework.monitor.db.jdbc.deprecated.proxy.ProxyConnection;

import java.sql.Connection;

/**
 * 抽象的Connection
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public abstract class AbstractConnection extends ProxyConnection {

    /**
     * 目标连接对象
     */
    protected Connection targetConnection;


    public Connection getTargetConnection() {
        return targetConnection;
    }

    public void setTargetConnection(Connection targetConnection) {
        this.targetConnection = targetConnection;
    }
}
