package com.shinemo.mpns.core.mpush.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.core.async.event.UserOfflineEvent;
import com.shinemo.mpns.core.async.event.UserOnlineEvent;

@Service("userChangeListener")
public class UserChangeListener extends com.shinemo.mpush.common.router.UserChangeListener{

	@Resource
	private com.shinemo.mpns.core.async.InternalEventBus internalEventBus;
	
	@Override
	public void onMessage(String channel, String message) {
		if(getOnlineChannel().equals(channel)){ //在线
			internalEventBus.postAsync(new UserOnlineEvent(message));
		}else if(getOfflineChannel().equals(channel)){ //离线
			internalEventBus.postAsync(new UserOfflineEvent(message));
		}
	}
	
}
