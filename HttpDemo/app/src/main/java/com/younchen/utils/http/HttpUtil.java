package com.younchen.utils.http;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;
import okio.Okio;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.younchen.utils.http.callback.DownLoadCallBack;
import com.younchen.utils.http.callback.PrograssListner;
import com.younchen.utils.http.callback.ResultCallBack;

/**
 * see also https://github.com/square/okhttp/wiki/Recipes
 * 
 * @author younchen
 *
 */
@SuppressLint("HandlerLeak")
public class HttpUtil {

	private static HttpUtil httpManager;
	private static OkHttpClient client;
	private MyHttpHandler handler;
	private Gson gson;
	

	public static Call sendRequst(Request request, Callback callBack) {
		Call c = client.newCall(request);
		c.enqueue(callBack);
		return c;
	}

	private HttpUtil() {
		handler = new MyHttpHandler(Looper.getMainLooper());
		client = new OkHttpClient();
		client.setConnectTimeout(10, TimeUnit.SECONDS);
		gson = new Gson();
	}

	public static HttpUtil getInstance() {
		if (httpManager == null) {
			synchronized (HttpUtil.class) {
				httpManager = new HttpUtil();
			}
		}
		return httpManager;
	}

	/**
	 * 发送http请求
	 * 
	 * @param request
	 * @param callback
	 */
	public <T> void sendRequest(HttpRequest request, ResultCallBack<T> callback) {
		if (callback == null) {
			throw new NullPointerException("callback must not be null");
		}
		final ResultCallBack<T> myCallback = callback;
		Call c = client.newCall(request.getRequest());
		c.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				if (response.code() == 200) {
					String body = response.body().string();
					ObjectStruct<T> ost = new ObjectStruct<T>(
							MyHttpHandler.SEND_SUCCESS);
					ost.callBack = myCallback;
					ost.content = body;
					handler.sendMessage(ost.getMessage());
				} else {
					ObjectStruct<T> ost = new ObjectStruct<T>(
							MyHttpHandler.SEND_FAIL);
					ost.ex = new HttpException(response.code());
					ost.callBack = myCallback;
					handler.sendMessage(ost.getMessage());
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				ObjectStruct<T> ost = new ObjectStruct<T>(
						MyHttpHandler.SEND_FAIL);
				ost.ex = new HttpException(arg1);
				ost.obj = arg0;
				ost.callBack = myCallback;
				handler.sendMessage(ost.getMessage());
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public void downLoad(final HttpRequest request, DownLoadCallBack callBack) {
		Call c = client.newCall(request.getRequest());
		final DownLoadCallBack downLoadCallBack=callBack;
		c.enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				// TODO Auto-generated method stub
				File downloadedFile = new File(request.getFileDir(), request
						.getFileName());

				BufferedSink sink = Okio.buffer(new CountingSink(Okio
						.sink(downloadedFile), response.body().contentLength(),
						new PrograssListner() {
							@SuppressWarnings("unchecked")
							@Override
							public void onPrograss(int prograss) {
								// TODO Auto-generated method stub
								if(prograss<100){
									ObjectStruct ost = new ObjectStruct(
											MyHttpHandler.ON_DOWNLOAD_PROGRASS);
									ost.downLoadCallBack = downLoadCallBack;
									ost.prograss=prograss;
									handler.sendMessage(ost.getMessage());
								}else if(prograss==100){
									ObjectStruct ost = new ObjectStruct(
											MyHttpHandler.ON_DOWNLOAD_SUCESS);
									ost.downLoadCallBack = downLoadCallBack;
									handler.sendMessage(ost.getMessage());
								}
							}
						}));
				sink.writeAll(response.body().source());
				sink.close();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				ObjectStruct ost = new ObjectStruct(
						MyHttpHandler.ON_DOWNLOAD_SUCESS);
				ost.downLoadCallBack = downLoadCallBack;
				ost.ex=new HttpException(arg1);
				handler.sendMessage(ost.getMessage());
			}
		});
	}

	@SuppressWarnings("rawtypes")
	class MyHttpHandler extends Handler {

		public static final int SEND_SUCCESS = 1;
		public static final int SEND_FAIL = 2;
		public static final int ON_DOWNLOAD_PROGRASS=3;
		public static final int ON_DOWNLOAD_SUCESS=4;
		public static final int ON_DOWNLOAD_FAIL=5;

		public MyHttpHandler(Looper looper) {
			// TODO Auto-generated constructor stub
			super(looper);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == SEND_SUCCESS) {
				ObjectStruct ost = (ObjectStruct) msg.obj;
				if (ost.callBack.mType == String.class) {
					ost.callBack.onResponse(ost.content);
				} else {
					try {
						Object object = gson.fromJson(ost.content,
								ost.callBack.mType);
						ost.callBack.onResponse(object);
					} catch (Exception ex) {
						Request request = (Request) ost.obj;
						ost.callBack.onError(request, new HttpException(ex));
					}
				}
			} else if (msg.what == SEND_FAIL) {
				ObjectStruct ost = (ObjectStruct) msg.obj;
				Request request = (Request) ost.obj;
				ost.callBack.onError(request, ost.ex);
			} else if(msg.what==ON_DOWNLOAD_PROGRASS){
				ObjectStruct ost = (ObjectStruct) msg.obj;
				ost.downLoadCallBack.onDownLoading(ost.prograss);
			} else if(msg.what==ON_DOWNLOAD_SUCESS){
				ObjectStruct ost = (ObjectStruct) msg.obj;
				ost.downLoadCallBack.onDownLoadSuccess();
			} else if(msg.what==ON_DOWNLOAD_FAIL){
				ObjectStruct ost = (ObjectStruct) msg.obj;
				ost.downLoadCallBack.onDownLoadFail(ost.ex);
			}
		}
	}

	private class ObjectStruct<T> {
		private ResultCallBack<T> callBack;
		private DownLoadCallBack downLoadCallBack;
		private String content;
		private HttpException ex;
		private Object obj;
		private Message msg;
		private int prograss;
		private int state;

		public ObjectStruct(int STATE) {
			this.msg = new Message();
			this.state = STATE;
		}

		public Message getMessage() {
			this.msg.what = state;
			msg.obj = this;
			return msg;

		}
	}
}
