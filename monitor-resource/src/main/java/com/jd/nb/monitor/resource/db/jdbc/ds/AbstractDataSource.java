package com.jd.nb.monitor.resource.db.jdbc.ds;
import com.jd.nb.monitor.resource.db.jdbc.proxy.ProxyDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 抽象的DataSource,负责高层各种DataSource的复用
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
public abstract class AbstractDataSource extends ProxyDataSource {

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }


}