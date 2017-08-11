package com.ht.db.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by huangtao on 16/9/2.
 */
public abstract class BaseDao <T> {

    public boolean save(T t){
        return DBUtils.getInstance().save(t);
    }

    public boolean update(T t){
        return DBUtils.getInstance().update(t);
    }

    public T findById(Serializable id){
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Class<T> clazz = (Class<T>) (((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
            return (T) DBUtils.getInstance().findById(clazz, id);
        } else {
            throw new RuntimeException("please give generic parameter");
        }
    }

    public T findByCondition(String condition) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Class<T> clazz = (Class<T>) (((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
            return findByCondition(clazz, condition);
        } else {
            throw new RuntimeException("please give generic parameter");
        }
    }

    private T findByCondition(Class clazz, String condition){
        return (T) DBUtils.getInstance().findByCondition(clazz, condition);
    }

    public boolean deleteById(Serializable id) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Class<T> clazz = (Class<T>) (((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
            return DBUtils.getInstance().deleteById(clazz, id);
        } else {
            throw new RuntimeException("please give generic parameter");
        }
    }

    public boolean deleteByCondition(String condition) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Class<T> clazz = (Class<T>) (((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
            return deleteByCondition(clazz, condition);
        } else {
            throw new RuntimeException("please give generic parameter");
        }
    }

    private boolean deleteByCondition(Class clazz, String condition){
        return  DBUtils.getInstance().deleteByCondition(clazz, condition);
    }
}
