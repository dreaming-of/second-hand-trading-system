package com.chl.campussecondhandtradingsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MyUtils {
    private static final String split = "-";

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getChatId(int fromId, int toId){
        if (fromId < toId){
            return fromId + split + toId;
        }else{
            return toId + split + fromId;
        }
    }

    public static String getOrderId(){
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String nowTime = sd.format(date);
        String uuid = getUUID();
        return nowTime + uuid.substring(0, 6);
    }
}
