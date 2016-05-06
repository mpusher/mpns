package com.shinemo.mpns.web.push.vo;


public class StatVO {
	
	private int type;
	private int page;
	private int size=10;
	private String ip;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public static enum StatType {
		ONLINE_USER_NUM("在线用户",1,"onlineUserNum",new Class<?>[]{String.class}),
		ONLINE_USER_LIST("在线用户列表",2,"onlineUserList",new Class<?>[]{String.class,Integer.class,Integer.class});

		StatType(String desc, int value,String method,Class<?>[] clazz) {
	    	this.desc = desc;
	    	this.value = value;
	    	this.method = method;
	    	this.clazz = clazz;
	    }

	    private final String desc;
	    private final int value;
	    private final String method;
	    private final Class<?>[] clazz;
	    
		public String getDesc() {
			return desc;
		}
		public int getValue() {
			return value;
		}
		public String getMethod() {
			return method;
		}
		
		public Class<?>[] getClazz() {
			return clazz;
		}
		
		public Object[] getParam(String ip,int start,int size){
			if(getClazz().length>1){
				return new Object[]{ip,start,size};
			}else{
				return new Object[]{ip};
			}
		}
		
		public static StatType get(final int value) {
			for (StatType type : StatType.values()) {
				if (type.value == value) {
					return type;
				}
			}
			return ONLINE_USER_NUM;
		}
		
	}

}
