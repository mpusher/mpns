package com.shinemo.mpns.client.mpush.domain;

import java.io.Serializable;


public class PushResponse<T> implements Serializable{
	
	private static final long serialVersionUID = -3587137772613902791L;
	
	private T msgId;
	
	public T getMsgId() {
		return msgId;
	}

	public void setMsgId(T msgId) {
		this.msgId = msgId;
	}

}
