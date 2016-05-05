package com.yxfang.mvpdemo.presenter.user;

import android.content.Context;

import com.yxfang.mvpdemo.bean.common.Result;
import com.yxfang.mvpdemo.bean.user.User;
import com.yxfang.mvpdemo.model.user.IUserLoginModel;
import com.yxfang.mvpdemo.model.user.UserLoginModel;
import com.yxfang.mvpdemo.presenter.BasePresenter;
import com.yxfang.mvpdemo.utils.StringUtil;
import com.yxfang.mvpdemo.utils.http.HttpException;
import com.yxfang.mvpdemo.utils.http.HttpRequestCallback;
import com.yxfang.mvpdemo.view.user.IUserLoginView;

import java.util.List;

import okhttp3.Call;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 10:58
 * ------------- Description -------------
 * view 和 model 中间件
 * ---------------------------------------
 */
public class UserLoginPresenter extends BasePresenter
{
    private IUserLoginView userLoginView;
    private IUserLoginModel userLoginModel;

    public UserLoginPresenter(IUserLoginView userLoginView)
    {
        this.userLoginView = userLoginView;
        this.userLoginModel = new UserLoginModel();
    }

    /**
     * 用户开始登录
     *
     * @param context
     * @param account
     * @param pwd
     */
    public void login(final Context context, String account, String pwd)
    {
        if (StringUtil.isEmpty(account))
        {
            userLoginView.showTip("请输入账号");
            return;
        }

        if (StringUtil.isEmpty(pwd))
        {
            userLoginView.showTip("请输入密码");
            return;
        }

        userLoginModel.userLogin(context, account, pwd, new HttpRequestCallback<Result<User>>()
        {


            @Override
            public void onStart()
            {
                userLoginView.showLoadingDialog();
            }

            @Override
            public void onFinish()
            {
                userLoginView.closeLoadingDialog();
            }

            @Override
            public void onResponse(Result<User> user)
            {
                userLoginView.onLogin(user);
            }

            @Override
            public void onFailure(Call call, HttpException e)
            {
                userLoginView.showTip(e.getMessage());
            }
        });
    }

    /**
     * 获取用户列表
     *
     * @param context
     */
    public void getUserList(Context context)
    {
        userLoginModel.getUserList(context, new HttpRequestCallback<Result<List<User>>>()
        {
            @Override
            public void onStart()
            {
                userLoginView.showLoadingDialog();
            }

            @Override
            public void onFinish()
            {
                userLoginView.closeLoadingDialog();
            }

            @Override
            public void onResponse(Result<List<User>> userList)
            {
                userLoginView.onGetUserList(userList);
            }

            @Override
            public void onFailure(Call call, HttpException e)
            {
                userLoginView.showTip(e.getMessage());
            }
        });
    }
}
