package com.yxfang.mvpdemo.utils.http;

import okhttp3.Callback;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 19:15
 * ------------- Description -------------
 * ---------------------------------------
 */
public interface HttpRequestCallback extends Callback
{
    /**
     * 开始请求
     */
    void onStart();

    /**
     * 请求结束
     */
    void onFinish();
}
