package com.shinemo.mpns.core.async.listener;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.client.mpush.domain.PushRequest.AttributeKey;
import com.shinemo.mpns.client.mpush.domain.PushRequest.Option;
import com.shinemo.mpns.client.mpush.service.OfflineMessageService;
import com.shinemo.mpns.core.async.AsyncSupport;
import com.shinemo.mpns.core.async.event.MpushErrorEvent;
import com.shinemo.mpns.core.util.DateUtil;

@Service("mpushListener")
public class MpushListener extends AsyncSupport {

	private static final Logger log = LoggerFactory.getLogger(MpushListener.class);

	@Resource
	private OfflineMessageService offlineMessageService;

	@Subscribe
	public void handlerEvent(MpushErrorEvent event) {

		Map<AttributeKey, Option> optionList = event.getOptionList();
		if (optionList != null && optionList.size() > 0) {
			Option keepDateOption = optionList.get(AttributeKey.KEEPDATE);
			if (keepDateOption != null) {
				int date = (int) keepDateOption.getValue();
				if (date > 15) {// 默认15天
					date = 15;
				}
				OfflineMessageQuery query = OfflineMessageQuery.build().setMessageId(event.getMessageId());
				Result<OfflineMessage> beforeResult = offlineMessageService.get(query);
				if (beforeResult != null && beforeResult.isSuccess() && beforeResult.getValue() != null) {
					OfflineMessage before = beforeResult.getValue();
					before.setOfflineType(event.getOfflineTypeEnum().getValue());
					before.setStatus(null);
					offlineMessageService.update(before);
				} else {
					Date expireDate = DateUtil.getFutureDate(new Date(), date);
					OfflineMessage message = OfflineMessage.build(event.getMessageId()).setContent(event.getContent()).setUserId(event.getUserId())
							.setOfflineType(event.getOfflineTypeEnum().getValue()).setExpireDate(expireDate);
					offlineMessageService.add(message);
				}
			}else{
				log.info("MpushErrorEvent,not keep option,{}",GsonUtil.toJson(event));
			}
		} else {
			log.info("MpushErrorEvent,not have option,{}",GsonUtil.toJson(event));
		}

	}

}
