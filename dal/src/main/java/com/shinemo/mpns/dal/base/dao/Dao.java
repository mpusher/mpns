package com.shinemo.mpns.dal.base.dao;
import java.util.List;

/**
 *
 * @param <Q> query
 * @param <D>do
 * @param <M>mapper
 */
public interface Dao<Q,D> {

    public List<D> find(Q query);

    public D get(Q query);

    public long count(Q query);

    public boolean update(D tempDO);

    public boolean delete(Q query);

    public D add(D tempDO);

}
