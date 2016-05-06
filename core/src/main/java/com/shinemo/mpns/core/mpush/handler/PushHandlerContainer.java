package com.shinemo.mpns.core.mpush.handler;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;

@Service("pushHandlerContainer")
public class PushHandlerContainer{

	@Resource
	private PushHandler jpushPushHandler;
	@Resource
	private PushHandler mpushPushHandler;
	
	@PostConstruct
	public void init(){
		mpushPushHandler.setNext(jpushPushHandler);
	}
	
	public String invoke(PushType pushType,final Payload payload, String userId,final Map<AttributeKey, Option> optionList) {
		return mpushPushHandler.invoke(pushType, payload, userId, optionList,sendType(optionList));
	}
	
	public void push(String userId, String content, String msgId) {
		mpushPushHandler.push(content,userId,msgId,null);
	}
	
	public String invoke(PushType pushType,final Payload payload, String userId,SendPushType sendType) {
		return mpushPushHandler.invoke(pushType, payload, userId, null,sendType);
	}
	
	private SendPushType sendType(Map<AttributeKey, Option> optionList){
		if(optionList!=null&&optionList.containsKey(AttributeKey.DOCTORFLAG)){
			Long doctorAttributeTag = (Long)optionList.get(AttributeKey.DOCTORFLAG).getValue();
			if(doctorAttributeTag!=null){
				boolean mpushTag = ((long)doctorAttributeTag&2)==2?true:false;
				boolean testTag = ((long)doctorAttributeTag&1)==1?true:false;
				if(mpushTag){ //测试，且有mpush标
					return SendPushType.MPUSH;
				}else if(testTag&&!mpushTag){ //测试，没有mpush标,则，mpush发送失败后，使用jpush再push
					return SendPushType.JPUSHANDMPUSH;
				}
			}
		}
		return SendPushType.JPUSH;
	}
	
	public static enum SendPushType{
		JPUSH,MPUSH,JPUSHANDMPUSH;
	}
}
