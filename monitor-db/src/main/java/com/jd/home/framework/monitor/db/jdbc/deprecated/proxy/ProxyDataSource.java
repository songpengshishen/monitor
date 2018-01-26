package com.jd.home.framework.monitor.db.jdbc.deprecated.proxy;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 代理数据源抽象类,负责代理jdbc DataSource接口的缺省方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/25
 */
public abstract class ProxyDataSource extends ProxyWrapper implements DataSource{

    private PrintWriter logWriter = new PrintWriter(System.out);

    private Logger logger = Logger.getLogger("proxy-jdbc-log");


    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }


    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
         this.logWriter = out;
    }


    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
          throw new UnsupportedOperationException("Unsupported  SetLoginTimeout!");
    }


    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("Unsupported  getLoginTimeout!");
    }


    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return logger;
    }




}
