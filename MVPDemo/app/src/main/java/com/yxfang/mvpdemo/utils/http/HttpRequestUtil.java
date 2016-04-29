package com.yxfang.mvpdemo.utils.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

    private static OkHttpClient okHttpClient;

    private HttpHandler httpHandler;

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

    public HttpRequestUtil()
    {
        okHttpClient = new OkHttpClient();
        httpHandler = new HttpHandler(Looper.getMainLooper());
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
                    HttpResult httpResult = new HttpResult(HttpHandler.HTTP_FAILURE);
                    httpResult.callback = callback;
                    httpResult.exception = e;
                    httpResult.call = call;
                    httpHandler.sendMessage(httpResult.getMessage());
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException
            {
                if (callback != null)
                {
                    HttpResult httpResult = new HttpResult(HttpHandler.HTTP_SUCCESS);
                    httpResult.callback = callback;
                    httpResult.call = call;
                    httpResult.response = response;
                    httpHandler.sendMessage(httpResult.getMessage());
                }
            }
        };
    }

    class HttpHandler extends Handler
    {
        public static final int HTTP_SUCCESS = 1;
        public static final int HTTP_FAILURE = 2;

        public HttpHandler(Looper looper)
        {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg)
        {
            HttpResult httpResult = (HttpResult) msg.obj;
            if (msg.what == HTTP_SUCCESS)
            {
                try
                {
                    httpResult.callback.onResponse(httpResult.call, httpResult.response);
                }
                catch (IOException e)
                {
                }
                httpResult.callback.onFinish();
            }
            else
            {
                httpResult.callback.onFailure(httpResult.call, httpResult.exception);
                httpResult.callback.onFinish();
            }
        }
    }

    class HttpResult
    {
        private HttpRequestCallback callback;
        private Response response;
        private IOException exception;
        private Call call;
        private int what;

        private Message msg;

        public HttpResult(int what)
        {
            this.msg = new Message();
            this.what = what;
        }

        public Message getMessage()
        {
            this.msg.what = what;
            msg.obj = this;
            return msg;

        }
    }
}
