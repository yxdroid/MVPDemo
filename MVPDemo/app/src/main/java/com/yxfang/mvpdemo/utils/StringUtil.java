package com.yxfang.mvpdemo.utils;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 17:30
 * ------------- Description -------------
 * 字符串工具类
 * ---------------------------------------
 */
public class StringUtil
{
    public static boolean isEmpty(CharSequence str)
    {
        return str == null || str.length() == 0;
    }
}
