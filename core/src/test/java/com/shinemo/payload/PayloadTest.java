package com.shinemo.payload;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.mpush.domain.CustomPushPayload;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory.MessageType;
import com.mpush.api.PushContent;
import com.mpush.api.PushContent.PushType;
import com.mpush.tools.Jsons;

public class PayloadTest {
	
	@Test
	public void customTest(){
		
		Map<String, String> map = Maps.newHashMap();
		map.put("key1", "value1");
		map.put("key2", "value2");
		
		CustomPushPayload customPayload = PayloadFactory.create(MessageType.CUSTOM);
		customPayload.putAll(map);
		
		PushContent pushContent = PushContent.build(PushType.MESSAGE, Jsons.toJson(customPayload));
		
		System.out.println(Jsons.toJson(pushContent));
	}
	
	@Test
	public void notificationTest(){
		NotificationPushPayload payload = PayloadFactory.create(MessageType.NOTIFICATION);
		Map<String,String> map = Maps.newHashMap();
		map.put("messageType", "map");
		payload.setContent("content");
		payload.setTitle("title").setExtras(map);
		
		payload.initContentType();

		PushContent pushContent = PushContent.build(PushType.NOTIFICATION, Jsons.toJson(payload));
		
		System.out.println(Jsons.toJson(pushContent));
	}
	
	@Test
	public void gsonTest(){
		NotificationPushPayload payload = PayloadFactory.create(MessageType.NOTIFICATION);
		Map<String,String> map = Maps.newHashMap();
		map.put("messageType", "map");
		payload.setContent("");
		payload.setExtras(map);
		payload.initContentType();
		System.out.println(GsonUtil.toJson(payload));
	}
	

}
