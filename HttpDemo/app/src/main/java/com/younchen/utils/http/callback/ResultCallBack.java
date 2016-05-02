package com.younchen.utils.http.callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.younchen.utils.http.HttpException;

public abstract class ResultCallBack<T>
{
    public Type mType;

    public ResultCallBack()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public abstract void onError(Request request, HttpException httpException);

    public abstract void onResponse(T response);
}
