package com.shinemo.mpns.core.mpush.domain;

import java.util.List;

import com.google.common.collect.Lists;
import com.shinemo.mpush.common.manage.ServerManage;
import com.shinemo.mpush.conn.client.ConnectionServerApplication;
import com.shinemo.mpush.push.ConnClient;
import com.shinemo.mpush.tools.spi.ServiceContainer;

public class ConnectionClientMain extends ConnClient {
	
	@SuppressWarnings("unchecked")
	private ServerManage<ConnectionServerApplication> connectionServerManage = ServiceContainer.getInstance(ServerManage.class);
	
	public List<ConnectionServerApplication> getApplicationList(){
		return Lists.newArrayList(connectionServerManage.getList());
	}
	
}
