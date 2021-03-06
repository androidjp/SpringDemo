package model.impl.login;

import model.ILogin;
import model.RequestManager;
import model.impl.connection_pool.ConPool;
import model.impl.connection_pool.MyCon;
import pojo.User;
import utils.ChineseUtil;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户登录相关类
 * Created by junpeng.wu on 1/6/2017.
 */
public class LoginManager extends RequestManager<User> implements ILogin{

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
    public void login(String user_id, String password) {
        MyCon connection = ConPool.getInstance("traffic_helper").getCon();
        try {
            PreparedStatement preparedStatement= connection.getConnection().prepareStatement("SELECT * FROM user where ( email=? OR phone=? ) AND user_pwd=?");
            preparedStatement.setString(1,user_id);
            preparedStatement.setString(2,user_id);
            preparedStatement.setString(3,password);
            ResultSet result =  preparedStatement.executeQuery();
            if (result.next()){
                //TODO: 构建JSON串并返回
                User user = new User();
                user.setUser_id(result.getString("user_id"));
                try {
                    user.setUser_name(new String(result.getString("user_name").getBytes("utf-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("登录成功时：从MySQL中获取的user_name : "+result.getString("user_name")+" , 长度："+result.getString("user_name").getBytes().length);
                System.out.println("登录成功时： 给到User对象赋值的user_name : "+ user.getUser_id().getBytes().length);

                user.setUser_pwd(result.getString("user_pwd"));
                user.setEmail(result.getString("email"));
                user.setPhone(result.getString("phone"));
                user.setUser_pic(result.getString("user_pic"));
                user.setSex(result.getInt("sex"));
                user.setAge(result.getInt("age"));
                user.setKind(result.getInt("kind"));
                System.out.println(user.toString());
                if (mCallback!=null){
                    mCallback.finish(user);
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
