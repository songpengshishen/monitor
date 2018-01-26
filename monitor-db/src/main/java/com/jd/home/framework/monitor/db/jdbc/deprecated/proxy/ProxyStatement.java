

package com.jd.home.framework.monitor.db.jdbc.deprecated.proxy;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;

/**
 * 代理数据源Statement抽象类,负责代理jdbc Statement接口的缺省方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/25
 */
public abstract class ProxyStatement extends ProxyWrapper implements Statement {
    
    @Override
    public final int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException("getFetchDirection");
    }
    
    @Override
    public final void setFetchDirection(final int direction) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection");
    }
    
    @Override
    public final void addBatch(final String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("addBatch sql");
    }
    
    @Override
    public void clearBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException("clearBatch");
    }
    
    @Override
    public int[] executeBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException("executeBatch");
    }
    
    @Override
    public final void closeOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException("closeOnCompletion");
    }
    
    @Override
    public final boolean isCloseOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException("isCloseOnCompletion");
    }
    
    @Override
    public final void setCursorName(final String name) throws SQLException {
        throw new SQLFeatureNotSupportedException("setCursorName");
    }
}
