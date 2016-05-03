package com.yxfang.mvpdemo.utils.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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
    private static final String TAG = "HttpRequestUtil";

    private static HttpRequestUtil instance;

    private static OkHttpClient okHttpClient;

    private Handler httpHandler;

    private Gson gson;

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
        httpHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    /**
     * http get 请求
     *
     * @param url
     * @param callback
     */
    public void getRequest(Context context, String url, final HttpRequestCallback callback)
    {
        Request request = new Request.Builder().tag(getTagByContext(context)).url(url).get().build();
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
    private Callback getCallback(final Context context, final HttpRequestCallback callback)
    {
        if (callback != null)
        {
            callback.onStart();
        }

        return new Callback()
        {

            @Override
            public void onFailure(Call call, IOException e)
            {
                if (callback != null)
                {
                    HttpResult httpResult = new HttpResult(HttpHandler.HTTP_FAILURE);
                    httpResult.callback = callback;
                    httpResult.exception = new HttpException(e);
                    httpResult.call = call;
                    httpHandler.post(new HttpHandler(httpResult));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (callback != null)
                {
                    if (response.code() == 200)
                    {
                        HttpResult httpResult = new HttpResult(HttpHandler.HTTP_SUCCESS);
                        httpResult.callback = callback;
                        httpResult.response = response.body().string();
                        httpResult.call = call;
                        httpHandler.post(new HttpHandler(httpResult));
                    }
                    else
                    {
                        HttpResult httpResult = new HttpResult(HttpHandler.HTTP_FAILURE);
                        httpResult.callback = callback;
                        httpResult.call = call;
                        httpResult.exception = new HttpException(response.code());
                        httpHandler.post(new HttpHandler(httpResult));
                    }
                }
            }
        };
    }

    class HttpHandler implements Runnable
    {
        public static final int HTTP_SUCCESS = 1;
        public static final int HTTP_FAILURE = 2;

        private HttpResult httpResult;

        public HttpHandler(HttpResult httpResult)
        {
            this.httpResult = httpResult;
        }

        @Override
        public void run()
        {
            httpResult.callback.onFinish();

            if (httpResult.what == HTTP_SUCCESS)
            {
                // 当返回的类型是String
                if (httpResult.callback.type == String.class)
                {
                    httpResult.callback.onResponse(httpResult.response);
                }
                else
                {
                    try
                    {
                        Object obj = gson.fromJson(httpResult.response, httpResult.callback.type);
                        httpResult.callback.onResponse(obj);
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, httpResult.response);
                        // 解析异常
                        httpResult.callback.onFailure(httpResult.call, new HttpException(HttpException.EXCEPTION_DATA));
                    }
                }
            }
            else
            {
                httpResult.callback.onFailure(httpResult.call, httpResult.exception);
            }
        }
    }

    class HttpResult
    {
        private HttpRequestCallback callback;
        private String response;
        private HttpException exception;
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
