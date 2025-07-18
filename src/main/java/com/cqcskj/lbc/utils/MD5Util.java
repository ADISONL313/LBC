package com.cqcskj.lbc.utils;

import java.security.MessageDigest;
public class MD5Util {
    public static String MD5Encoder(String browserPwd){
        try{
            MessageDigest md5=MessageDigest.getInstance("MD5");
            byte[] messageDigest=md5.digest(browserPwd.getBytes());
            StringBuilder stringBuilder=new StringBuilder();
            for (byte b:messageDigest){
                String hex=Integer.toHexString(0xff & b);
                if(hex.length()==1){
                    stringBuilder.append('0');
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
