package com.jd.home.framework.monitor.db.config;


/**
 * Title : 系统常量
 * Description: 各项监控指标默认值  </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public class SystemConstans {

    /**
     * 最小的慢SQL超时时间，不能小于这个值，以免报警过于频繁。
     */
    public static final long MIN_SLOW_SQL_TIMEOUT = 20;


    /**
     * 最低允许的活跃连接占比。不能过低，以免导致报警过于频繁。
     */
    public static final float MIN_MAX_ACTIVE_CONNECTION_RATIO = 0.3f;


}
