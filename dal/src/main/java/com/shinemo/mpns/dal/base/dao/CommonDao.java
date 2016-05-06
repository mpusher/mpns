package com.shinemo.mpns.dal.base.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinemo.mpns.dal.base.mapper.Mapper;

import javax.annotation.Resource;


@SuppressWarnings("unchecked")
public abstract class CommonDao<Q,D,M extends Mapper<Q, D> > {

    @Resource
    protected SqlSession sqlSession;
    
    private Class<M> clazzMapper;

    public CommonDao() {
        this.clazzMapper = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).
                getActualTypeArguments()[2];
    }

    private static Logger log = LoggerFactory.getLogger(CommonDao.class);

    public List<D> find(Q query){
        try {
    		M mapper = (M) sqlSession.getMapper(clazzMapper);
            List<D> ret = mapper.find(query);
            return ret;
        } catch (Exception e) {
            log.error(this.getClass()+".find:"+query.toString(),e);
            return null;
        }
    }

    public D get(Q query){
        try {
    		M mapper = (M) sqlSession.getMapper(clazzMapper);
    		D ret = mapper.get(query);
            return ret;
        } catch (Exception e) {
            log.error(this.getClass()+".get:"+query.toString(),e);
            return null;
        }
    }

    public D add(D temp) {
        try {
            M mapper = (M) sqlSession.getMapper(clazzMapper);
            mapper.add(temp);
            return temp;
        } catch (Exception e) {
            log.error(this.getClass()+".add:"+temp.toString(),e);
        }
        return null;
    }

    public boolean update(D temp) {
        try {
            M mapper = (M) sqlSession.getMapper(clazzMapper);
            int ret = mapper.update(temp);
            if(ret>0) return Boolean.TRUE;
        } catch (Exception e) {
            log.error("BlockDaoImpl.update:"+temp.toString(),e);
        }
        return Boolean.FALSE;
    }

    public boolean delete(Q query) {
        try {

            M mapper = (M) sqlSession.getMapper(clazzMapper);
            int ret = mapper.delete(query);
            if(ret>0) return Boolean.TRUE;
        } catch (Exception e) {
            log.error(this.getClass()+".delete:"+query.toString(),e);
        }
        return Boolean.FALSE;
    }

    public long count(Q query) {
        try {
    		M mapper = (M) sqlSession.getMapper(clazzMapper);
            long ret = mapper.count(query);
            return ret;
        } catch (Exception e) {
            log.error(this.getClass()+".count:"+query.toString(),e);
            return 0L;
        }
    }
    
    //是否使用备库,默认查询，都走备库
    public boolean readOnly(){
    	return true;
    }

}

