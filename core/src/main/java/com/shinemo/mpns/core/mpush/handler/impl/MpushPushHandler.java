package com.shinemo.mpns.core.mpush.handler.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.OfflineTypeEnum;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.core.async.InternalEventBus;
import com.shinemo.mpns.core.async.event.MpushErrorEvent;
import com.shinemo.mpns.core.mpush.handler.PushHandler;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer.SendPushType;
import com.shinemo.mpns.core.mpush.service.MsgIdService;
import com.shinemo.mpns.core.mpush.service.impl.MsgIdServiceImpl;
import com.shinemo.mpns.core.mpush.service.impl.PushServiceImpl;
import com.shinemo.mpns.core.util.PushTypeUtil;
import com.mpush.api.PushContent;
import com.mpush.api.PushSender;
import com.mpush.push.PushClient;

@Service("mpushPushHandler")
public class MpushPushHandler implements PushHandler{
	
	private static final PushClient pushClient = new PushClient();
	
	private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
	
	private PushHandler next;
	
	@Resource
	private InternalEventBus internalEventBus;
	
	@Resource
	private MsgIdService msgIdService;
	
	static{
		pushClient.start();
		log.info("push client init success!");
	}
	
	@Override
	public String invoke(final PushType pushType, final Payload payload, String userId, final Map<AttributeKey, Option> optionList, final SendPushType sendPushType) {
		if(sendPushType.equals(SendPushType.MPUSH)||sendPushType.equals(SendPushType.JPUSHANDMPUSH)){
			final String msgId = msgIdService.generateMsgId(MsgIdServiceImpl.bizId);
			final PushContent pushContent = PushContent.build(PushTypeUtil.convert(pushType), GsonUtil.toJson(payload));
	    	pushContent.setMsgId(msgId);
	    	final String jsonPushContent = GsonUtil.toJson(pushContent);
			pushClient.send(jsonPushContent, userId, new PushSender.Callback() {
				@Override
				public void onTimeout(String userId) {
					log.info("userId:{} send:{} timeout,msgId:{},pushType:{}",userId,jsonPushContent,msgId,sendPushType.name());
					if(sendPushType.equals(SendPushType.MPUSH)){
						internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.TIMEOUT, msgId, jsonPushContent,optionList));
					}else{
						next.invoke(pushType, payload, userId, optionList, sendPushType);
					}
				}
				
				@Override
				public void onSuccess(String userId) {
					log.info("userId:{} send:{} success,msgId:{},pushType:{}",userId,jsonPushContent,msgId,sendPushType.name());
				}
				
				@Override
				public void onOffline(String userId) {
					log.info("userId:{} send:{} offline,msgId:{},pushType:{}",userId,jsonPushContent,msgId,sendPushType.name());
					if(sendPushType.equals(SendPushType.MPUSH)){
						internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.OFFLINE, msgId, jsonPushContent,optionList));
					}else{
						next.invoke(pushType, payload, userId, optionList, sendPushType);
					}
				}
				
				@Override
				public void onFailure(String userId) {
					log.info("userId:{} send:{} failure,msgId:{},pushType:{}",userId,jsonPushContent,msgId,sendPushType.name());
					if(sendPushType.equals(SendPushType.MPUSH)){
						internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.FAILED, msgId, jsonPushContent,optionList));
					}else{
						next.invoke(pushType, payload, userId, optionList, sendPushType);
					}
				}
			});
		}else{
			if(next !=null){
				return next.invoke(pushType, payload, userId, optionList, sendPushType);
			}
		}
		return null;
	}

	@Override
	public void setNext(PushHandler next) {
		this.next = next;
	}
	
	@Override
	public void push(final String content,String userId,final String msgId,final Map<AttributeKey, Option> optionList){
		pushClient.send(content, userId, new PushSender.Callback() {
			@Override
			public void onTimeout(String userId) {
				log.info("userId:{} send:{} timeout,msgId:{}",userId,content,msgId);
				internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.TIMEOUT, msgId, content,optionList));
			}
			
			@Override
			public void onSuccess(String userId) {
				log.info("userId:{} send:{} success,msgId:{}",userId,content,msgId);
			}
			
			@Override
			public void onOffline(String userId) {
				log.info("userId:{} send:{} offline,msgId:{}",userId,content,msgId);
				internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.OFFLINE, msgId, content,optionList));
			}
			
			@Override
			public void onFailure(String userId) {
				log.info("userId:{} send:{} failure,msgId:{}",userId,content,msgId);
				internalEventBus.postAsync(new MpushErrorEvent(userId, OfflineTypeEnum.FAILED, msgId, content,optionList));
			}
		});
	}
	
}
