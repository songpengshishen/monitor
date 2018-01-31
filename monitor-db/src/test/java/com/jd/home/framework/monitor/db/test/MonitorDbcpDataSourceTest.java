package com.jd.home.framework.monitor.db.test;
import com.jd.home.framework.monitor.db.jdbc.ds.MonitorDbcpDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 监控DBCP测试
 * @author wsp
 * @since 2018/01/30
 */
public class MonitorDbcpDataSourceTest extends MonitorDataSourceTest<MonitorDbcpDataSource> {


    public static final String INNSERT_SQL = "insert into sequence_value (uid,name,id)value(0,\"test\",1)";
    public static final String SELECT_SQL = "select * from sequence_value";
    public static final String UPDATE_SQL = "update sequence_value set name = 'haha' where uid = 54";
    public static final String DELETE_SQL = "delete from sequence_value where uid = 54";

    @Test
    public void select()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        ResultSet resultSet =  statement.executeQuery(SELECT_SQL);
        System.out.println(resultSet);
    }

    @Test
    public void insert()throws Exception{
        Connection connection =  dataSource.getConnection();
        connection.setAutoCommit(false);
        Statement statement =  connection.createStatement();
        int flag =  statement.executeUpdate(INNSERT_SQL);
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void update()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        int flag =  statement.executeUpdate(UPDATE_SQL);
        System.out.println(flag);
    }


    @Test
    public void delete()throws Exception{
        Connection connection =  dataSource.getConnection();
        Statement statement =  connection.createStatement();
        int flag =  statement.executeUpdate(DELETE_SQL);
        System.out.println(flag);
    }


    @Test
    public void selectByPrepared() throws Exception{
        Connection connection =  dataSource.getConnection();
        String sql = "select * from sequence_value where uid = ?";
        PreparedStatement preparedStatement =  connection.prepareStatement(sql);
        preparedStatement.setInt(1,54);
        ResultSet resultSet =  preparedStatement.executeQuery();
        System.out.println(resultSet);
    }


    @Test
    public void insertByPrepared()throws Exception{
        Connection connection =  dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement =  connection.prepareStatement(INNSERT_SQL);
        int flag =   preparedStatement.executeUpdate();
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void updateByPrepared()throws Exception{
        Connection connection =  dataSource.getConnection();
        PreparedStatement preparedStatement =  connection.prepareStatement(UPDATE_SQL);
        int flag =  preparedStatement.executeUpdate();
        System.out.println(flag);
    }


    @Test
    public void deleteByPrepared()throws Exception{
        Connection connection =  dataSource.getConnection();
        PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_SQL);
        int flag =  preparedStatement.executeUpdate();
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
