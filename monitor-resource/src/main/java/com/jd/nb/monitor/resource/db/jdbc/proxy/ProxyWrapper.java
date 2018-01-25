package com.jd.nb.monitor.resource.db.jdbc.proxy;
import javax.sql.DataSource;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Wrapper;

/**
 * 代理Wrapper抽象类,负责代理jdbc Wrapper接口的缺省方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/25
 */
public class ProxyWrapper implements Wrapper{

    protected Class<DataSource> dataSourceClass = DataSource.class;


    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if(!isDataSource(iface)) {
            throw new SQLDataException("DataSource of type [" + this.getClass().getName() + "] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
        } else {
            return (T) this;
        }
    }


    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return isDataSource(iface);
    }


    private <T>  boolean isDataSource(Class<T> tclass){
        return DataSource.class.equals(dataSourceClass);
    }
}
