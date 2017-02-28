package model.user;

import base.Constant;
import model.IUserManagement;
import model.RequestManager;
import model.impl.connection_pool.ConPool;
import pojo.User;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User管理实现类
 * Created by junpeng.wu on 2/10/2017.
 */
public class UserManager extends RequestManager<User> implements IUserManagement {

    private UserManager() {

    }

    public static UserManager getInstance() {
        return SingletonHolder.sInstance;
    }

    @Override
    public void updateUserName(String userId, String userName) {
        updateSingleValue(userId, "user_name", userName);
    }

    @Override
    public void updateUserPwd(String userId, String userPwd) {
       updateSingleValue(userId, "user_pwd", userPwd);
    }

    @Override
    public void updateEmail(String userId, String email) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT user_id from user where email=?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                if (mCallback!=null)
                    mCallback.error("该邮箱已被绑定，请重新设定");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateSingleValue(userId, "email", email);
    }

    @Override
    public void updatePhone(String userId, String phone) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT user_id from user where phone=?");
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                if (mCallback!=null)
                    mCallback.error("该手机号已被绑定，请重新设定");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        updateSingleValue(userId, "phone", phone);
    }

    @Override
    public void updateUserPic(String userId, File fileName) {
        ///TODO:用户头像文件如何上传和客户端缓存
        updateSingleValue(userId, "user_pic", fileName.getAbsolutePath());
    }

    @Override
    public void updateSex(String userId, int sex) {
        updateSingleValue(userId,"sex", sex);
    }

    @Override
    public void updateAge(String userId, int age) {
        updateSingleValue(userId,"age", age);
    }


    private <T> void updateSingleValue(String userId , String key ,T value){
        String str = "update user set "+ key + "= ? where user_id=?";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(str);
            if (value instanceof Integer)
                preparedStatement.setInt(1, (Integer) value);
            else if (value instanceof String)
                preparedStatement.setString(1, (String) value);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();
                if (mCallback!=null)
                    mCallback.finish(getUserMsgFromDB(userId));
        } catch (SQLException e) {
            if (mCallback!=null)
                mCallback.error("修改"+key+"异常");
            e.printStackTrace();
        }
    }


    @Override
    public void execute(String sql) {
        if (Constant.isEmpty(sql)) {
            if (this.mCallback != null)
                this.mCallback.error("execute(sql语句为空)~！");
            return;
        }
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            preparedStatement.execute();
            if (mCallback != null)
                mCallback.finish(null);
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("");
            e.printStackTrace();
        }
    }

    @Override
    public void executeQuery(String sql) {
        if (Constant.isEmpty(sql)) {
            if (this.mCallback != null)
                this.mCallback.error("execute(sql语句为空)~！");
            return;
        }
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ///TODO:获取查询的结果
            if (mCallback != null)
                mCallback.finish(null);
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("");
            e.printStackTrace();
        }
    }

    private static final class SingletonHolder {
        private static final UserManager sInstance = new UserManager();
    }


    private User getUserMsgFromDB(String userId) {

        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * from user where user_id=?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUser_id(userId);
                user.setUser_name(resultSet.getString(Constant.USER_NAME));
                user.setUser_pwd(resultSet.getString(Constant.USER_PWD));
                user.setUser_pic(resultSet.getString(Constant.USER_PIC));
                user.setAge(Integer.valueOf(resultSet.getString(Constant.AGE)));
                user.setSex(Integer.valueOf(resultSet.getString(Constant.SEX)));
                user.setEmail(resultSet.getString(Constant.EMAIL));
                user.setPhone(resultSet.getString(Constant.PHONE));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


}
