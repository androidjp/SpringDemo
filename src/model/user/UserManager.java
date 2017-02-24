package model.user;

import base.Constant;
import model.IUserManagerment;
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
public class UserManager extends RequestManager<User> implements IUserManagerment {

    private UserManager() {

    }

    public static UserManager getInstance() {
        return SingletonHolder.sInstance;
    }

    @Override
    public void updateUserName(String userId, String userName) {
        ///TODO: 处理新用户名
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("UPDATE USER SET user_name = ? where user_id=?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userId);
            preparedStatement.execute();

            if (mCallback != null)
                mCallback.finish(getUserMsgFromDB(userId));
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("修改用户信息异常！");
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserPwd(String userId, String userPwd) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("UPDATE USER SET user_pwd = ? where user_id= ?");
            preparedStatement.setString(1, userPwd);
            preparedStatement.setString(2, userId);
            if (mCallback != null)
                mCallback.finish(getUserMsgFromDB(userId));
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("");
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmail(String userId, String email) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * FROM USER where user_id = ?");
            preparedStatement.setString(1, email);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {///需要同时修改user_id 和 email 并判断新的user_id 是否存在
                preparedStatement = null;
                if (mCallback != null)
                    mCallback.error("此邮箱已被绑定，请重新输入");
                return;
            }
            ////查看是否存在 是 id 的email
            set = null;
            preparedStatement = null;
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * FROM USER where email = ?");
            preparedStatement.setString(1, userId);
            set = preparedStatement.executeQuery();
            if (set.next()) {
                preparedStatement = null;
                preparedStatement = ConPool.getInstance("traffic_helper")
                        .getCon().getConnection().prepareStatement("UPDATE USER set user_id = ? AND email = ? where user_id= ?");
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, userId);
            } else {
                preparedStatement = null;
                preparedStatement = ConPool.getInstance("traffic_helper")
                        .getCon().getConnection().prepareStatement("UPDATE USER set email = ? WHERE user_id = ?");
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, userId);
            }
            if (preparedStatement.execute()) {
                if (mCallback != null)
                    mCallback.finish(getUserMsgFromDB(userId));
            } else {
                if (mCallback != null)
                    mCallback.error("修改失败");
            }
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("修改异常");
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhone(String userId, String phone) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * FROM USER where user_id = ?");
            preparedStatement.setString(1, phone);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {///需要同时修改user_id 和 phone 并判断新的user_id 是否存在
                preparedStatement = null;
                if (mCallback != null)
                    mCallback.error("此手机号已被绑定，请重新输入");
                return;
            }
            ////查看是否存在 是 id 的phone
            set = null;
            preparedStatement = null;
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * FROM USER where phone = ?");
            preparedStatement.setString(1, userId);
            set = preparedStatement.executeQuery();
            if (set.next()) {
                preparedStatement = null;
                preparedStatement = ConPool.getInstance("traffic_helper")
                        .getCon().getConnection().prepareStatement("UPDATE USER set user_id = ? AND phone = ? where user_id= ?");
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, userId);
            } else {
                preparedStatement = null;
                preparedStatement = ConPool.getInstance("traffic_helper")
                        .getCon().getConnection().prepareStatement("UPDATE USER set phone = ? WHERE user_id = ?");
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, userId);
            }
            if (preparedStatement.execute()) {
                if (mCallback != null)
                    mCallback.finish(getUserMsgFromDB(userId));
            } else {
                if (mCallback != null)
                    mCallback.error("修改失败");
            }
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("修改异常");
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserPic(String userId, File fileName) {
        ///TODO:用户头像文件如何上传和客户端缓存
    }

    @Override
    public void updateSex(String userId, int sex) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("UPDATE USER SET sex = ? where user_id= ?");
            preparedStatement.setInt(1, sex);
            preparedStatement.setString(2, userId);
            if (mCallback != null)
                mCallback.finish(getUserMsgFromDB(userId));
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("");
            e.printStackTrace();
        }
    }

    @Override
    public void updateAge(String userId, int age) {
        try {
            PreparedStatement preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("UPDATE USER SET age = ? where user_id= ?");
            preparedStatement.setInt(1, age);
            preparedStatement.setString(2, userId);
            if (mCallback != null){
                mCallback.finish(getUserMsgFromDB(userId));
            }
        } catch (SQLException e) {
            if (mCallback != null)
                mCallback.error("");
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


    private User getUserMsgFromDB(String userId){

        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            preparedStatement = ConPool.getInstance("traffic_helper")
                    .getCon().getConnection().prepareStatement("SELECT * from user where user_id=?");
            preparedStatement.setString(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
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
