package com.jd.home.framework.moitor.db.test;
import org.junit.After;
import org.junit.Before;


/**
 * 监控DataSource测试
 * @author wsp
 * @since 2018/01/30
 */
public abstract class MonitorDataSourceTest<T> {


    protected T dataSource;

    private Long startTime;

    @Before
    public void before(){
        startTime = System.currentTimeMillis();
        dataSource =  createDataSource();
    }



    @After
    public void after(){
        System.out.println("执行耗时: " + (System.currentTimeMillis() - startTime));
    }


    protected abstract T createDataSource();


}
