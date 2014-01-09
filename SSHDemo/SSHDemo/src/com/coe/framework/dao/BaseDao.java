package com.coe.framework.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao  <T extends Serializable, PK extends Serializable>{
	
	public Serializable save(T entity);
	/**
	 * 更新
	 * */
	public void update(T t);
	
	public void update(final String hql, final Object[] params);
	
	public void update(Class<T> entityClass, String pkName, Object pkValue,String propName, Object propValue); 
	
	public void update(Class<T> entityClass, String pkName, Object pkValue,String[] propNames, Object[] propValues);
	
	public void saveorupdate(T entity);
	/**
	 * 删除
	 * */
	public void delete(T entity);
	
	public void deleteById(Class<T> entityClass, PK id);
	
	public void deleteByProperty(final Class<T> entityClass,final String propName, final Object propValue);
	
	public void deleteByProperty(final Class<T> entityClass,final String[] propNames, final Object[] propValues);
	
	/*****
	 * 查询
	 *****/
	
	public T findEnityById(Class<T> entityClass, PK id);
	
	public List<T> findAll(Class<T> entityClass);
	
	public List<T> findAllAsc(Class<T> entityClass, String orderProperty);
	
	public List<T> findAllDesc(Class<T> entityClass, String orderProperty);
	
	public List<T> findAll_order(Class<T> entityClass, String orderDesc);
	
	public List<T> findAllByProperty(Class<T> entityClass, String propertyName,Object propertyValue);
	
	public List<T> findAllByProperty_order(Class<T> entityClass,String propertyName, Object propertyValue, String orderDesc);
	
	public List<T> findAllByProperties(Class<T> entityClass,String[] propertyNames, Object[] propertyValues);
	
	public List<T> findAllByProperties_order(Class<T> entityClass,String[] propertyNames, Object[] propertyValues, String orderDesc);
	
	public List<T> findAllByLikeProperty(Class<T> entityClass,String propertyName, String propertyValue);
	
	public List<T> findAllByLikeProperty_order(Class<T> entityClass,String propertyName, String propertyValue, String orderDesc);
	
	public List<T> findAllByHQL(final String hql);
	
	public List<T> findAllByHQL(final String hql, final Object[] params);
	
	public List<T> findAllByPage(Class<T> entityClass, int start, int limit);
	
	public List<T> findAllByPage_order(Class<T> entityClass, String orderDesc,int start, int limit);
	
	public List<T> findAllByPropertyPage(Class<T> entityClass,String propertyName, Object propertyValue, int start, int limit);
	
	public List<T> findAllByPropertyPage_order(Class<T> entityClass,String propertyName, Object propertyValue, String orderDesc,int start, int limit);

	public List<T> findAllByPropertiesPage(Class<T> entityClass,String[] propertyNames, Object[] propertyValues, int start,int limit);
	
	public List<T> findAllByPropertiesPage_order(Class<T> entityClass,String[] propertyNames, Object[] propertyValues, String orderDesc,int start, int limit); 

	public List<T> findAllByLikePropertyPage(Class<T> entityClass,String propertyName, String propertyValue, int start, int limit);
	
	public List<T> findAllByLikePropertyPage_order(Class<T> entityClass,String propertyName, String propertyValue, String orderDesc,int start, int limit);
	
	public List<T> findAllByHQLPage(final String hql, final Object[] params,final int start, final int limit); 
	
	public Long getTotalCount(final Class<T> entityClass); 
	
	public Long getTotalCountByHQL(final String hql, final Object[] params);
	
	public Long getTotalCountByHQL(final String hql);
	
	/***************************************************************************
	 * 以下部分是QBE查询
	 **************************************************************************/
	
	
}
