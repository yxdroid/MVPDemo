package com.yxfang.mvpdemo.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * User: yxfang
 * Date: 2016-04-29
 * Time: 15:41
 * ------------- Description -------------
 * json与object互换类
 * ---------------------------------------
 */
public class JsonAndObject
{

    /**
     * 对象 ===>> json
     *
     * @param object 待转换的对象
     * @return 类转换后的json数据
     * @throws IllegalAccessException 异常
     * @throws InstantiationException 异常
     */
    public static String object2Json(Object object) throws InstantiationException, IllegalAccessException
    {
        return JSONArray.toJSONString(object);
    }

    /**
     * * json ===>> 对象
     *
     * @param <T>   返回的泛型
     * @param json  json数据
     * @param clazz 对象的类全路径
     * @return 返回的对象
     */
    public static <T> T json2Object(String json, Class<T> clazz)
    {
        return JSONObject.toJavaObject(JSON.parseObject(json), clazz);
    }

    /**
     * json ===>> 对象
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T json2Object(String json, TypeReference<T> type)
    {
        return JSON.parseObject(json, type);
    }

    public static <T> List<T> json2Array(String json, Class<T> clazz)
    {
        return JSON.parseArray(json, clazz);
    }
}
