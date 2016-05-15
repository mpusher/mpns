package com.shinemo.mpns.core.util;

import com.shinemo.mpns.client.mpush.domain.PushType;

public class PushTypeUtil {
	
	public static com.mpush.api.PushContent.PushType convert(PushType pushType){
		if(pushType==null) return null;
		for(com.mpush.api.PushContent.PushType mpushPushType:com.mpush.api.PushContent.PushType.values()){
			if(mpushPushType.getValue()==pushType.getValue()){
				return mpushPushType;
			}
		}
		return null;
	}

}
