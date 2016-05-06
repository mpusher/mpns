package com.shinemo.mpns.core.mpush.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.client.mpush.service.OfflineMessageService;
import com.shinemo.mpns.core.common.CommonService;
import com.shinemo.mpns.dal.mpush.dao.OfflineMessageDao;


@Service("offlineMessageService")
public class OfflineMessageServiceImpl extends CommonService<OfflineMessageQuery, OfflineMessage, OfflineMessageDao> implements OfflineMessageService{
	
	@Resource(name="offlineMessageDao")
	private OfflineMessageDao offlineMessageDao;
	
	@Override
	public OfflineMessageDao getDao() {
		return offlineMessageDao;
	}

	@Override
	public Long getId(OfflineMessage temp) {
		return temp.getId();
	}
	
	@Override
	public boolean checkAddParam(OfflineMessage temp) {
		return super.checkAddParam(temp);
	}
	
	@Override
	public boolean checkUpdateParam(OfflineMessage temp) {
		if(temp.getId()==null||temp.getId()<=0){
			return false;
		}
		return super.checkUpdateParam(temp);
	}
	
}
