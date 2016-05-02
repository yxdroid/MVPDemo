package com.younchen.utils.http;

import java.io.File;

import com.younchen.utils.http.callback.PrograssListner;


/**
 * 文件上传功能，的文件描述实体
 * 
 * @author longquan
 *
 */
public class FileDiscription {

	private File file;
	private String mediaType;
	private String charSet;
	private String fileName;
	private String key;

	private PrograssListner uploadListener;

	public FileDiscription(String fileName, String mediaType, File file,
			String charSet, String key) {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		if (key == null) {
			key = "file";
		}
		this.file = file;
		this.mediaType = mediaType;
		this.fileName = fileName;
		this.charSet = charSet;
		this.setKey(key);
	}

	/**
	 * 
	 * @param file
	 * @param key
	 *            后端接受时的key
	 */
	public FileDiscription(File file, String key) {
		this(file.getName(), "application/octet-stream", file, "UTF-8", key);
	}

	/**
	 * 
	 * @param file
	 */
	public FileDiscription(File file) {
		this(file.getName(), "application/octet-stream", file, "UTF-8", "file");
	}

	public FileDiscription(File file, String key, String type) {
		this(file.getName(), type, file, "UTF-8", key);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public FileDiscription setUploadPrograssListener(PrograssListner listener) {
		this.uploadListener = listener;
		return this;
	}

	public PrograssListner getUploadPrograssListener() {
		return uploadListener;
	}

}
