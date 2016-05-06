package com.shinemo.mpns.core.jpush.util;

import java.util.Map;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


public class JPushUtil {

	
	public static PushPayload buildPushNotificationAndMessage(String alias, Map<String, String> extra,String title) {
		return PushPayload.newBuilder()
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
								.addExtras(extra)
								.setAlert(title)
								.build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setSound("happy")
								.addExtras(extra)
								.setAlert(title)
								.build())

                        .build())
				.setMessage(Message.newBuilder()
						.addExtras(extra)
						.setMsgContent(title)
						.build())
				.build();
	}

	public static PushPayload buildPushNotification(String alias, Map<String, String> extra,String title) {
		return PushPayload.newBuilder()
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.setPlatform(Platform.android_ios())
				
                .setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
								.setSound("happy")
								.addExtras(extra)
								.setAlert(title)
								.build())
						.addPlatformNotification(AndroidNotification.newBuilder()
								.setTitle("Send to Android")
								.setAlert(title)
								.addExtras(extra)
								.build())
						.build())
				.build();
	}

	public static PushPayload buildPushMessage(String alias, Map<String, String> extra,String title) {
		return PushPayload.newBuilder()
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.alias(alias))
				.setMessage(Message.newBuilder()
						.addExtras(extra)
						.setMsgContent(title==null?"":title)
                        .build())
				.build();
	}

}
