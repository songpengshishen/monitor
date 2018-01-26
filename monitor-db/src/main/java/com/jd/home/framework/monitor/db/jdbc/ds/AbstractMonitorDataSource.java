package com.jd.home.framework.monitor.db.jdbc.ds;
import com.jd.home.framework.monitor.db.config.MonitorConfig;
import com.jd.home.framework.monitor.db.jdbc.MonitorDataSource;
import com.jd.home.framework.monitor.db.jdbc.connection.MonitorConnectionImpl;
import com.jd.home.framework.monitor.db.jdbc.wrapper.MonitorWrapperImpl;
import org.apache.commons.dbcp.BasicDataSource;

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
public abstract class AbstractMonitorDataSource extends MonitorWrapperImpl implements MonitorDataSource {

    /**
     * 目标数据源
     */
    protected DataSource targetDataSource;

    /**
     * 监控配置,没有配置使用默认的
     */
    protected MonitorConfig monitorConfig = new MonitorConfig();


    @Override
    public Connection getConnection() throws SQLException {
        return new MonitorConnectionImpl(targetDataSource.getConnection(),monitorConfig);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new MonitorConnectionImpl(targetDataSource.getConnection(username,password),monitorConfig);
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

    public MonitorConfig getMonitorConfig() {
        return monitorConfig;
    }

    public void setMonitorConfig(MonitorConfig monitorConfig) {
        this.monitorConfig = monitorConfig;
    }

    public DataSource getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
        refRealDataSource();
    }

    /**
     * 引用真实的数据源,子类实现
     */
    protected abstract void refRealDataSource();
}
