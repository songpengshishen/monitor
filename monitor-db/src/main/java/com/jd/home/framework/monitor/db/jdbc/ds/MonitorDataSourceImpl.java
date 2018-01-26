package com.jd.home.framework.monitor.db.jdbc.ds;
/**
 * Title : 监控数据源实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorDataSourceImpl extends AbstractMonitorDataSource {



    @Override
    public int getActiveConnections() {
        return 0;
    }
}
