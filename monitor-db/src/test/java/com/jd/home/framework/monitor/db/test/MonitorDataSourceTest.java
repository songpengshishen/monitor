package com.jd.home.framework.monitor.db.test;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * 监控DataSource测试
 * @author wsp
 * @since 2018/01/30
 *
 */
public abstract class MonitorDataSourceTest {


    protected DataSource dataSource;

    private Long startTime;

    @Before
    public void before(){
        startTime = System.currentTimeMillis();
        dataSource =  createMonitorDataSource();
    }


    @After
    public void after(){
        System.out.println("执行耗时: " + (System.currentTimeMillis() - startTime));
    }




    protected abstract DataSource createMonitorDataSource();


}
