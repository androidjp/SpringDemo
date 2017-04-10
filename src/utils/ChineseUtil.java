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


    /**
     * UTF-8编码 转换为对应的 汉字
     *
     * URLEncoder.encode("上海", "UTF-8") ---> %E4%B8%8A%E6%B5%B7
     * URLDecoder.decode("%E4%B8%8A%E6%B5%B7", "UTF-8") --> 上 海
     *
     * convertUTF8ToString("E4B88AE6B5B7")
     * E4B88AE6B5B7 --> 上海
     *
     * @param s
     * @return
     */
    public static String convertUTF8ToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }

        try {
            s = s.toUpperCase();

            int total = s.length() / 2;
            int pos = 0;

            byte[] buffer = new byte[total];
            for (int i = 0; i < total; i++) {

                int start = i * 2;

                buffer[i] = (byte) Integer.parseInt(
                        s.substring(start, start + 2), 16);
                pos++;
            }

            return new String(buffer, 0, pos, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     *
     * @param s 原串
     * @return
     */
    public static String convertStringToUTF8(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;

                    b = Character.toString(c).getBytes("utf-8");

                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append(Integer.toHexString(k).toUpperCase());
                        // sb.append("%" +Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }
}
