package com.jd.home.framework.monitor.db.test.spring;

import com.jd.home.framework.monitor.db.test.dao.ibatis.SequenceValue;
import com.jd.home.framework.monitor.db.test.dao.ibatis.SequenceValueDaoImpl;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by wangsongpeng on 2018/1/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config-druid.xml"})
public class MonitorDruidDataSourceSpringTest extends TestCase{

    @Resource
    private SequenceValueDaoImpl sequenceValueDao;

    @Test
    public void insert(){
        sequenceValueDao.getTransactionTemplate().execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    SequenceValue sequenceValue = new SequenceValue();
                    sequenceValue.setUid(1000l);
                    sequenceValue.setName("test");
                    sequenceValue.setId(1l);
                    Object obj = sequenceValueDao.insert(sequenceValue);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
                return true;
            }
        });
    }


    @Test
    public void select(){
      List<SequenceValue> ls =  sequenceValueDao.select(1000l);
      System.out.println(ls);
    }

    @Test
    public void delete(){
        sequenceValueDao.getTransactionTemplate().execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    Object obj = sequenceValueDao.delete(1000l);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
                return true;
            }
        });
    }



    @Test
    public void update(){
        sequenceValueDao.getTransactionTemplate().execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    SequenceValue sequenceValue = new SequenceValue();
                    sequenceValue.setUid(1000l);
                    sequenceValue.setName("haha");
                    Object obj = sequenceValueDao.update(sequenceValue);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
                return true;
            }
        });
    }

}
