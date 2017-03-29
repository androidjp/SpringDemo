package model.connection_pool;

import java.sql.*;

/**
 * 数据库连接管理类
 * Created by androidjp on 2017/1/8.
 */
public class MySqlDAO {

    private static String database = null;
    /**
     * 建立连接
     * @return 连接
     * @throws Exception 连接异常
     */
    public static Connection getConnection() throws Exception{
        String driverName="com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/"+(database==null?"":database);///连接协议 + 数据库地址+ 数据库名称
        System.out.println(url);
        String user = "root";
        String password="root";

        Class.forName(driverName);///加载数据库驱动，此过程会自动调用DriverManager中的registerDriver(Driver driver)方法，注册到管理器中
        Connection con = DriverManager.getConnection(url,user,password);////创建并获取连接
        return con;//等待并最终返回连接信息
    }



    /**
     * 建立连接会话后，通过statement对数据进行CRUD
     * @return 会话
     * @throws Exception 异常
     */
    static Statement getStatement() throws Exception{
        return getConnection().createStatement();
    }

    static PreparedStatement preparedStatement(String sql) throws Exception{
        return getConnection().prepareStatement(sql);
    }

    /**
     * 进入数据库
     * @param dbName 数据库名
     */
    public static void useDatabase(String dbName){
        database = dbName;
        if (database==null||database.length()==0)
            database = null;
    }
    /**
     * 执行数据库语句(增、查、改)
     * @param sql SQL语句
     * @throws Exception 异常
     */
    public static void executeMySQL(String sql) throws Exception {
        Statement statement = getStatement();
        statement.execute(sql);
        statement.close();
    }

    /**
     * 数据库查询
     * @param sql select语句
     * @return resultSet
     * @throws Exception 异常
     */
    public static ResultSet query(String sql, QueryResultCallback callback) throws Exception{
        Statement statement = getStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        callback.onQueryResult(resultSet);
        statement.close();
        return resultSet;
    }

    public interface QueryResultCallback{
        void onQueryResult(ResultSet resultSet);
    }


    public static void testStatement() throws Exception{

    }

    public static void testPrepareStatement() throws Exception {
        String sql = "select * from user where name=?";
        PreparedStatement pStatement = preparedStatement(sql);
        pStatement.setString(1,"小明");
        ResultSet res = pStatement.executeQuery();
        while(res.next()){
            System.out.printf("id：%d， email: %s\n",res.getInt("uid"),res.getString(3));
        }
    }

}

