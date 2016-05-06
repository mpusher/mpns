package com.shinemo.mpns.core.async.event;

public class UserOfflineEvent {

	
	private final String userId;

	public UserOfflineEvent(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
}
