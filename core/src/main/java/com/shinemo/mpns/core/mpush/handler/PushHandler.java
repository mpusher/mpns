package com.shinemo.mpns.core.mpush.handler;

import java.util.Map;

import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer.SendPushType;

public interface PushHandler {
	
	public String invoke(PushType pushType,final Payload payload, String userId,final Map<AttributeKey, Option> optionList,SendPushType sendPushType);

	public void setNext(PushHandler next);

	void push(String content, String userId, String msgId, Map<AttributeKey, Option> optionList);
	
}
