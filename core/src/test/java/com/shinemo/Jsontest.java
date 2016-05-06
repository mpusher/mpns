package com.shinemo;

import java.util.Map;

import org.junit.Test;

import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.mpush.domain.CustomPushPayload;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.PushRequest;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer.SendPushType;

public class Jsontest {
	
	@Test
	public void test(){
		NotificationPushPayload payload = new NotificationPushPayload();
		payload.setContent("hello");
		System.out.println(GsonUtil.toJson(payload));
	}

	@Test
	public void test2(){
		PushRequest request = new PushRequest(PushType.MESSAGE, null, "doctor7");
		request.option(AttributeKey.DOCTORFLAG, 2L);
		
		String str = GsonUtil.toJson(request);
		
		PushRequest newRequest = GsonUtil.fromGson2Obj(str, PushRequest.class);
		
		Map<AttributeKey, Option> settings = newRequest.getSettings();
		
		if(settings.containsKey(AttributeKey.DOCTORFLAG)){
			System.out.println("包含");
		}else{
			System.out.println("不包含");
		}
		
		Long doctorAttributeTag = 0L;
		if(doctorAttributeTag!=null){
			long lgdoctorAttributeTag = doctorAttributeTag.longValue();
			System.out.println(lgdoctorAttributeTag&2);
			System.out.println(lgdoctorAttributeTag&1);
			boolean mpushTag = ((long)doctorAttributeTag&2)==2?true:false;
			boolean testTag = ((long)doctorAttributeTag&1)==1?true:false;
			if(mpushTag){ //测试，且有mpush标
				System.out.println("have mpush");
			}else if(testTag&&!mpushTag){ //测试，没有mpush标,则，mpush发送失败后，使用jpush再push
				System.out.println("SendPushType.JPUSHANDMPUSH");
			}
		}
		
		
	}
	
}
