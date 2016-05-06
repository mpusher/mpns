package com.shinemo.mpns.core.mpush.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinemo.mpns.client.common.EnvUtil;
import com.shinemo.mpns.client.common.Errors;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.common.ResultBuilder;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushRequest;
import com.shinemo.mpns.client.mpush.domain.PushResponse;
import com.shinemo.mpns.client.mpush.domain.PushType;
import com.shinemo.mpns.client.mpush.service.PushService;
import com.shinemo.mpns.core.mpush.handler.PushHandlerContainer;

@Service("pushService")
public class PushServiceImpl implements PushService{
	
	private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
	
	@Resource
	private PushHandlerContainer pushHandlerContainer;
	
	@Override
	public Result<PushResponse<String>> push(PushRequest request) {
		ResultBuilder<PushResponse<String>> result = new ResultBuilder<>();
		try{
			String userId = request.getUserId();
			Payload payload = request.getPayload();
			PushType pushType = request.getPushType();
			
			if(payload == null||pushType == null||StringUtils.isBlank(userId)){
				result.setError(Errors.E_METHOD_PARAMS);
				result.setSuccess(false);
				return result.build();
			}
			
			if(log.isInfoEnabled()){
				log.info("request:"+GsonUtil.toJson(request));
			}
			
			if(EnvUtil.isDaily()||EnvUtil.isDev()){
				userId += "test";
			}else if(EnvUtil.isPre()){
				userId += "pre";
			}
			
			if(payload instanceof NotificationPushPayload){ //初始化contentType
				NotificationPushPayload notificationPushPayload = (NotificationPushPayload) payload;
				notificationPushPayload.initContentType();
				//gson 无法去掉"",所以在入口去掉
				if(notificationPushPayload.getContent()!=null&&notificationPushPayload.getContent().equals("")){
					notificationPushPayload.setContent(null);
				}
			}
			
			String msgId = pushHandlerContainer.invoke(pushType,payload,userId,request.getSettings());
	    	
			PushResponse<String> response = new PushResponse<>();
			response.setMsgId(msgId);
			result.setSuccess(true).setValue(response);

			result.setSuccess(true).setValue(response);
		}catch(Exception e){
			log.error("push error:{},{}",GsonUtil.toJson(request),e);
			result.setError(Errors.E_SYS_SERVICE);
			result.setSuccess(false);
		}
		return result.build();
	}
	
}
