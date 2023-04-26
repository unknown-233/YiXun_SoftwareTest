package com.yixun.yixun_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class TimeTrans {
    public static String myToString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        return strDate;
    }
    public static String myToDateString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    public static Date myToDate(String str)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = sdf.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myDate;
    }
    public static Date myToDate_1(String str)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date myDate = null;
        try {
            myDate = sdf.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myDate;
    }
    public static Date myToBeginningOfMonth(String str){
        str=str+"-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = sdf.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myDate;
    }
    public static Date myToEndOfMonth(String str){
        String year=str.substring(0, 4);
        String month = str.substring(5);
        String day=getDaysInMonth(year,month);
        str=str+"-"+day+" 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date myDate = null;
        try {
            myDate = sdf.parse(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myDate;
    }

    public static String getDaysInMonth(String year, String month) {
        // 将年份和月份字符串解析为整数
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);

        // 使用YearMonth类获取指定年月的天数
        YearMonth yearMonth = YearMonth.of(yearInt, monthInt);
        int days = yearMonth.lengthOfMonth();

        // 返回天数的字符串表示
        return String.valueOf(days);
    }
}
