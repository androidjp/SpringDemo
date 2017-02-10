package base;

/**
 * Created by junpeng.wu on 1/6/2017.
 */
public class Constant {
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";

    public static final String USER_PIC = "user_pic";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String KIND = "kind";

    public static boolean isEmpty(String value){
        return value==null || value.length()==0;
    }
}
