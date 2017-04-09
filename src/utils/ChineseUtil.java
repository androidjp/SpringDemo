package utils;

import java.io.UnsupportedEncodingException;

/**
 * 处理中文信息
 * Created by junpeng.wu on 1/6/2017.
 */
public class ChineseUtil {

//    /**
//     * 调整中文格式（除乱码）
//     * 数据库 -> Java
//     * @param chinese 中文
//     * @return 调整后的中文
//     */
//    public static String adjustMessCode(String chinese){
//        if (chinese==null || chinese.length() ==0)
//            return null;
//        try {
//            System.out.println("ChineseUtil.adjustMessCode("+chinese+")");
////            return new String(chinese.getBytes("ISO8859-1"),"UTF-8");
//            return new String(chinese.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return chinese;
//    }
//
//    ///Java -> 数据库
//    public static String toUTF8(String str){
//
//        if (str==null || str.length() ==0)
//            return null;
//        try {
//            System.out.println("ChineseUtil.toUTF8("+str+")");
////            return new String(chinese.getBytes("ISO8859-1"),"UTF-8");
//            return new String(str.getBytes(),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

}
