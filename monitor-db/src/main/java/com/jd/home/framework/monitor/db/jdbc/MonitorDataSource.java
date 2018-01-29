package com.jd.home.framework.monitor.db.jdbc;
import com.jd.home.framework.monitor.db.enums.DataSourceTypeEnum;

import javax.sql.DataSource;

/**
 * Title : 监控数据源接口
 * Description: 扩展 JDBC {@link DataSource}接口,添加监控规范 </br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/1/26
 */
public interface MonitorDataSource extends DataSource{

    /**
     * 获取当前活跃的连接数
     * @return 连接数个数
     */
     int getActiveConnections();


    /**
     * 获取最高的活跃的连接数
     * @return 连接数个数
     */
     int getMaxActiveConnections();


    /**
     * 获取数据源连接URL
     * @return
     */
     String getUrl();

    /**
     * 获取数据源类型
     * @return {@link DataSourceTypeEnum}
     */
     DataSourceTypeEnum getDataSourceType();

}
