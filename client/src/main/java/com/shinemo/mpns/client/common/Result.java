package com.shinemo.mpns.client.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 5947203836182608272L;

	private boolean success;

	private T value;

	private Errors error;

	private Throwable throwable;
	
	private String remark;

	public Result() {

	}
	
	public Result(boolean success, T value, Errors error, Throwable throwable,String remark) {
		this.success = success;
		this.value = value;
		this.error = error;
		this.throwable = throwable;
		this.remark = remark;
	}

	public Result(boolean success, T value, Errors error, Throwable throwable) {
		this.success = success;
		this.value = value;
		this.error = error;
		this.throwable = throwable;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Errors getError() {
		return error;
	}

	public void setError(Errors error) {
		this.error = error;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getThrowableStackTrace() {
		if (null != throwable) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			throwable.printStackTrace(new PrintStream(out));
			return out.toString();
		}
		return "无异常被捕获";
	}

	public boolean isEmpty() {
		return null == value;
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	public boolean hasValue() {
		return isSuccess() && isNotEmpty();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}

