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
    /**
     * 显示提示信息
     *
     * @param msg
     */
    void showTip(String msg);

    /**
     * 成功加载结果（默认回调方法，一般在子类里自定义）
     *
     * @param result
     */
    void loadSuccess(Result result);

    /**
     * 失败加载结果（默认回调方法，一般在子类里自定义）
     *
     * @param errorMsg
     */
    void loadFailure(String errorMsg);

    /**
     * 显示加载对话框
     */
    void showLoadingDialog();

    /**
     * 关闭加载对话框
     */
    void closeLoadingDialog();
}
