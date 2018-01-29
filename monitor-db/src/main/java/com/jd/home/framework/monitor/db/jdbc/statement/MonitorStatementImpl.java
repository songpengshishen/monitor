package com.jd.home.framework.monitor.db.jdbc.statement;
import com.jd.home.framework.monitor.db.core.DBMonitor;
import java.sql.Statement;

/**
 * Title : 监控Statement实现
 * Description: 实现真正的监控功能 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorStatementImpl extends AbstractMonitorStatement {



    public MonitorStatementImpl(Statement statement,DBMonitor dbMonitor) {
        this.targetStatement = statement;
        this.dbMonitor = dbMonitor;
    }


}
