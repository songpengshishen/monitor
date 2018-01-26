package com.jd.home.framework.monitor.db.jdbc.wrapper;
import com.jd.home.framework.monitor.db.jdbc.MonitorWrapper;
import javax.sql.DataSource;
import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * Title : 监控Wrapper实现
 * Description: 提供JDBC原生方法实现 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorWrapperImpl implements MonitorWrapper{


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
