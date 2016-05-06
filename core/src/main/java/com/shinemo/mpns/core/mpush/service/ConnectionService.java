package com.shinemo.mpns.core.mpush.service;

import java.util.Collection;

import com.shinemo.mpns.core.mpush.domain.ConnectionClientMain;

public interface ConnectionService {

	public void connect(Collection<String> userIds) throws Exception;
	
	public void close(String userId);

	public ConnectionClientMain getConnectionClient();
}
