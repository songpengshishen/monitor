package com.jd.home.framework.monitor.db.test;

/**
 * Sql常量类
 * @author wsp
 * @since 2018/02/18
 */
public class SqlConstant {


    public static final String TEST_INNSERT_SQL = "insert into sequence_value (uid,name,id)value(1000,\"test\",1)";
    public static final String TEST_SELECT_SQL = "select * from sequence_value";
    public static final String TEST_UPDATE_SQL = "update sequence_value set name = 'haha' where uid = 1000";
    public static final String TEST_DELETE_SQL = "delete from sequence_value where uid = 1000";

    public static final String TEST_SELECT_SQL_PREPARED = "select * from sequence_value where uid = ?";
    public static final String TEST_INNSERT_SQL_PREPARED = "insert into sequence_value (uid,name,id)value(?,?,?)";
    public static final String TEST_UPDATE_SQL_PREPARED = "update sequence_value set name = ? where uid = ?";
    public static final String TEST_DELETE_SQL_PREPARED = "delete from sequence_value where uid = ?";
}
