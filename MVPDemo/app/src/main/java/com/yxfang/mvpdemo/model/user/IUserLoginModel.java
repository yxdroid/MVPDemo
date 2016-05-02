package com.yxfang.mvpdemo.model.user;

import android.content.Context;

import com.yxfang.mvpdemo.utils.http.HttpRequestCallback;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 10:56
 * ------------- Description -------------
 * ---------------------------------------
 */
public interface IUserLoginModel
{
    void userLogin(Context context, String account, String pwd, HttpRequestCallback callBack);

    void getUserList(Context context, HttpRequestCallback callback);
}
