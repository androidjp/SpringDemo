package model.register;

import model.IRegister;
import model.IRequestCallback;
import model.RequestManager;
import model.connection_pool.ConPool;
import model.login.LoginManager;
import pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public class RegisterManager extends RequestManager implements IRegister{
    private RegisterManager(){

    }

    private static final class SingletonHolder{
        private static final RegisterManager sInstance = new RegisterManager();
    }

    public static RegisterManager getInstance(){
        return RegisterManager.SingletonHolder.sInstance;
    }

    @Override
    public void register(String user_id, String user_pwd) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("SELECT * from user where user_id=?&&user_pwd=?");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("成功请求");
            if (resultSet.next()){
                if (this.mCallback!=null){
                    this.mCallback.error("用户名存在");
                }
            }else{
                //注册
                PreparedStatement preparedStatement2 = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("INSERT INTO user(user_id,user_pwd) VALUES(?,?)");
                preparedStatement2.setString(1, user_id);
                preparedStatement2.setString(2, user_pwd);
                preparedStatement2.execute();
                System.out.println("成功插入");
                if (this.mCallback!=null)
                    this.mCallback.finish();
                preparedStatement2.cancel();
            }
            preparedStatement.cancel();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
