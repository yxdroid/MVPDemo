package com.yxfang.mvpdemo.view.user;

import com.yxfang.mvpdemo.bean.common.Result;
import com.yxfang.mvpdemo.bean.user.User;
import com.yxfang.mvpdemo.view.base.IBaseView;

import java.util.List;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 10:58
 * ------------- Description -------------
 * ---------------------------------------
 */
public interface IUserLoginView extends IBaseView
{
    /**
     * 获取输入账号
     *
     * @return
     */
    String getAccount();

    /**
     * 获取输入的密码
     *
     * @return
     */
    String getPwd();

    /**
     * 登录回调
     *
     * @param result
     */
    void onLogin(Result<User> result);

    /**
     * 获取用户列表（只是个案例）
     *
     * @param result
     */
    void onGetUserList(Result<List<User>> result);
}
