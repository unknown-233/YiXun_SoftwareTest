package com.yixun.yixun_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTrans {
    public static String myToString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
}
