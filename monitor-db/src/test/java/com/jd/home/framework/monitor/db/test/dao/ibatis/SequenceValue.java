package com.jd.home.framework.monitor.db.test.dao.ibatis;

/**
 * 序列
 */
public class SequenceValue {
    private Long uid;
    private String name;
    private Long id;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SequenceValue{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
