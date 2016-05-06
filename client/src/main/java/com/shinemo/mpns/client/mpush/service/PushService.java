package com.shinemo.mpns.client.mpush.service;

import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.mpush.domain.PushRequest;
import com.shinemo.mpns.client.mpush.domain.PushResponse;

public interface PushService {
	
	public Result<PushResponse<String>> push(PushRequest request);
	
}
