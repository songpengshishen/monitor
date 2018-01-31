package com.jd.home.framework.monitor.db.test.spring;

import com.jd.home.framework.monitor.db.test.dao.ibatis.SequenceValue;
import com.jd.home.framework.monitor.db.test.dao.ibatis.SequenceValueDaoImpl;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * Created by wangsongpeng on 2018/1/31.
 */
public class IbatisMonitorDataSourceTest extends MonitorDataSourceSpringTest{

    @Resource
    private SequenceValueDaoImpl sequenceValueDao;

    @Test
    public void testInsert(){
        SequenceValue sequenceValue = new SequenceValue();
        sequenceValue.setUid(100l);
        sequenceValue.setName("test");
        sequenceValue.setId(200l);
        Object obj = sequenceValueDao.insert(sequenceValue);
    }
}
