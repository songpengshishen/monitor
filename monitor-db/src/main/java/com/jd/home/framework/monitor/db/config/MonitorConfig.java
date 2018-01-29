package com.jd.home.framework.monitor.db.config;

/**
 * Title : 监控配置
 * Description: 提供配置监控参数和指标  </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class MonitorConfig {

    /********************开关配置Start********************/
    /**
     * SQL执行异常报警开关。
     */
    private boolean sqlExceptionEnabled = true;

    /**
     * 慢SQL报警开关。
     */
    private boolean slowSqlEnabled = true;

    /**
     * 连接数过多报警开关。
     */
    private boolean tooManyActiveConnEnabled = true;

    /********************开关配置End********************/


    /******************** umpKey Start********************/
    /**
     * SQL执行异常UMP报警的key。
     */
    private String sqlExceptionKey = "ql.app.sql.exception";

    /**
     * SQL执行异常UMP报警的key。
     */
    private String slowSqlKey = "ql.app.slowsql.timeout";


    /**
     * 连接数过多UMP报警的key。
     */
    private String tooManyActiveConnKey = "ql.app.tooMany.active.connection";

    /******************** umpKey End********************/


    /******************** 指标参数配置 *********************/

    /**
     * 慢SQL执行超时时间，单位是毫秒。
     */
    private long slowSqlTimeout = 1000;


    /**
     * 连接数过多报警。活跃连接占比允许的最大值，超过该值将会报警。
     */
    private float maxActiveConRatio = 0.7f;


    /**
     * 日志中是否打印DB的连接url。
     */
    private boolean isLogDBUrl = false;


    /**
     * 忽略的sql语句id列表，多个id请以";"隔开。sql的id必须是包含namespace的完整id值。
     */
    private String excludeStatementIds;

    /******************** 指标参数配置 *********************/


    public boolean isSqlExceptionEnabled() {
        return sqlExceptionEnabled;
    }

    public void setSqlExceptionEnabled(boolean sqlExceptionEnabled) {
        this.sqlExceptionEnabled = sqlExceptionEnabled;
    }

    public boolean isSlowSqlEnabled() {
        return slowSqlEnabled;
    }

    public void setSlowSqlEnabled(boolean slowSqlEnabled) {
        this.slowSqlEnabled = slowSqlEnabled;
    }

    public boolean isTooManyActiveConnEnabled() {
        return tooManyActiveConnEnabled;
    }

    public void setTooManyActiveConnEnabled(boolean tooManyActiveConnEnabled) {
        this.tooManyActiveConnEnabled = tooManyActiveConnEnabled;
    }

    public String getSqlExceptionKey() {
        return sqlExceptionKey;
    }

    public void setSqlExceptionKey(String sqlExceptionKey) {
        this.sqlExceptionKey = sqlExceptionKey;
    }

    public String getSlowSqlKey() {
        return slowSqlKey;
    }

    public void setSlowSqlKey(String slowSqlKey) {
        this.slowSqlKey = slowSqlKey;
    }

    public String getTooManyActiveConnKey() {
        return tooManyActiveConnKey;
    }

    public void setTooManyActiveConnKey(String tooManyActiveConnKey) {
        this.tooManyActiveConnKey = tooManyActiveConnKey;
    }

    public long getSlowSqlTimeout() {
        return slowSqlTimeout;
    }

    public void setSlowSqlTimeout(long slowSqlTimeout) {
        this.slowSqlTimeout = slowSqlTimeout;
    }

    public float getMaxActiveConRatio() {
        return maxActiveConRatio;
    }

    public void setMaxActiveConRatio(float maxActiveConRatio) {
        this.maxActiveConRatio = maxActiveConRatio;
    }

    public boolean isLogDBUrl() {
        return isLogDBUrl;
    }

    public void setLogDBUrl(boolean logDBUrl) {
        isLogDBUrl = logDBUrl;
    }

    public String getExcludeStatementIds() {
        return excludeStatementIds;
    }

    public void setExcludeStatementIds(String excludeStatementIds) {
        this.excludeStatementIds = excludeStatementIds;
    }
}
