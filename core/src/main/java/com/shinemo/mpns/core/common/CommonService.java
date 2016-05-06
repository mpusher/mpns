package com.shinemo.mpns.core.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinemo.mpns.client.common.BaseService;
import com.shinemo.mpns.client.common.Errors;
import com.shinemo.mpns.client.common.Result;
import com.shinemo.mpns.client.common.ResultBuilder;
import com.shinemo.mpns.dal.base.dao.Dao;



public abstract class CommonService<Q,D,DA extends Dao<Q,D>> implements BaseService<Q, D> {
	
	private static Logger log = LoggerFactory.getLogger(CommonService.class);
	
	public abstract DA getDao();
	
	public Result<List<D>> find(Q query) {
		ResultBuilder<List<D>> builder = new ResultBuilder<List<D>>();
		try{
			//step1 
			fixQuery(query);
			
			Long count = getDao().count(query);
			if(count!=null&&count>0){
				List<D> ret = getDao().find(query);
				builder.setValue(ret);
			}else{
				builder.setValue(new ArrayList<D>(0));
			}
			if(count!=null){
				builder.setRemark(count.toString());
			}else{
				builder.setRemark("0");
			}
			
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("find:"+query.toString(),e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_FIND).setThrowable(e);
		}
		return builder.build();
	}
	
	public Result<D> get(Q query) {
		ResultBuilder<D> builder = new ResultBuilder<D>();
		try{
			D ret = getDao().get(query);
			builder.setValue(ret);
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("get:"+query.toString(),e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_GET).setThrowable(e);
		}
		return builder.build();
	}

	public Result<Long> add(D temp) {
		ResultBuilder<Long> builder = new ResultBuilder<Long>();
		try{
			if(checkAddParam(temp)){
				getDao().add(temp);
				builder.setValue(getId(temp));
			}else{
				builder.setError(Errors.ERROR_DB_PARAM);
			}
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("add:"+temp.toString(),e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_ADD).setThrowable(e);
		}
		return builder.build();
	}

	public Result<Boolean> update(D temp) {
		ResultBuilder<Boolean> builder = new ResultBuilder<Boolean>();
		try{
			if(checkUpdateParam(temp)){
				Boolean updateRet = getDao().update(temp);
				builder.setValue(updateRet);
			}else{
				builder.setError(Errors.ERROR_DB_PARAM);
			}
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("update:"+temp,e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_UPDATE).setThrowable(e);
		}
		return builder.build();
	}

	public Result<Boolean> delete(Q query) {
		ResultBuilder<Boolean> builder = new ResultBuilder<Boolean>();
		try{
			if(checkDeleteParam(query)){
				Boolean updateRet = getDao().delete(query);
				builder.setValue(updateRet);
			}else{
				builder.setError(Errors.ERROR_DB_PARAM);
			}
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("delete:"+query,e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_DELETE).setThrowable(e);
		}
		return builder.build();
	}
	
	public Result<Long> count(Q query) {
		ResultBuilder<Long> builder = new ResultBuilder<Long>();
		try{
			Long ret = getDao().count(query);
			builder.setValue(ret);
			builder.setSuccess(true);
		}catch (Exception e) {
			log.error("get:"+query.toString(),e);
			builder.setSuccess(false).setError(Errors.ERROR_DB_COUNT).setThrowable(e);
		}
		return builder.build();
	}
	
	public void fixQuery(Q query){
		//空实现
	}
	
	public boolean checkAddParam(D temp){
		return true;
	}
	
	public boolean checkDeleteParam(Q query){
		return true;
	}
	
	public boolean checkUpdateParam(D temp){
		return true;
	}
	
	public abstract Long getId(D temp);

}
