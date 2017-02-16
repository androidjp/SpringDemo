package model.impl.cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
public class CookieManager {


    private CookieManager(){

    }

    private static final class SingletonHolder{
        private static final CookieManager sInstance = new CookieManager();
    }

    public static CookieManager getInstance(){
        return SingletonHolder.sInstance;
    }

    //===================================================

    /**
     * 存储中文到Cookie中
     * @param chinese 中文
     * @return 编码后的值
     */
    public String saveChineseCookie(String chinese){
        try {
            return URLEncoder.encode(chinese,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Cookie中的中文
     * @param cookie_value cookie值
     * @return 解码后的中文
     */
    public String getChineseCookie(String cookie_value){
        try {
            return URLDecoder.decode(cookie_value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
