package com.yxfang.mvpdemo.presenter;

import com.alibaba.fastjson.TypeReference;
import com.yxfang.mvpdemo.bean.common.Result;
import com.yxfang.mvpdemo.utils.http.JsonAndObject;
import com.yxfang.mvpdemo.view.base.IBaseView;

import java.io.IOException;

import okhttp3.Response;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 17:11
 * ------------- Description -------------
 * <p/>
 * ---------------------------------------
 */
public class BasePresenter
{

    /**
     * 处理返回结果
     *
     * @param response
     * @param t
     * @param baseView
     * @param <T>
     */
    protected <T> void handleOnResponse(Response response, TypeReference<T> t, IBaseView baseView)
    {
        if (response.isSuccessful())
        {
            try
            {
                Result<T> result = (Result<T>) JsonAndObject.json2Object(response.body().toString(), t);
                baseView.loadSuccess(result);
            }
            catch (Exception e)
            {
                baseView.loadFailure("数据异常，请稍后再试");
            }
        }
        else
        {
            baseView.loadFailure("请求异常，请稍后再试");
        }
    }

    /**
     * 请求网路异常
     *
     * @param e
     * @param baseView
     */
    protected void handleOnFailure(IOException e, IBaseView baseView)
    {
        baseView.loadFailure("网络异常，请检查网路设置");
    }
}
