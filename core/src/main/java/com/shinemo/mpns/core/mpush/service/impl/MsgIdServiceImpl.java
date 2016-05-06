package com.shinemo.mpns.core.mpush.service.impl;


import java.util.Date;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.core.mpush.service.MsgIdService;
import com.shinemo.mpns.core.util.DateUtil;
import com.shinemo.mpush.tools.redis.manage.RedisManage;

@Service("msgIdService")
public class MsgIdServiceImpl implements MsgIdService {
	
	private static final String ORDER_NUM_KEY = "mpns_msg_id_key_";
	
	private static final int EXPIRE_TIME = 60*60*24; //暂时使用一天
	
	public static int bizId = 1; //业务1Id
	
	@Override
	public String generateMsgId(int bizId) {
		StringBuffer sb = new StringBuffer();
		String date = getDate();
//		String fixTime = getTime();

		String fixNum = getNum();
		sb.append(bizId).append(date).append(fixNum);
		return sb.toString();
	}
	/**
	 * 20150807   -> 1508
	 * @return
	 */
	private   String getDate(){
		Date now = new Date();
		String yeah = DateUtil.getYearAndMonth(now);
		return yeah.substring(2);
	}
	
	@SuppressWarnings("unused")
	private   String getTime(){
		Date now = new Date();
		int second = DateUtil.getSecondOfDay(now);
		return fixLenght(second);
	}
	
	private   String getNum(){
		long num = RedisManage.incr(ORDER_NUM_KEY, EXPIRE_TIME);
		return fixLenght((int)num);
	}
	
	private  String fixLenght(int num){
		String strNum = num+"";
		int lenght = strNum.length();
		StringBuilder sb = new StringBuilder();
		if(lenght<8){
			for(int i = 0;i<8-lenght;i++){
				sb.append("0");
			}

		}
		sb.append(strNum);
		return sb.toString();
	}
}
