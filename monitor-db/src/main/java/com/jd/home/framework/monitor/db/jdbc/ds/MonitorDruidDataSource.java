package com.jd.home.framework.monitor.db.jdbc.ds;
import com.alibaba.druid.pool.DruidDataSource;

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
    protected void refRealDataSource() {
        if(!(this.targetDataSource instanceof DruidDataSource)){
            throw new IllegalArgumentException("Only Support DruidDataSource");
        }
        druidDataSource = (DruidDataSource)this.targetDataSource;
    }

}
