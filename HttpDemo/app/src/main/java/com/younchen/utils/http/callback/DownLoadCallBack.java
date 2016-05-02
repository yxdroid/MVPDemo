package com.younchen.utils.http.callback;

import com.younchen.utils.http.HttpException;

public interface DownLoadCallBack {

	public void onDownLoading(int prograss);
	
	public void onDownLoadSuccess();
	
	public void onDownLoadFail(HttpException ex);
}
