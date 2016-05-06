package com.shinemo.mpns.client.mpush.domain;

public enum PushType {
	
	NOTIFICATION("提醒",1),
	MESSAGE("消息",2),
	NOTIFICATIONANDMESSAGE("提醒+消息",3);

	PushType(String desc, int value) {
    	this.desc = desc;
    	this.value = value;
    }

    private final String desc;
    private final int value;
    
	public String getDesc() {
		return desc;
	}
	public int getValue() {
		return value;
	}
	
}
