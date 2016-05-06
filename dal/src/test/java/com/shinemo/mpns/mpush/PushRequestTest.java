package com.shinemo.mpns.mpush;

import org.junit.Test;

import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory;
import com.shinemo.mpns.client.mpush.domain.PayloadFactory.MessageType;
import com.shinemo.mpns.client.mpush.domain.PushRequest;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushType;

public class PushRequestTest {
	
	@Test
	public void test(){
		NotificationPushPayload payload = PayloadFactory.create(MessageType.NOTIFICATION);
		PushRequest request = PushRequest.build(PushType.MESSAGE, payload, "huang2");
		request.option(AttributeKey.KEEPOFFLINEMESSAGE, Boolean.TRUE).option(AttributeKey.KEEPDATE, new Integer(10));
		
		System.out.println(request);
	}

}
