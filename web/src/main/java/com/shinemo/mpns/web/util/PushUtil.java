package com.shinemo.mpns.web.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class PushUtil {
	
	public static Map<String,String> getFeedMap(){
		Map<String,String> map = Maps.newHashMap();
		map.put("notificationId", "303061");
		map.put("feedId", "303061");
		map.put("notificationInfo", "成都市草地上成都市草地上成都市草地上成都市草地上成都市草地上>成都市草地上成都市草地上成都市草地上成都市草地上成都市草地上成都市草地上");
		map.put("messageType", "messageNewsbox");
		map.put("openId", "42");
		return map;
	}
	
}
