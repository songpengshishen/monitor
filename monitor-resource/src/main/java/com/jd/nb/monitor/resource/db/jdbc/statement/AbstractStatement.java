package com.jd.nb.monitor.resource.db.jdbc.statement;


import com.jd.nb.monitor.resource.db.jdbc.proxy.ProxyStatement;

import java.sql.Statement;

/**
 * 抽象的Statement
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/01/25
 */
public abstract class AbstractStatement extends ProxyStatement {

    /**
     * 目标Statement
     */
    protected Statement targetStatement;

    public Statement getTargetStatement() {
        return targetStatement;
    }

    public void setTargetStatement(Statement targetStatement) {
        this.targetStatement = targetStatement;
    }
}
