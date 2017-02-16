package model.impl.connection_pool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接池
 * Created by androidjp on 2016/11/16.
 */
public class ConPool {
    private List<MyCon> freeCons = new ArrayList<>();
    private List<MyCon> buzyCons = new ArrayList<>();
    private int max = 10;
    private int min = 2;
    private int current = 0;
    private static ConPool sInstance;

    private ConPool(String db_name){
        MySqlDAO.useDatabase(db_name);
        while(this.min > this.current){
            this.freeCons.add(this.createCon());
        }
    }

    public static ConPool getInstance(String db_name){
        if (sInstance==null){
            synchronized (ConPool.class){
                if (sInstance==null)
                    sInstance = new ConPool(db_name);
            }
        }
        return sInstance;
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public MyCon getCon() {
        MyCon myCon = this.getFreeCon();
        if (myCon!=null)
            return myCon;
        return this.getNewCon();
    }


    /**
     * 设置连接为空闲状态
     * @param con 连接
     */
    public void setFree(MyCon con){
        this.buzyCons.remove(con);
        con.setState(MyCon.FREE);
        this.freeCons.add(con);
    }

    /**
     *
     * @return 返回当前连接池的连接状态
     */
    public String toString(){
        return "当前连接数："+ this.current + "，空闲连接数："+this.freeCons.size()+"，繁忙连接数："+this.buzyCons.size();
    }


    //========================================================================================

    private MyCon getNewCon() {

        if (this.current>=this.max)
            return null;
        MyCon myCon = this.createCon();
        assert myCon != null;
        myCon.setState(MyCon.BUZY);
        this.buzyCons.add(myCon);
        return myCon;
    }

    private MyCon createCon() {
        try{
            Connection connection = MySqlDAO.getConnection();
            MyCon myCon = new MyCon(connection);
            this.current++;
            return myCon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MyCon getFreeCon() {
        if (freeCons.size()>0){
            MyCon con  =freeCons.remove(0);
            con.setState(MyCon.BUZY);
            this.buzyCons.add(con);
            return con;
        }
        return null;
    }
}
