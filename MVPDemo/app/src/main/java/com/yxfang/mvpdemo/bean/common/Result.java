package com.yxfang.mvpdemo.bean.common;

import lombok.Data;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 15:27
 * ------------- Description -------------
 * ---------------------------------------
 */
@Data
public class Result<T>
{
    public static final int RESULT_OK = 200;

    private int code;
    private String msg;
    private T data;
}
