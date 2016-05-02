package com.example.okhttpdemo;

import java.io.File;
import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.younchen.entity.Foo;
import com.younchen.utils.FileUtil;
import com.younchen.utils.ToastUtil;
import com.younchen.utils.http.FileDiscription;
import com.younchen.utils.http.HttpException;
import com.younchen.utils.http.HttpRequest;
import com.younchen.utils.http.HttpRequestBuilder;
import com.younchen.utils.http.HttpRequestBuilder.HttpMethod;
import com.younchen.utils.http.HttpUtil;
import com.younchen.utils.http.callback.DownLoadCallBack;
import com.younchen.utils.http.callback.PrograssListner;
import com.younchen.utils.http.callback.ResultCallBack;

public class MainActivity extends ActionBarActivity {

	private File cameraFile;
	private final int REQUEST_CODE_LOCAL = 201;
	private final int REQUEST_CODE_CAMERA = 202;

	private TextView textView;
	private ProgressBar progressBar;

	private FileUploadListener fileUploadListener;
	private String filePath;
	// private AsyncHttpRequest uploadRequest;

	private String url = "http://192.168.1.41:8080/titan/test/index";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		filePath=FileUtil.PROJECT_PATH;
		
		textView = (TextView) findViewById(R.id.txt_file);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		fileUploadListener = new FileUploadListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void selectPicFromCamera() {
		if (!FileUtil.getSDCardMount()) {
			ToastUtil.getInstance(this).show("SD卡不存在，不能拍照");
			return;
		}
		String tempImgDir = FileUtil.IMAGE_URL + "/" + new Date().getTime()
				+ ".jpg";
		cameraFile = new File(tempImgDir);
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
						MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				REQUEST_CODE_CAMERA);
	}

	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}

	/**
	 * onActivityResult
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		try {

			if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						sendPicByUri(selectedImage);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 根据图库图片uri发送图片
	 * 
	 * @param selectedImage
	 */
	private void sendPicByUri(Uri selectedImage) {

		Cursor cursor = getContentResolver().query(selectedImage, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				ToastUtil.getInstance(this).show("找不到图片");
				return;
			}
			textView.setText(picturePath);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				ToastUtil.getInstance(this).show("找不到图片");
				return;
			}
			textView.setText(file.getAbsolutePath());
		}

	}

	public void selectPic(View v) {
		selectPicFromLocal();
	}

	/**
	 * upload事例
	 * 
	 * @param v
	 */
	public void upload(View v) {
		fileUploadSample();
	}

	public void cancel(View v) {
		getSample();
	
	}
	
	public void downLoad(View v){
		downLoad();
	}

	/**
	 * get 请求
	 */
	private void getSample() {

		HttpRequest request = new HttpRequestBuilder().url(url)
				.addHeader("cookie", "df").addParams("key", "value")
				.method(HttpMethod.GET).build();

		HttpUtil.getInstance().sendRequest(request, new ResultCallBack<Foo>() {

			@Override
			public void onResponse(Foo response) {
				// TODO Auto-generated method stub
				textView.setText(response.getName());
			}

			@Override
			public void onError(Request request, HttpException httpException) {
				// TODO Auto-generated method stub
				textView.setText("requestCode:"+httpException.getResponseCode()+"  ErrorMessage:"+httpException.getMessage());
			}
		});
	}

	/**
	 * post 请求
	 */
	private void postSample() {

		HttpRequest request = new HttpRequestBuilder()
				.url("http://www.oschina.net/action/user/hash_login")
				.addHeader("cookie", "df").addParams("email", "KTVyin@163.com")
				.addParams("pwd", "2D7DFB84C1FCD0DF718BFE1E802816B1D4DC8D66")
				.method(HttpMethod.POST).build();

		HttpUtil.getInstance().sendRequest(request,
				new ResultCallBack<String>() {

					@Override
					public void onError(Request request,
							HttpException httpException) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						textView.setText(response);
					}
				});

	}
	
	/**
	 * 文件下载
	 */
	private void downLoad(){
		HttpRequest request= new HttpRequestBuilder()
		.url("http://img.taopic.com/uploads/allimg/130501/240451-13050106450911.jpg")
		.method(HttpMethod.GET).downLoadPath(filePath, String.valueOf(new Date().getTime())+".jpg").build();
		
		HttpUtil.getInstance().downLoad(request, new DownLoadCallBack() {
			
			@Override
			public void onDownLoading(int prograss) {
				// TODO Auto-generated method stub
				textView.setText(prograss+"%");
				progressBar.setProgress(prograss);
			}
			
			@Override
			public void onDownLoadSuccess() {
				// TODO Auto-generated method stub
				textView.setText("down load success");
			}

			@Override
			public void onDownLoadFail(HttpException ex) {
				// TODO Auto-generated method stub
				textView.setText(ex.getMessage());
			}
			
		});
	}

	/**
	 * 文件上传
	 */
	private void fileUploadSample() {
		if (!TextUtils.isEmpty(textView.getText())) {
			File file = new File(textView.getText().toString());
			final String url = "http://192.168.1.41:8080/titan/test/upload";

			HttpRequest request = new HttpRequestBuilder()
					.url(url)
					.method(HttpMethod.POST)
					.addFile(
							new FileDiscription(file)
									.setUploadPrograssListener(fileUploadListener))
					.build();

			HttpUtil.getInstance().sendRequest(request,
					new ResultCallBack<String>() {

						@Override
						public void onError(Request request,
								HttpException httpException) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onResponse(String response) {
							// TODO Auto-generated method stub
							textView.setText(response);
						}
					});
		}
	}

	class FileUploadListener implements PrograssListner {

		@Override
		public void onPrograss(int prograss) {
			// TODO Auto-generated method stub
			progressBar.setProgress(prograss);
		}

	}

}
