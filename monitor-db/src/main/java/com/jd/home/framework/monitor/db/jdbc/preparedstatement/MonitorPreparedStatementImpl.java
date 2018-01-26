package com.jd.home.framework.monitor.db.jdbc.preparedstatement;
import com.jd.home.framework.monitor.db.config.MonitorConfig;

import java.sql.*;


/**
 * Title : 监控PreparedStatement实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorPreparedStatementImpl extends AbstractMonitorPreparedStatement {


    public MonitorPreparedStatementImpl(PreparedStatement preparedStatement,MonitorConfig config){
        this.preparedStatement = preparedStatement;
        this.monitorConfig = config;
    }


}
