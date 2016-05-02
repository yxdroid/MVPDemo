package com.younchen.utils.http;

import com.squareup.okhttp.Request;

public class HttpRequest {
	
	private Request request;
	private String fileDir;
	private String fileName;
	
	public HttpRequest(Request request){
		this.setRequest(request);
	}

	public HttpRequest(){
		
	}
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
}
