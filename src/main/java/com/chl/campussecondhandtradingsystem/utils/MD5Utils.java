package com.chl.campussecondhandtradingsystem.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    private final static String SALT = "c9049h24800l";
    public static String md5(String pass){
        String s = "" + SALT.substring(0, 5) + pass + SALT.substring(5);
        return DigestUtils.md5Hex(s);
    }
}
