package com.chl.campussecondhandtradingsystem.utils;

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
}
