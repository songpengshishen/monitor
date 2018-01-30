package com.jd.home.framework.moitor.db.test;
import com.jd.home.framework.monitor.db.jdbc.ds.MonitorDbcpDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 监控DBCP测试
 * @author wsp
 * @since 2018/01/30
 */
public class MonitorDbcpDataSourceTest extends MonitorDataSourceTest<MonitorDbcpDataSource> {


    @Test
    public void select()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        String sql = "select * from auction_ware_new limit 0,1";
        ResultSet resultSet =  statement.executeQuery(sql);
        System.out.println(resultSet);
    }

    @Test
    public void insert()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        String sql = "insert into sequence_value (uid,name,id)value(0,\"test\",1);";
        int flag =  statement.executeUpdate(sql);
        System.out.println(flag);
    }


    @Test
    public void update()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        String sql = "update sequence_value set name = 'haha' where uid = 48";
        int flag =  statement.executeUpdate(sql);
        System.out.println(flag);
    }


    @Test
    public void delete()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        String sql = "delete from sequence_value where uid = 48";
        int flag =  statement.executeUpdate(sql);
        System.out.println(flag);
    }


    protected MonitorDbcpDataSource createDataSource(){
        MonitorDbcpDataSource dataSource = new MonitorDbcpDataSource();
        dataSource.setTargetDataSource(createDbcpDataSource());
        dataSource.init();
        return dataSource;
    }


    private DataSource createDbcpDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://192.168.195.161:3306/pop_auction");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("admin");
        basicDataSource.setInitialSize(0);
        basicDataSource.setMaxActive(15);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxWait(15000);
        return basicDataSource;
    }

}
