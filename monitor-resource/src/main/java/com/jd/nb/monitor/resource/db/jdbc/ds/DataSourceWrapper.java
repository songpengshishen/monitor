package com.jd.nb.monitor.resource.db.jdbc.ds;

import com.jd.nb.monitor.resource.db.jdbc.connection.ConnectionWrapper;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource包装类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
public class DataSourceWrapper extends AbstractDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionWrapper(this);
    }



}
