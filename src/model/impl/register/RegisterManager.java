package model.impl.register;

import model.IRegister;
import model.RequestManager;
import model.connection_pool.ConPool;
import utils.StringRandomUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 注册管理器
 * Created by junpeng.wu on 1/9/2017.
 */
public class RegisterManager extends RequestManager<String> implements IRegister{
//    private RegisterManager(){
//
//    }
//
//    private static final class SingletonHolder{
//        private static final RegisterManager sInstance = new RegisterManager();
//    }
//
//    public static RegisterManager getInstance(){
//        return RegisterManager.SingletonHolder.sInstance;
//    }

    @Override
    public void register(String user_name, String user_pwd,String email,String phone, int age, int sex) {
        try {
            System.out.println("RegisterManager.register()");
            if ((phone==null)^(email==null)){
                ///其中一个存在的
                if (phone!=null){///只有手机号
                    System.out.println("RegisterManager:  只有手机号");
                    PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("SELECT * from user where phone=?");
                    preparedStatement.setString(1,phone);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    System.out.println("成功查询是否存在此用户");
                    if (resultSet.next()){
                        if (this.mCallback!=null){
                            System.out.println("Register: 此手机号已被绑定");
                            this.mCallback.error("此手机号已被绑定");
                        }
                    }else{
                        //注册
                        PreparedStatement preparedStatement2 = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("INSERT INTO user(user_id,user_name,user_pwd,phone,age,sex) VALUES(?,?,?,?,?,?)");
                        preparedStatement2.setString(1, StringRandomUtil.getStringRandom(20));
                        preparedStatement2.setString(2, (user_name==null)?phone:user_name);
                        preparedStatement2.setString(3, user_pwd);
                        preparedStatement2.setString(4, phone);
                        preparedStatement2.setInt(5, age);
                        preparedStatement2.setInt(6, sex);
                        preparedStatement2.execute();
                        System.out.println("成功插入数据库");
                        if (this.mCallback!=null)
                            this.mCallback.finish("成功");
                        preparedStatement2.cancel();
                    }
                    preparedStatement.cancel();
                }else{///只有邮箱
                    System.out.println("RegisterManager:  只有邮箱");
                    PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("SELECT * from user where email=?");
                    preparedStatement.setString(1,email);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    System.out.println("成功查询是否存在此用户");
                    if (resultSet.next()){
                        if (this.mCallback!=null){
                            System.out.println("Register: 此邮箱已被绑定");
                            this.mCallback.error("此邮箱已被绑定");
                        }
                    }else{
                        //注册
                        PreparedStatement preparedStatement2 = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("INSERT INTO user(user_id,user_name,user_pwd,email,age,sex) VALUES(?,?,?,?,?,?)");
                        preparedStatement2.setString(1, StringRandomUtil.getStringRandom(20));
                        preparedStatement2.setString(2, (user_name==null)?email:user_name);
                        preparedStatement2.setString(3, user_pwd);
                        preparedStatement2.setString(4, email);
                        preparedStatement2.setInt(5, age);
                        preparedStatement2.setInt(6, sex);
                        preparedStatement2.execute();
                        System.out.println("成功插入数据库");
                        if (this.mCallback!=null)
                            this.mCallback.finish("成功");
                        preparedStatement2.cancel();
                    }
                    preparedStatement.cancel();
                }
            }else{///两者都输入了
                System.out.println("RegisterManager:  手机号与邮箱都存在");

                PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("SELECT * from user where email=? OR phone=?");
                preparedStatement.setString(1,email);
                preparedStatement.setString(2,phone);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("成功查询是否存在此用户");
                if (resultSet.next()){
                    if (this.mCallback!=null){
                        System.out.println("邮箱或手机已被绑定");
                        this.mCallback.error("邮箱或手机已被绑定");
                    }
                }else{
                    //注册
                    PreparedStatement preparedStatement2 = ConPool.getInstance("traffic_helper").getCon().getConnection().prepareStatement("INSERT INTO user(user_id,user_name,user_pwd,email,phone,age,sex) VALUES(?,?,?,?,?,?,?)");
                    preparedStatement2.setString(1, StringRandomUtil.getStringRandom(20));
                    preparedStatement2.setString(2, (user_name==null)?email:user_name);
                    preparedStatement2.setString(3, user_pwd);
                    preparedStatement2.setString(4, email);
                    preparedStatement2.setString(5, phone);
                    preparedStatement2.setInt(6, age);
                    preparedStatement2.setInt(7, sex);
                    preparedStatement2.execute();
                    System.out.println("成功插入数据库");
                    if (this.mCallback!=null)
                        this.mCallback.finish("成功");
                    preparedStatement2.cancel();
                }
                preparedStatement.cancel();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
