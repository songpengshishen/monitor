package com.jd.home.framework.monitor.db.test.dao.ibatis;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 序列Dao
 */
@Repository
public class SequenceValueDaoImpl {

    @Resource
    private SqlMapClientTemplate sqlMapClientTemplate;


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

}
