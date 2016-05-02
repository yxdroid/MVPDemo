package com.yxfang.mvpdemo.utils.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 19:15
 * ------------- Description -------------
 * ---------------------------------------
 */
public abstract class HttpRequestCallback<T>
{
    public Type type;

    public HttpRequestCallback()
    {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    /**
     * 开始请求
     */
    public abstract void onStart();

    /**
     * 请求结束
     */
    public abstract void onFinish();

    /**
     * 响应回调
     *
     * @param t
     */
    public abstract void onResponse(T t);

    /**
     * 请求异常错误回调
     *
     * @param call
     * @param e
     */
    public abstract void onFailure(Call call, HttpException e);
}
