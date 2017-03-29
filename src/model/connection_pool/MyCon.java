package model.connection_pool;

import java.sql.Connection;

/**
 * Created by androidjp on 2016/11/16.
 */
public class MyCon {
    public static final int FREE = 100;///空闲
    public static final int BUZY = 101;///繁忙
    public static final int CLOSED = 102;///连接关闭
    private Connection connection;//持有Connection对象的引用
    private int state= FREE;///当前的连接状态

    public MyCon(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * @return 当前状态
     */
    public int getState() {
        return state;
    }

    /**
     * @param state 设置当前状态
     */
    public void setState(int state) {
        this.state = state;
    }
}
