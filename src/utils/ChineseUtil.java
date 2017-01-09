package utils;

import java.io.UnsupportedEncodingException;

/**
 * 处理中文信息
 * Created by junpeng.wu on 1/6/2017.
 */
public class ChineseUtil {

    /**
     * 调整中文格式（除乱码）
     * @param chinese 中文
     * @return 调整后的中文
     */
    public static String adjustMessCode(String chinese){
        try {
            return new String(chinese.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return chinese;
    }



}
