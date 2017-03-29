package base;

/**
 * 相关全局常量
 * Created by junpeng.wu on 1/6/2017.
 */
public class Constant {
    ///User信息
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String SEX = "sex";
    public static final String KIND = "kind";
    public static final String USER_PIC = "user_pic";
    public static final String AGE = "age";

    //上传文件存放目录
    public static final String UPLOAD_DIR = "upload";
    public static final String USER_IMG_DIR = "user_image_cache";


    //上传配置常数
    public static final int MEMORY_THRESHOLD = 1024*1024*3;//3MB
    public static final int MAX_FILE_SIZE = 1024*1024*40;//40MB
    public static final int MAX_REQUEST_SIZE = 1024*1024*50;//50MB



    public static boolean isEmpty(String value){
        return value==null || value.length()==0;
    }
}
