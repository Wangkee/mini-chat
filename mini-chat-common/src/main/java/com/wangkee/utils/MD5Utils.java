package com.wangkee.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5 加密工具类
 */
public class MD5Utils {

    /**
     * MD5混合加密
     * @param data: 待加密字符串
     * @param slat: 盐，用于混合md5加密
     */
    public static String encrypt(String data, String slat) {
        String base = data + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}