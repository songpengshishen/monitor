package com.jd.home.framework.monitor.db.core.ump;

import java.util.Date;

/**
 * Title : ump实体
 * Description: 代表一次UMP报警</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/29
 */
public class Ump {

    /**
     * ump平台key
     */
    private String key;

    /**
     * 报警信息
     */
    private String info;

    /**
     * 报警时间
     */
    private Date date;


    public Ump(String key,String info){
        this(key,info,new Date());
    }


    public Ump(String key,String info,Date date){
        this.key = key;
        this.info = info;
        this.date = date;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ump{" +
                "date=" + date +
                ", info='" + info + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
