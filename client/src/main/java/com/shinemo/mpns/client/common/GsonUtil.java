package com.shinemo.mpns.client.common;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	private final static Gson gson       = new GsonBuilder().create();
	
    public static <T> String fromObj2Gson(T obj, Class<T> clazz) {
        if (null == obj)
            return null;
        return gson.toJson(obj, clazz);
    }

    public static <T> T fromGson2Obj(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json))
            return null;
        return gson.fromJson(json, clazz);
    }

    public static <T> String fromObj2Gson(T obj, Type t) {
        if (null == obj)
            return null;
        return gson.toJson(obj, t);
    }
    

    public static <T> T fromGson2Obj(String json, Type type) {
        if (StringUtils.isBlank(json))
            return null;
        return gson.<T> fromJson(json, type);
    }
    
	public static Map<String,Object> getJsonMap(String jsonStr){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(jsonStr)){
			Type typeOfT = new TypeToken<Map<String,Object>>(){}.getType();
			paramMap =gson.fromJson(jsonStr, typeOfT);
		}
		return paramMap;
	}
	
	
	public static String toJson(Object object){
		return gson.toJson(object);
	}
	
}