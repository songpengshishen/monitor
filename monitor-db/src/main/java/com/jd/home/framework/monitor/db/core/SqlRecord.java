package com.jd.home.framework.monitor.db.core;

/**
 * Title : sql执行记录
 * Description: 记录一个sql的执行时间,执行过程</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public final class SqlRecord {

    /**
     * sql开始执行时间
     */
    private Long startTime;

    /**
     * sql执行结束时间
     */
    private Long elapsedTime;

    /**
     * sql语句
     */
    private String sql;

    /**
     * sql执行过程状态
     */
    private int processState;

    /**
     * 执行成功
     */
    public static final int STATE_TRUE = 0;

    /**
     * 执行失败
     */
    public static final int STATE_FALSE = 1;

    /**
     * 执行异常堆栈
     */
    private Throwable throwable;


    public SqlRecord(String sql){
        this(System.currentTimeMillis(),sql,STATE_FALSE);
    }

    public SqlRecord(Long startTime,String sql){
        this(startTime,sql,STATE_FALSE);
    }

    public SqlRecord(Long startTime,String sql,int processState){
        this.startTime = startTime;
        this.sql = sql;
        this.processState = processState;
        this.elapsedTime = -1l;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getElapsedTime() {
        if(this.elapsedTime == -1L) {
            this.elapsedTime = System.currentTimeMillis() - this.startTime;
        }
        return this.elapsedTime;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public int getProcessState() {
        return processState;
    }

    public void setProcessState(int processState) {
        this.processState = processState;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
