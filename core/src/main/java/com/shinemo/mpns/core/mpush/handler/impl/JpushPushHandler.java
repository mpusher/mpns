package com.shinemo.mpns.core.mpush.handler.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.core.jpush.service.JPushService;
import com.shinemo.mpns.core.mpush.handler.PushHandler;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer.SendPushType;
import com.shinemo.mpns.core.util.PushTypeUtil;

@Service("jpushPushHandler")
public class JpushPushHandler implements PushHandler{
	
	@Resource
	private JPushService jPushService;
	
	@Override
	public String invoke(PushType pushType, Payload payload, String userId, Map<AttributeKey, Option> optionList, SendPushType sendPushType) {
		jPushService.push(PushTypeUtil.convert(pushType), payload, userId);
		return null;
	}

	@Override
	public void setNext(PushHandler next) {
		
	}

	@Override
	public void push(String content, String userId, String msgId, Map<AttributeKey, Option> optionList) {
		
	}


	
}
