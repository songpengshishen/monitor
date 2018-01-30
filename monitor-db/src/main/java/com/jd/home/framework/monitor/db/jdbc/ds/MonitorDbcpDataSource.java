package com.jd.home.framework.monitor.db.jdbc.ds;
import com.jd.home.framework.monitor.db.enums.DataSourceTypeEnum;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * Title : 监控DBCP数据源实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorDbcpDataSource extends AbstractMonitorDataSource {

    private BasicDataSource basicDataSource;

    /**
     * 获取当前活跃的连接数
     * @return
     */
    @Override
    public int getActiveConnections() {
        return basicDataSource.getNumActive();
    }

    /**
     * 获取当前数据源url
     * @return
     */
    @Override
    public String getUrl() {
        return basicDataSource.getUrl();
    }

    /**
     * 获取最高活跃的连接数
     * @return
     */
    @Override
    public int getMaxActiveConnections() {
        return basicDataSource.getMaxActive();
    }

    @Override
    public DataSourceTypeEnum getDataSourceType() {
        return DataSourceTypeEnum.DBCP;
    }

    @Override
    protected void doProcess() {
        if(!(this.targetDataSource instanceof BasicDataSource)){
            throw new IllegalArgumentException("Only Support BasicDataSource");
        }
        basicDataSource = (BasicDataSource)this.targetDataSource;
    }

}
