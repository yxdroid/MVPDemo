package com.yxfang.mvpdemo.view.user;

import com.yxfang.mvpdemo.view.base.IBaseView;

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
}
