package com.shinemo.mpns.dal.mpush.dao.impl;

import org.springframework.stereotype.Repository;

import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.dal.base.dao.CommonDao;
import com.shinemo.mpns.dal.mpush.dao.OfflineMessageDao;
import com.shinemo.mpns.dal.mpush.mapper.OfflineMessageMapper;


@Repository("offlineMessageDao")
public class OfflineMessageDaoImpl extends CommonDao<OfflineMessageQuery, OfflineMessage, OfflineMessageMapper> implements OfflineMessageDao{

	@Override
	public boolean delete(long id) {
		OfflineMessageQuery query = new OfflineMessageQuery();
		query.setId(id);
		return delete(query);
	}

}
