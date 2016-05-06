package com.shinemo.mpns.dal.mpush.dao;

import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.dal.base.dao.Dao;


public interface OfflineMessageDao extends Dao<OfflineMessageQuery, OfflineMessage>{
	
	public boolean delete(long id);
	
}
