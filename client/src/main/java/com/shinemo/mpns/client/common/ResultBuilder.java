package com.shinemo.mpns.client.common;

import java.io.Serializable;

public class ResultBuilder<T> implements Serializable {

	private static final long serialVersionUID = -5382128855948257372L;

	private T value;

	private Errors error;
	
	private String remark;

	private Throwable throwable;

	private boolean success;

	public ResultBuilder<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public ResultBuilder<T> setError(Errors error) {
		this.error = error;
		return this;
	}
	
	public ResultBuilder<T> setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public ResultBuilder<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public ResultBuilder<T> setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}

	public ResultBuilder<T> copyResult(Result<?> other) {
		this.error = other.getError();
		this.success = other.isSuccess();
		this.throwable = other.getThrowable();
		return this;
	}

	public ResultBuilder<T> copy(Result<T> other) {
		this.error = other.getError();
		this.success = other.isSuccess();
		this.throwable = other.getThrowable();
		this.value = other.getValue();
		return this;
	}

	public Result<T> build() {
		if ((null == error && null != value) || (success && error == null)) {
			success = true;
			error = Errors.SUCCESS;
		} else if (null == error && null == value) {
			error = Errors.FAILURE;
		}
		return new Result<T>(success, value, error, throwable,remark);
	}

	public T getValue() {
		return value;
	}

	public Errors getError() {
		return error;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getRemark() {
		return remark;
	}
	
	
}

