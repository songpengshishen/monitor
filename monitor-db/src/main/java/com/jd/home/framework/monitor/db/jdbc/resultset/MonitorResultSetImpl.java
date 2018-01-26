package com.jd.home.framework.monitor.db.jdbc.resultset;
import java.sql.*;

/**
 * Title : 监控ResultSet实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorResultSetImpl extends AbstractMonitorResultSet{

    public MonitorResultSetImpl(ResultSet resultSet){
        this.targetResultSet = resultSet;
    }


}
