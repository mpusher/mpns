package com.shinemo.mpns.mpush;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.shinemo.mpns.base.Base;
import com.shinemo.mpns.client.common.GsonUtil;
import com.shinemo.mpns.client.mpush.domain.NotificationPushPayload;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.OfflineTypeEnum;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.StatusEnum;
import com.shinemo.mpns.client.mpush.domain.OfflineMessageQuery;
import com.shinemo.mpns.dal.mpush.dao.OfflineMessageDao;

public class CronLogDaoTest extends Base{

	@Resource
    private OfflineMessageDao  offlineMessageDao;

    @Test
    public void addTest() throws ParseException  {
    	
    	OfflineMessageQuery query = new OfflineMessageQuery();
    	query.setId(12L);
    	OfflineMessage before = offlineMessageDao.get(query);
    	
    	PushContent pushContent = GsonUtil.fromGson2Obj(before.getContent(), PushContent.class);
    	
    	NotificationPushPayload payload = GsonUtil.fromGson2Obj(pushContent.getContent(), NotificationPushPayload.class);
    	
    	for(int i = 1;i<100;i++){
    		String msgId = "100"+i;
    		pushContent.setMsgId(msgId);
    		payload.setTitle(msgId).setContent(msgId+",content");
    		pushContent.setContent(GsonUtil.toJson(payload));
    		OfflineMessage message = OfflineMessage.build(msgId).setContent(GsonUtil.toJson(payload)).setUserId(before.getUserId()).setOfflineType(OfflineTypeEnum.OFFLINE.getValue());
        	offlineMessageDao.add(message);
        	System.out.println(ToStringBuilder.reflectionToString(message, ToStringStyle.MULTI_LINE_STYLE));
    	}
    	
    	
    }
    
    

    @Test
    public void findTest()  {
    	OfflineMessageQuery query = OfflineMessageQuery.build().setUserId("userId").setStatus(StatusEnum.NORMAL);
    	List<OfflineMessage> ret = offlineMessageDao.find(query);
    	if(ret!=null){
    		for(OfflineMessage userDO:ret){
    			System.out.println(ToStringBuilder.reflectionToString(userDO, ToStringStyle.MULTI_LINE_STYLE));
    		}
    	}else{
    		System.out.println("ret is null");
    	}

    }

    @Test
    public void queryTest()  {
    	OfflineMessageQuery query = new OfflineMessageQuery();
    	query.setId(445L);
    	OfflineMessage ret = offlineMessageDao.get(query);
    	if(ret!=null){
    		PushContent content = GsonUtil.fromGson2Obj(ret.getContent(),PushContent.class);
        	System.out.println(ToStringBuilder.reflectionToString(content, ToStringStyle.MULTI_LINE_STYLE));
    	}else{
    		System.out.println("ret is null");
    	}

    }

    @Test
    public void countTest()  {
    	OfflineMessageQuery query = new OfflineMessageQuery();
    	query.setUserId("doctor7test");
    	query.setStatus(StatusEnum.NORMAL);
    	long ret = offlineMessageDao.count(query);
    	System.out.println(ToStringBuilder.reflectionToString(ret, ToStringStyle.MULTI_LINE_STYLE));
    }
    
    @Test
    public void updateTest(){
    	OfflineMessage message = new OfflineMessage();
    	message.setId(1L);
    	message.setContent("content2");
    	offlineMessageDao.update(message);
    }
    
    @Test
    public void deleteTest(){
    	OfflineMessageQuery query = OfflineMessageQuery.build().setUserId("doctor7test");
    	query.setExpireDate(getFutureDate(new Date(), 5));
    	offlineMessageDao.delete(query);
    }
    
    @Test
    public void user7test(){
		OfflineMessageQuery query = OfflineMessageQuery.build().setUserId("doctor7test").setStatus(StatusEnum.NORMAL);
		query.setPageSize(100);
		List<OfflineMessage> ret = offlineMessageDao.find(query);
    	if(ret!=null){
    		for(OfflineMessage userDO:ret){
    			System.out.println(ToStringBuilder.reflectionToString(userDO, ToStringStyle.MULTI_LINE_STYLE));
    		}
    	}else{
    		System.out.println("ret is null");
    	}
    }
    
    @Test
    public void time(){
    	System.out.println(1456298293396L-1456298289485L);
    }
    
    public static Date getFutureDate(Date date, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }
    
    
    
}
