package com.yxfang.mvpdemo.model.user;

import android.content.Context;

import com.yxfang.mvpdemo.model.BaseModel;
import com.yxfang.mvpdemo.utils.http.HttpRequestCallback;
import com.yxfang.mvpdemo.utils.http.RequestParams;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 10:56
 * ------------- Description -------------
 * ---------------------------------------
 */
public class UserLoginModel extends BaseModel implements IUserLoginModel
{
    @Override
    public void userLogin(Context context, String account, String pwd, HttpRequestCallback callBack)
    {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("pwd", pwd);
        sendPostRequest(context, USER_LOGIN, params, callBack);
    }
}
