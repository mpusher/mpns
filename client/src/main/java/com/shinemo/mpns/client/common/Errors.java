package com.shinemo.mpns.client.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Errors implements Serializable {

	SUCCESS(0, "操作成功"), // 操作成功
	FAILURE(1, "系统繁忙，请稍后重试"), // 系统繁忙，请稍后重试
	E_METHOD_PARAMS(2, "系统调用参数错误"), // 参数错误
	E_UN_KNOWN(3, "未知错误"), // 未知错误
	E_SYS_SERVICE(1000, "服务繁忙，请稍后重试"), // 服务繁忙，请稍后重试
	E_SYS_SQL(2000, "数据库繁忙，请稍后重试"), // 数据库繁忙，请稍后重试
	
	ERROR_DB_FIND(4000,"数据库繁忙，请稍候查询"),
	ERROR_DB_GET(4001,"数据库繁忙，请稍候查询"),
	ERROR_DB_ADD(4002,"数据库繁忙，请稍候查询"),
	ERROR_DB_UPDATE(4003,"数据库繁忙，请稍候查询"),
	ERROR_DB_DELETE(4004,"数据库繁忙，请稍候查询"),
	ERROR_DB_COUNT(4005,"数据库繁忙，请稍候查询"),
	ERROR_DB_PARAM(4006,"参数有误"),
	ERROR_USER_PARAM(4007,"用户名长度为6-20，密码长度为6-20，且密码为字母，数字与特殊符号的组合"),
	ERROR_NOT_SUPPORT(4008,"不支持"),
	
	
	ERROR_SQL_COUNT(10000,"每次只能执行一条sql"),
	ERROR_SQL_STATUS(10001,"状态不对"),
	ERROR_NOT_RIGHT(10002,"没有权限"),
	ERROR_WRONG_PASSWORD(10003,"密码错误"),
	
	E_SYS_WEB(3000, "应用服务器繁忙，请稍后重试");// 应用服务器繁忙，请稍后重试
	
	private Errors(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private static final Map<Integer, Errors> codeToError;
	static {
		Map<Integer, Errors> m = new HashMap<Integer, Errors>();
		for (Errors error : Errors.values()) {
			m.put(error.getCode(), error);
		}
		codeToError = Collections.unmodifiableMap(m);
	}

	public static Errors getErrorFromCode(int code) {
		return codeToError.containsKey(code) ? codeToError.get(code) : Errors.E_UN_KNOWN;
	}

	private static final long serialVersionUID = 1L;

	private final int code;

	private final String msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Error Code(").append(code).append(") Msg:").append(msg);
		return builder.toString();
	}

}
