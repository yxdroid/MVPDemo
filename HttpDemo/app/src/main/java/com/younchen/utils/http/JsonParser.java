package com.younchen.utils.http;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * @author kang
 * @date 2014年11月27日
 * @description
 */
public class JsonParser {
	public static Gson gson = new Gson();

	public static <T> T parse(String jsonData, Class<T> clazz) {
		T t = null;
		try {
			t = gson.fromJson(jsonData, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				t = clazz.newInstance();
				Method setMethod = clazz.getMethod("setCode", int.class);
				setMethod.invoke(t, -1);
				setMethod = clazz.getMethod("setMsg", String.class);
				setMethod.invoke(t, "数据反回格式错误");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();

			return t;
		}
		return t;
	}

	public static <T> T jsonToObject(String jsonData, Class<T> clazz) {
		try {
			return gson.fromJson(jsonData, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> changeGsonToList(String gsonString,Class<T> clazz) {
		List<T> lst = new ArrayList<T>();

		JsonArray jsonArray = new com.google.gson.JsonParser().parse(gsonString)
				.getAsJsonArray();

		for (final JsonElement elem : jsonArray) {
			lst.add(new Gson().fromJson(elem, clazz));
		}
		return lst;
	}

	public static String toJsonStr(Object object) {
		return gson.toJson(object);
	}

	public static <T> List<Map<String, T>> changeGsonToListMaps(
			String gsonString) {
		List<Map<String, T>> list = null;
		list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
		return list;
	}

	public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
		Map<String, T> map = null;
		map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
		return map;
	}
}
