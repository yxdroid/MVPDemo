package com.yxfang.mvpdemo.view.base;

import com.yxfang.mvpdemo.bean.common.Result;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 15:22
 * ------------- Description -------------
 * ---------------------------------------
 */
public interface IBaseView
{
    void showTip(String msg);

    void loadSuccess(Result result);

    void loadFailure(String errorMsg);

    void showLoadingDialog();

    void closeLoadingDialog();
}
