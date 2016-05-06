package com.shinemo.mpns.client.mpush.domain;

import java.util.Date;

import com.shinemo.mpns.client.common.BaseDO;

public class OfflineMessage extends BaseDO{

	private static final long serialVersionUID = 3021670214888544203L;
	
	private Long id;
	private String userId;
	private String messageId;
	private String content;
	private Integer status;
	private Integer offlineType;
	private Date gmtCreate;
	private Date gmtModified;
	private Date expireDate;
	
	public OfflineMessage(){
		
	}
	
	public OfflineMessage(String messageId){
		this.messageId = messageId;
		this.status = StatusEnum.NORMAL.value;
	}
	
	public static OfflineMessage build(String messageId){
		OfflineMessage message = new OfflineMessage(messageId);
		return message;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public OfflineMessage setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public OfflineMessage setContent(String content) {
		this.content = content;
		return this;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	public Integer getOfflineType() {
		return offlineType;
	}

	public OfflineMessage setOfflineType(Integer offlineType) {
		this.offlineType = offlineType;
		return this;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}

	public OfflineMessage setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		return this;
	}



	public static enum StatusEnum {

		DELETE(0, "删除"), NORMAL(1, "正常");

		private StatusEnum(int ivalue, String idesc) {
			value = ivalue;
			desc = idesc;
		}

		private final int value;
		private final String desc;

		public static StatusEnum getType(final int aValue) {
			for (StatusEnum type : StatusEnum.values()) {
				if (type.value == aValue) {
					return type;
				}
			}
			throw new IllegalArgumentException("wrong StatusEnum");
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
	
	public static enum OfflineTypeEnum {

		TIMEOUT(0, "超时"), FAILED(1, "失败"),OFFLINE(2,"离线");

		private OfflineTypeEnum(int ivalue, String idesc) {
			value = ivalue;
			desc = idesc;
		}

		private final int value;
		private final String desc;

		public static OfflineTypeEnum getType(final int aValue) {
			for (OfflineTypeEnum type : OfflineTypeEnum.values()) {
				if (type.value == aValue) {
					return type;
				}
			}
			throw new IllegalArgumentException("wrong StatusEnum");
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
