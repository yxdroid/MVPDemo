package com.yxfang.mvpdemo.utils.http;

import okhttp3.FormBody;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 16:55
 * ------------- Description -------------
 * ---------------------------------------
 */
public class RequestParams
{

    private FormBody.Builder builder;

    public RequestParams()
    {
        builder = new FormBody.Builder();
    }

    public void put(String key, String value)
    {
        builder.add(key, value);
    }

    public FormBody toParams()
    {
        return builder.build();
    }
}
