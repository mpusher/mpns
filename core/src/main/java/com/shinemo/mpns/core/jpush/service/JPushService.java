package com.shinemo.mpns.core.jpush.service;

import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushResponse;
import com.shinemo.mpush.api.PushContent.PushType;

public interface JPushService {

	public Result<PushResponse<String>> push(PushType pushType, Payload payload, String userId);
	
}
