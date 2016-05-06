package com.shinemo.mpns.core.async.event;

public class UserOnlineEvent {
	
	private final String userId;

	public UserOnlineEvent(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
}
