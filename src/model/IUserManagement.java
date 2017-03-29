package model;

import java.io.File;

/**
 * 用户管理类
 * Created by junpeng.wu on 2/10/2017.
 */
public interface IUserManagement {
    public void updateUserName(String userId, String userName);
    public void updateUserPwd(String userId,String userPwd);
    public void updateEmail(String userId, String email);
    public void updatePhone(String userId, String phone);
    public void updateUserPic(String userId, String fileName);
    public void updateSex(String userId, int sex);
    public void updateAge(String userId, int age);
    public void execute(String sql);
    public void executeQuery(String sql);
}
