package com.younchen.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author kang
 */
public class BytesUtil {

	/**
	 * @param in
	 * @param length
	 * @return bytes or null if count <= 0 or any other reason lead to fail
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream in, int length) throws IOException {
		if (in == null) {
			return null;
		}
		if (length <= 0) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];
			int len;
			while ((len = in.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		}
		byte[] buffer = new byte[length];
		int len, total = 0;
		while ((len = in.read(buffer, total, buffer.length - total)) != -1) {
			total += len;
			if (total == length) {
				return buffer;
			}
		}
		return null;
	}

	/**
	 * @param in
	 * @param offset
	 * @param length
	 * @return bytes or null
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream in, int offset, int length) throws IOException {
		if (in == null || offset < 0 || length <= 0) {
			return null;
		}

		long totalSkip = 0;
		long actualSkip = 0;
		while (actualSkip >= 0 && totalSkip < offset) {
			actualSkip = in.skip(offset - totalSkip);
			totalSkip += actualSkip;
		}
		return readBytes(in, length);
	}

	public static String inputStream2String(InputStream is) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 小端�?build a short from the data offset
	 * 
	 * @param data
	 * @return
	 */
	public static short getShort(byte[] data) {
		return getShort(data, 0);
	}

	/**
	 * 小端�?build a short from the data offset
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static short getShort(byte[] data, int offset) {
		short result = 0;
		short mask = 0xff;
		for (int i = 0; i < 2; i++) {
			int temp = data[offset + i] & mask;
			temp <<= i * 8;
			result |= temp;
		}

		return result;
	}

	/**
	 * 小端�?build a int from the data offset
	 * 
	 * @param data
	 * @return
	 */
	public static int getInt(byte[] data) {
		return getInt(data, 0);
	}

	/**
	 * 小端�?build a int from the data offset
	 * 
	 * @param data
	 * @param offset
	 * @return
	 */
	public static int getInt(byte[] data, int offset) {
		int result = 0;
		int mask = 0xff;
		for (int i = 0; i < 4; i++) {
			int temp = data[offset + i] & mask;
			temp <<= i * 8;
			result |= temp;
		}
		return result;
	}
}
