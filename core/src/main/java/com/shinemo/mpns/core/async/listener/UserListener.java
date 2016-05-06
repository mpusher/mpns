package com.shinemo.mpns.core.async.listener;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.StatusEnum;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.client.mpush.service.OfflineMessageService;
import com.shinemo.mpns.client.mpush.service.PushService;
import com.shinemo.mpns.core.async.AsyncSupport;
import com.shinemo.mpns.core.async.event.UserOfflineEvent;
import com.shinemo.mpns.core.async.event.UserOnlineEvent;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer;

@Service("userListener")
public class UserListener extends AsyncSupport{
	
	private static final Logger log = LoggerFactory.getLogger(UserListener.class);
	
	@Resource
	private OfflineMessageService offlineMessageService;
	
	@Resource
	private PushService pushService;
	
	@Resource
	private PushHandlerContainer pushHandlerContainer;
	
	//最多返回100个离线消息
	@Subscribe
	public void handlerUserOnlineEvent(UserOnlineEvent event) {

		if (event != null) {
			log.warn("user online:"+event.getUserId());
			OfflineMessageQuery query = OfflineMessageQuery.build().setUserId(event.getUserId()).setStatus(StatusEnum.NORMAL);
			query.setPageSize(100);
			Result<List<OfflineMessage>> ret = offlineMessageService.find(query);
			if(ret!=null&&ret.isSuccess()&&ret.getValue()!=null&&ret.getValue().size()>0){
				log.warn("user {}, offline message size {}",event.getUserId(),ret.getRemark());
				for(OfflineMessage offlineMessage:ret.getValue()){
					pushHandlerContainer.push(event.getUserId(), offlineMessage.getContent(),offlineMessage.getMessageId());
				}
				offlineMessageService.delete(OfflineMessageQuery.build().setUserId(event.getUserId()));
			}else{
				log.warn("user {}, offline message size 0",event.getUserId());
			}
		}
	}
	
	
	@Subscribe
	public void handlerUserOfflinelineEvent(UserOfflineEvent event) {

		if (event != null) {
			log.warn("user offline:"+event.getUserId());
		}
		
	}

}
