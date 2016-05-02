package com.younchen.utils.http;

import java.io.Serializable;

/**
 * 
 * @author younchen
 *
 */
public class HttpException implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 规定如果是-1 说明是本地访问网络时出现错误,列如超时.
	 */
	private int responseCode;
	private Exception ex;


	public HttpException() {
		ex = new Exception();
	}
	
	public HttpException(int code){
		this.responseCode=code;
		ex=new Exception(code+" error!");
	}
	
	public HttpException(int code,String message){
		this.responseCode=code;
		this.ex=new Exception(message);
	}

	public HttpException(Exception ex) {
		this.responseCode = -1;
		this.ex = ex;
	}

	public String getMessage() {
		return ex.getMessage();
	}

	/**
	 * 请求代码，如：404，400 ，500等，在http访问服务器时如果出错，返回的状态值
	 * @return
	 */

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

}
