package model;

/**
 * 注册接口
 * Created by junpeng.wu on 1/9/2017.
 */
public interface IRegister {
    void register(String user_name, String user_pwd,String email,String phone, int age, int sex);
}
