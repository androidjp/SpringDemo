package model.login;

import model.ILogin;
import model.IRequestCallback;
import model.RequestManager;
import model.connection_pool.ConPool;
import model.connection_pool.MyCon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户登录相关类
 * Created by junpeng.wu on 1/6/2017.
 */
public class LoginManager extends RequestManager implements ILogin{

    private LoginManager(){

    }

    private static final class SingletonHolder{
        private static final LoginManager sInstance = new LoginManager();
    }

    public static LoginManager getInstance(){
        return SingletonHolder.sInstance;
    }
    //-----------------------------------------------------------------------------------

    @Override
    public void login(String user_name, String password) {
        MyCon connection = ConPool.getInstance("traffic_helper").getCon();
        try {
            PreparedStatement preparedStatement= connection.getConnection().prepareStatement("SELECT * FROM user where user_id=?&&user_pwd=?");
            preparedStatement.setString(1,user_name);
            preparedStatement.setString(2,password);
            ResultSet result =  preparedStatement.executeQuery();
            if (result.next()){
                if (mCallback!=null){
                    mCallback.finish();
                }
            }else{
                if (mCallback!=null){
                    mCallback.error("登录失败，不存在此用户或用户密码有误");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
