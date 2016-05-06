package com.shinemo.mpns.client.mpush.domain;

import java.util.Date;

import com.shinemo.mpns.client.common.QueryBase;
import com.shinemo.mpns.client.mpush.domain.OfflineMessage.StatusEnum;

public class OfflineMessageQuery extends QueryBase{

	private static final long serialVersionUID = 3021670214888544203L;
	
	private String userId;
	private String content;
	private Date gmtCreate;
	private Long id;
	private StatusEnum status;
	private String messageId;
	private Date expireDate = new Date();
	
	public OfflineMessageQuery(){
		
	}
	
	public static OfflineMessageQuery build(){
		OfflineMessageQuery query = new OfflineMessageQuery();
		return query;
	}
	
	public String getUserId() {
		return userId;
	}
	public OfflineMessageQuery setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	public String getContent() {
		return content;
	}
	public OfflineMessageQuery setContent(String content) {
		this.content = content;
		return this;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public OfflineMessageQuery setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
		return this;
	}
	public Long getId() {
		return id;
	}
	public OfflineMessageQuery setId(Long id) {
		this.id = id;
		return this;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public OfflineMessageQuery setStatus(StatusEnum status) {
		this.status = status;
		return this;
	}

	public String getMessageId() {
		return messageId;
	}

	public OfflineMessageQuery setMessageId(String messageId) {
		this.messageId = messageId;
		return this;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public OfflineMessageQuery setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		return this;
	}
	
}
