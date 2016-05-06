package com.shinemo.mpns.client.common;

import java.util.List;

public interface BaseService<Q,D> {
	
	public Result<List<D>> find(Q query);
	
	public Result<D> get(Q query);
	
	public Result<Long> count(Q query);
	
	public Result<Long> add(D temp);
	
	public Result<Boolean> update(D temp);
	
	public Result<Boolean> delete(Q query);
	
}
