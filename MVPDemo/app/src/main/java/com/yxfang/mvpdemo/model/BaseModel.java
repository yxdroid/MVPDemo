package com.yxfang.mvpdemo.model;

import android.content.Context;

import com.yxfang.mvpdemo.utils.http.HttpRequestCallback;
import com.yxfang.mvpdemo.utils.http.HttpRequestUtil;
import com.yxfang.mvpdemo.utils.http.RequestParams;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 16:25
 * ------------- Description -------------
 * ---------------------------------------
 */
public abstract class BaseModel implements IBaseModel
{
    /**
     * 发送http get 请求
     *
     * @param context
     * @param url
     * @param callback
     */
    protected void sendGetRequest(Context context, String url, HttpRequestCallback callback)
    {
        HttpRequestUtil.getInstance().getRequest(context, getAbsUrl(url), callback);
    }

    /**
     * 发送http post 请求
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    protected void sendPostRequest(Context context, String url, RequestParams params, HttpRequestCallback callback)
    {
        HttpRequestUtil.getInstance().postRequest(context, getAbsUrl(url), params == null ? null : params.toParams(), callback);
    }

    private String getAbsUrl(String url)
    {
        return SERVER_ADDRESS + url;
    }
}
