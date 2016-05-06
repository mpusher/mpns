package com.shinemo.mpns.client.mpush.domain;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;


public class PushRequest implements Serializable{
	
	private static final long serialVersionUID = -3587137772613902791L;
	
	private PushType pushType;
	private Payload payload;
	private String userId;
	private Map<AttributeKey, Option> settings = Maps.newHashMap();
	
	public PushRequest(PushType pushType,Payload payload,String userId) {
		this.payload = payload;
		this.pushType = pushType;
		this.userId = userId;
	}
	
	public static PushRequest build(PushType pushType,Payload payload,String userId){
		if(pushType == null||payload == null||userId == null||userId.equals("")){
			throw new IllegalArgumentException("pushType,payload,userId can not null");
		}
		return new PushRequest(pushType, payload, userId);
	}
	
	public PushType getPushType() {
		return pushType;
	}

	public Payload getPayload() {
		return payload;
	}

	public String getUserId() {
		return userId;
	}
	
	public Map<AttributeKey, Option> getSettings() {
		return settings;
	}

	public PushRequest option(AttributeKey key, Object value){
		if(key==null||value == null){
			throw new IllegalArgumentException("key,value 不能为空");
		}
		Option option = Option.build(key, value);
		settings.put(key, option);
		return this;
	}

	public static class Option implements Serializable{
		private static final long serialVersionUID = 3901525446230821714L;
		private final AttributeKey key;
		private final Object value;
		private Option(AttributeKey key, Object value) {
			this.key = key;
			this.value = value;
		}
		public AttributeKey getKey() {
			return key;
		}
		public Object getValue() {
			return value;
		}
		
		public static Option build(AttributeKey key, Object value){
			if(key==null||value == null){
				throw new IllegalArgumentException("参数错误");
			}
			Class<?> claz = key.clazz;
			if(value.getClass().getName().equals(claz.getName())){
				return new Option(key, value);
			}
			throw new IllegalArgumentException("类型不匹配");
		}
	}
	
	public static enum AttributeKey implements Serializable{

		KEEPOFFLINEMESSAGE(0, "是否保存离线消息",Boolean.class), KEEPDATE(1, "离线消息保存时间",Integer.class),DOCTORFLAG(2, "医生的属性标",Long.class);

		private AttributeKey(int ivalue, String idesc,Class<?> clazz) {
			this.value = ivalue;
			this.desc = idesc;
			this.clazz = clazz;
		}

		private final int value;
		private final String desc;
		private final Class<?> clazz;

		public static AttributeKey getType(final int aValue) {
			for (AttributeKey type : AttributeKey.values()) {
				if (type.value == aValue) {
					return type;
				}
			}
			throw new IllegalArgumentException("wrong StatusEnum");
		}
		
		public Class<?> getClazz() {
			return clazz;
		}

		public int toInteger() {
			return value;
		}

		public String toDesc() {
			return desc;
		}

		public int getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}
	}
}
