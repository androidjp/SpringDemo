package pojo;

/**
 * User
 * Created by junpeng.wu on 1/6/2017.
 */
public class User {
    private String user_id;
    private String user_name;
    private String usr_pwd;
    private String phone;
    private String email;
    private int sex;
    private int user_kind;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUsr_pwd() {
        return usr_pwd;
    }

    public void setUsr_pwd(String usr_pwd) {
        this.usr_pwd = usr_pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getUser_kind() {
        return user_kind;
    }

    public void setUser_kind(int user_kind) {
        this.user_kind = user_kind;
    }
}
