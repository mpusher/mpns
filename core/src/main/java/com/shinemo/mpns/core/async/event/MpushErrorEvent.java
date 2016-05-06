package com.shinemo.mpns.core.async.event;

import java.util.Map;

import com.shinemo.mpns.client.common.BaseDO;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.OfflineTypeEnum;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;

public class MpushErrorEvent extends BaseDO{
	
	private static final long serialVersionUID = 6852511000686049939L;
	
	private final String userId;
	private final OfflineTypeEnum offlineTypeEnum;
	private final String messageId;
	private final String content;
	private final Map<AttributeKey, Option> optionList;
	
	public MpushErrorEvent(String userId, OfflineTypeEnum offlineTypeEnum, String messageId, String content,Map<AttributeKey, Option> optionList) {
		this.userId = userId;
		this.offlineTypeEnum = offlineTypeEnum;
		this.messageId = messageId;
		this.content = content;
		this.optionList = optionList;
	}
	
	public String getUserId() {
		return userId;
	}
	public String getMessageId() {
		return messageId;
	}
	public String getContent() {
		return content;
	}

	public OfflineTypeEnum getOfflineTypeEnum() {
		return offlineTypeEnum;
	}

	public Map<AttributeKey, Option> getOptionList() {
		return optionList;
	}
	
}
