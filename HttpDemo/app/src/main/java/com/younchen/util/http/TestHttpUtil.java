package com.younchen.util.http;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.squareup.okhttp.Request;
import com.younchen.entity.Foo;
import com.younchen.utils.http.OkHttpClientManager.ResultCallback;

public class TestHttpUtil extends AndroidTestCase {
	
	private String url="http://192.168.1.41:8080/titan/test/index";
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public void testGet() {

//		HttpRequestBuilder builder=new HttpRequestBuilder();
//		Request request=builder.addParams("fuck", "deam").method(HttpMethod.GET).url(url)
//		.build();
//		
//		HttpRequestHandler handler=new HttpRequestHandler(request,new HttpCallBack() {
//			
//			@Override
//			public void onSuccess(String body) {
//				// TODO Auto-generated method stub
//				System.out.println(body);
//			}
//			
//			@Override
//			public void onFail(String message) {
//				// TODO Auto-generated method stub
//				System.err.println(message);
//			}
//		});
//		handler.execute();
//		try {
//			Thread.sleep(50000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
//	public void testGet() {
//		try {
//			OkHttpClientManager.getInstance().getGetDelegate()
//					.getAsyn(url, new Min());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	class Min extends ResultCallback<Foo> {

		@Override
		public void onError(Request request, Exception e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onResponse(Foo myfoo) {
			// TODO Auto-generated method stub
			System.out.println(myfoo);
			Assert.assertNotNull(myfoo);
		}

	}
}
