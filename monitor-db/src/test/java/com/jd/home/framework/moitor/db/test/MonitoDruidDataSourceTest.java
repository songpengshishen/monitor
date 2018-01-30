package com.jd.home.framework.moitor.db.test;
import com.jd.home.framework.monitor.db.jdbc.ds.MonitorDruidDataSource;
import org.junit.Test;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 监控Druid测试
 * @author wsp
 * @since 2018/01/30
 */
public class MonitoDruidDataSourceTest extends MonitorDataSourceTest<MonitorDruidDataSource>{



    @Test
    public void select()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        String sql = "select * from auction_ware_new limit 0,1";
        ResultSet resultSet =  statement.executeQuery(sql);
        System.out.println(resultSet);
    }



    protected MonitorDruidDataSource createDataSource(){
        return null;
    }


    private DataSource createDruidDataSource(){
        return null;
    }}
