package com.jd.home.framework.monitor.db.jdbc.ds;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;
import com.jd.home.framework.monitor.db.jdbc.wrapper.MonitorWrapperImpl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by wangsongpeng on 2018/1/26.
 */
public abstract class AbstractMonitorDataSource extends MonitorWrapperImpl implements MonitorDataSource {


    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
