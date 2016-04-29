package com.yxfang.mvpdemo.utils.http;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 15:41
 * ------------- Description -------------
 * ---------------------------------------
 */
public class HttpRequestUtil
{
    private static HttpRequestUtil instance;

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static synchronized HttpRequestUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (HttpRequestUtil.class)
            {
                instance = new HttpRequestUtil();
            }
        }
        return instance;
    }

    /**
     * http get 请求
     *
     * @param url
     * @param callback
     */
    public void getRequest(Context context, String url, final HttpRequestCallback callback)
    {
        Request request = new Request.Builder().tag(getTagByContext(context)).url(url).build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }


    /**
     * http post 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void postRequest(Context context, String url, @Nullable FormBody params, HttpRequestCallback callback)
    {
        Request request = new Request.Builder().tag(getTagByContext(context)).url(url).post(params).build();
        okHttpClient.newCall(request).enqueue(getCallback(context, callback));
    }

    /**
     * 通过context 生成http 请求tag
     * tag 用来标识 http 请求，可通过tag 来取消请求
     *
     * @param context
     * @return
     */
    private String getTagByContext(Context context)
    {
        return context != null ? context.getClass().getName() : null;
    }

    /**
     * 重新封装一层callback
     * 添加onStart 和 onFinish
     *
     * @param callback
     * @return
     */
    private HttpRequestCallback getCallback(final Context context, final HttpRequestCallback callback)
    {
        return new HttpRequestCallback()
        {
            @Override
            public void onStart()
            {
                if (callback != null)
                {
                    callback.onStart();
                }
            }

            @Override
            public void onFinish()
            {
                if (callback != null)
                {
                    callback.onFinish();
                }
            }

            @Override
            public void onFailure(final Call call, final IOException e)
            {
                if (callback != null)
                {
                    new Handler(context.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            callback.onFailure(call, e);
                            callback.onFinish();
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException
            {
                if (callback != null)
                {
                    new Handler(context.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                callback.onResponse(call, response);
                            }
                            catch (IOException e)
                            {
                            }
                            callback.onFinish();
                        }
                    });
                }
            }
        };
    }
}
