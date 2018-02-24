package com.jd.home.framework.monitor.db.test;

import com.jd.home.framework.monitor.db.config.SystemConstans;
import com.jd.home.framework.monitor.db.jdbc.ds.MonitorDbcpDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 监控DBCP测试
 *
 * @author wsp
 * @since 2018/01/30
 */
public class MonitorDbcpDataSourceTest extends MonitorDataSourceTest {


    @Test
    public void select() throws Exception {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SqlConstant.TEST_SELECT_SQL);
        while (resultSet.next()) {
            System.out.println(resultSet.getLong(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getLong(3));
        }
    }

    @Test
    public void insert() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        int flag = statement.executeUpdate(SqlConstant.TEST_INNSERT_SQL);
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void update() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        int flag = statement.executeUpdate(SqlConstant.TEST_UPDATE_SQL);
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void delete() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        int flag = statement.executeUpdate(SqlConstant.TEST_DELETE_SQL);
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void selectByPrepared() throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.TEST_SELECT_SQL_PREPARED);
        preparedStatement.setInt(1, 1000);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getLong(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getLong(3));
        }
    }


    @Test
    public void insertByPrepared() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.TEST_INNSERT_SQL_PREPARED);
        preparedStatement.setInt(1, 1000);
        preparedStatement.setString(2, "test");
        preparedStatement.setLong(3, 1);
        int flag = preparedStatement.executeUpdate();
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void updateByPrepared() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.TEST_UPDATE_SQL_PREPARED);
        preparedStatement.setString(1, "haha");
        preparedStatement.setLong(2, 1000);
        int flag = preparedStatement.executeUpdate();
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void deleteByPrepared() throws Exception {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(SqlConstant.TEST_DELETE_SQL_PREPARED);
        preparedStatement.setLong(1, 1000);
        int flag = preparedStatement.executeUpdate();
        connection.commit();
        System.out.println(flag);
    }


    @Test
    public void getMeteData() throws Exception {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.getDatabaseProductName());
    }

    protected MonitorDbcpDataSource createMonitorDataSource() {
        MonitorDbcpDataSource dataSource = new MonitorDbcpDataSource();
        dataSource.setTargetDataSource(createDbcpDataSource());
        dataSource.setSlowSqlTimeout(150);
        dataSource.init();
        return dataSource;
    }


    private DataSource createDbcpDataSource() {
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

    @Test
    public void test() throws Exception{
        long time = System.currentTimeMillis();
        for(int i = 5000 ; i<10000;i++){
            Connection connection = createDbcpDataSource().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int flag = statement.executeUpdate("insert into sequence_value (uid,name,id)value("+i+","+i+","+i+")");
            connection.commit();
        }
        System.out.println("测试完成,共耗时 : " + (System.currentTimeMillis() - time));
    }

}
