package com.jd.home.framework.monitor.db.test.spring;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wangsongpeng on 2018/1/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class MonitorDataSourceSpringTest extends TestCase {
    private Long startTime;

    @Before
    public void before(){
        startTime = System.currentTimeMillis();
    }


    @After
    public void after(){
        System.out.println("执行耗时: " + (System.currentTimeMillis() - startTime));
    }
}
