package com.f4Blog.basic.util;

import com.f4Blog.basic.exception.ServiceException;

import java.security.MessageDigest;
import java.util.Arrays;


/**
 * md5加盐
 * @author authstr
 */
public class Md5Salt {
    private static int hashcode(String v) {
        int h = 0;
        char[] value = v.toCharArray();
        if (h == 0 && value.length > 0) {
            char[] val = value;
            int i = 0;
            while (i < value.length) {
                h = 31 * h + val[i];
                ++i;
            }
        }
        return h;
    }

    public static String sec(String salt, String pwd) {
        int index = Md5Salt.hashcode(salt) % 4 + 1;
        switch (index) {
            case 1: {
                return Md5Salt.sec1(salt, pwd);
            }
            case 2: {
                return Md5Salt.sec2(salt, pwd);
            }
            case 3: {
                return Md5Salt.sec3(salt, pwd);
            }
            case 4: {
                return Md5Salt.sec4(salt, pwd);
            }
        }
        return Md5Salt.sec1(salt, pwd);
    }
    
    public static String sec(Integer salt,String pwd){
    	String saltInt=md5Hex(String.valueOf(salt));
    	return sec(saltInt,pwd);
    }


    public static String md5Hex(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            StringBuffer md5StrBuff = new StringBuffer();

            for(int i = 0; i < bs.length; ++i) {
                if (Integer.toHexString(255 & bs[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(255 & bs[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(255 & bs[i]));
                }
            }

            return md5StrBuff.toString();
        } catch (Exception var5) {
            throw new ServiceException("CoreExceptionEnum.ENCRYPT_ERROR");
        }
    }


    private static String sec1(String salt, String pwd) {
        int le = salt.length();
        String before = salt.substring(0, le / 2);
        String end = salt.substring(le / 2, le);
        md5Hex(String.valueOf(before) + pwd + end);
        return  md5Hex(String.valueOf(before) + pwd + end);
    }

    private static String sec2(String salt, String pwd) {
        int le = salt.length();
        String before = salt.substring(0, le / 2);
        String end = salt.substring(le / 2, le);
        return md5Hex(String.valueOf(end) + pwd + before);
    }

    private static String sec3(String salt, String pwd) {
        int len_pwd;
        int len_salt = salt.length();
        char[] longchars = len_salt > (len_pwd = pwd.length()) ? salt.toCharArray() : pwd.toCharArray();
        char[] shortchars = len_salt <= len_pwd ? salt.toCharArray() : pwd.toCharArray();
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < longchars.length) {
            sb.append(longchars[i]);
            if (shortchars.length > i) {
                sb.append(shortchars[i]);
            }
            ++i;
        }
        return md5Hex(sb.toString());
    }

    private static String sec4(String salt, String pwd) {
        char[] end = (String.valueOf(salt) + pwd).toCharArray();
        Arrays.sort(end);
        return md5Hex(new String(end));
    }

    
}
