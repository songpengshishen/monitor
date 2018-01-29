package com.jd.home.framework.monitor.db.jdbc.ds;
import com.alibaba.druid.pool.DruidDataSource;
import com.jd.home.framework.monitor.db.enums.DataSourceTypeEnum;

/**
 * Title : 监控Druid数据源实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorDruidDataSource extends AbstractMonitorDataSource {

    private DruidDataSource druidDataSource;

    @Override
    public int getActiveConnections() {
        return druidDataSource.getMaxActive();
    }

    @Override
    public DataSourceTypeEnum getDataSourceType() {
        return DataSourceTypeEnum.DRUID;
    }

    @Override
    protected void doProcess() {
        if(!(this.targetDataSource instanceof DruidDataSource)){
            throw new IllegalArgumentException("Only Support DruidDataSource");
        }
        druidDataSource = (DruidDataSource)this.targetDataSource;
    }


}
