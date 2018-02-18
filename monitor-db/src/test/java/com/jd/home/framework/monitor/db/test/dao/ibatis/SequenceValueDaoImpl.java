package com.jd.home.framework.monitor.db.test.dao.ibatis;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 序列Dao
 */
@Repository
public class SequenceValueDaoImpl {

    @Resource
    private SqlMapClientTemplate sqlMapClientTemplate;

    @Resource
    private PlatformTransactionManager txManager;



    /**
     * 插入
     * @param sequenceValue
     */
    public Object insert(SequenceValue sequenceValue){
       return sqlMapClientTemplate.insert("SequenceValue.insert",sequenceValue);
    }


    /**
     * 查询
     * @param
     */
    public List<SequenceValue> select(Long id){
       return sqlMapClientTemplate.queryForList("SequenceValue.select",id);
    }



    /**
     * 插入
     * @param sequenceValue
     */
    public int update(SequenceValue sequenceValue){
        return sqlMapClientTemplate.update("SequenceValue.update",sequenceValue);
    }

    /**
     * 删除
     * @param uid
     */
    public int delete(Long uid){
        return sqlMapClientTemplate.delete("SequenceValue.delete",uid);
    }


    public PlatformTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    public TransactionTemplate getTransactionTemplate(){
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return txTemplate;
    }
}
