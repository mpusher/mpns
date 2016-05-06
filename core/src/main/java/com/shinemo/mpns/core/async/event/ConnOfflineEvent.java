package com.shinemo.mpns.core.async.event;

public class ConnOfflineEvent {

	
	private final String userId;

	public ConnOfflineEvent(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
}
