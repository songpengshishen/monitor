package com.jd.home.framework.monitor.db.enums;

import com.jd.home.framework.monitor.db.utils.EnumUtils;

/**
 * Title : 数据源枚举
 * Description: 提供DBCP,DRUID  </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public enum DataSourceTypeEnum {

    DBCP(1,"DBCP"),
    DRUID(2,"DRUID")
    ;

    DataSourceTypeEnum(int val, String desc) {
        this.val = val;
        this.desc = desc;
        EnumUtils.putIntegerCache(DataSourceTypeEnum.class,val,this);
    }

    private int val;
    private String desc;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static DataSourceTypeEnum valueOfByCode(Integer val) {
        return EnumUtils.getByIntegerCache(DataSourceTypeEnum.class,val);
    }
}
