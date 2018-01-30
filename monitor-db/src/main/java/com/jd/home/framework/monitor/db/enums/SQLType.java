package com.jd.home.framework.monitor.db.enums;

/**
 * Title : Sql语句类型
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/30
 */
public enum SQLType {
    
    /**
     *  数据查询语句
     * <p>{@code SELECT}.</p>
     */
    DQL,
    
    /**
     *  数据操纵语句
     * <p>{@code INSERT}, {@code UPDATE}, {@code DELETE}.</p>
     */
    DML,
    
    /**
     *  数据定义语句
     * <p>{@code CREATE}, {@code ALTER}, {@code DROP}, {@code TRUNCATE}.</p>
     */
    DDL
}
