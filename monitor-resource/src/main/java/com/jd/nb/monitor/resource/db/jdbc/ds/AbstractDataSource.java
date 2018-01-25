package com.jd.nb.monitor.resource.db.jdbc.ds;
import com.jd.nb.monitor.resource.db.jdbc.proxy.ProxyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 抽象的DataSource,负责高层各种DataSource的复用
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
public abstract class AbstractDataSource extends ProxyDataSource {

    /**
     * 目标数据源
     */
    protected DataSource targetDataSource;


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    public DataSource getTargetDataSource() {
        return targetDataSource;
    }

    public void setTargetDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

}
