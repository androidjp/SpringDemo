package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Format
 * Created by androidjp on 2017/1/10.
 */
public class DateFormatUtil {

    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return format.format(date);
    }

    public static String formatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }


    public static String formatDateTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
        return format.format(date);
    }

    public static String formatFull(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss E a");
        return format.format(date);
    }

    public static String getMonthDay(Date date){
        SimpleDateFormat format = new SimpleDateFormat("d");
        return "这是一个月中的第"+format.format(date)+"天";
    }

}
