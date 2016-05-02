package com.younchen.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast类
 * 2015.4.16
 * @author longquan
 *
 */
public class ToastUtil {
	private static Toast mToast = null;
	
	private   Context context;
	
	private static ToastUtil toastUtil;
	
	private ToastUtil(Context context){
		this.context=context;
	}
	
	public static ToastUtil getInstance(Context context){
		
		if(toastUtil==null){
			toastUtil=new ToastUtil(context);
		}
		return toastUtil;
	}
	
	

	public void show(int msg) {
		try {
			show(context.getResources().getString(msg));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void show(String msg) {

		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (context == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(context, msg,
						Toast.LENGTH_LONG);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(context, msg,
						Toast.LENGTH_LONG);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


	public void showToast(Context context, String msg, int duration) {
		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (context == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(context, msg, duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(context, msg, duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void show(String msg, int duration) {
		try {
			if (TextUtils.isEmpty(msg)) {
				return;
			} else if (context == null) {
				return;
			}
			if (mToast == null) {
				mToast = Toast.makeText(context, msg,
						duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(context, msg,
						duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void show(int msg, int duration) {
		try {
			if (mToast == null) {
				mToast = Toast.makeText(context, msg,
						duration);
			} else {
				mToast.cancel();
				mToast = Toast.makeText(context, msg,
						duration);
			}
			mToast.setText(msg);
			mToast.show();
		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}
}
