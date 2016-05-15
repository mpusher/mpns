package com.shinemo.mpns.core.jpush.service.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.shinemo.mpns.client.common.Errors;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.common.ResultBuilder;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.Payload;
import com.shinemo.mpns.client.mpush.domain.PushResponse;
import com.shinemo.mpns.core.jpush.service.JPushService;
import com.shinemo.mpns.core.jpush.util.JPushUtil;
import com.shinemo.mpns.dal.config.ConfigCenter;
import com.mpush.api.PushContent.PushType;

@Service("jPushService")
public class JPushServiceImpl implements JPushService{

	private static final Logger log = LoggerFactory.getLogger(JPushServiceImpl.class);
	
	private final Executor pool = Executors.newFixedThreadPool(5);
	
	@Override
	public Result<PushResponse<String>> push(PushType pushType, Payload payload, String userId) {
		ResultBuilder<PushResponse<String>> result = new ResultBuilder<>();
		try{
			
			if(pushType==null||payload==null||StringUtils.isBlank(userId)||!(payload instanceof NotificationPushPayload)){
				result.setError(Errors.E_METHOD_PARAMS);
				result.setSuccess(false);
				return result.build();
			}
			
			NotificationPushPayload customPushPayload = (NotificationPushPayload)payload;
			
			pool.execute(new JPushHandler(userId, customPushPayload,pushType.getValue()));
		}catch(Exception e){
			log.error("jpush error:{},{},{}",payload,GsonUtil.toJson(userId),e);
			result.setError(Errors.E_SYS_SERVICE);
			result.setSuccess(false);
		}
		return result.build();
	}

}

class JPushHandler implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(JPushHandler.class);
	private String toUsers;
	private NotificationPushPayload payload;
	private int type;

	public JPushHandler(String toUsers, NotificationPushPayload payload, int type) {
		this.toUsers = toUsers;
		
		this.type = type;
		this.payload = payload;
	}

	@SuppressWarnings("unused")
	public void run() {
		
		int tryCount = 0 ;
		while(tryCount ++ < 3){
			try {
				JPushClient jpush = new JPushClient(ConfigCenter.holder.jpushSecretKey(), ConfigCenter.holder.jpushAppKey());
				PushPayload pushPayload = null;
				
                switch (type) {
                    case 1:
                        pushPayload = JPushUtil.buildPushNotification(toUsers, payload.getExtras(),payload.getContent());
                        break;
                    case 2:
                        pushPayload = JPushUtil.buildPushMessage(toUsers, payload.getExtras(),payload.getContent());
                        break;
                    case 3:
                        pushPayload = JPushUtil.buildPushNotificationAndMessage(toUsers, payload.getExtras(),payload.getContent());
                        break;
                    default:
                        break;
                }
				if (pushPayload == null) {
					log.error("Failed to send message to " + toUsers
							+ ", msg: " + GsonUtil.toJson(payload) + ", type " + type);
					return;
				}
				
				if (pushPayload == null) {
					log.error("Failed to send message to " + toUsers + ", notification " + payload.getContent()
							+ ", extra " + payload.getExtras() + ", type " + type);
					return;
				}
				

				PushResult pushResult = jpush.sendPush(pushPayload);
				log.error("send message success user:" + toUsers + ", notification " + payload.getContent()
                        + ", extra " + payload.getExtras() + ", type " + type);
				return;
			} catch (Exception e) {
				
				log.error("Failed to send ,msg:{},type:{} user:{},try {},ex{} ",GsonUtil.toJson(payload),type,toUsers, tryCount, e);
				
				try {
					Thread.sleep(1000);
				} catch (Exception e2) {
				}
			}
		}
	}
}
