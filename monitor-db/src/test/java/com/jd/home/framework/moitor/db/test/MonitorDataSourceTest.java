package com.jd.home.framework.moitor.db.test;
import org.junit.Before;


/**
 * 监控DataSource测试
 * @author wsp
 * @since 2018/01/30
 */
public abstract class MonitorDataSourceTest<T> {


    protected T dataSource;


    @Before
    public void before(){
        dataSource =  createDataSource();
    }


    protected abstract T createDataSource();


}
