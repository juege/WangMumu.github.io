package com.coe.framework.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.coe.framework.dao.BaseDao;

/**
 * T extends Serializable T其实就是一个占位符, 你也可以换成其它字母,但作为惯例 当我们需要一种类型时,我们都T来占位,表示Type ,
 * 后面的extends Serializable 表示在实际应用过程中,替换T的类型必须实现Serializable接口. PK => Primary
 * Key
 */
public class BaseDaoImpl<T extends Serializable, PK extends Serializable>
		extends HibernateDaoSupport implements BaseDao<T, PK> {

	/***************************************************************************
	 * 新增
	 **************************************************************************/

	/**
	 * 新增对象
	 * 
	 * @see com.chinarewards.framework.dao.BaseDao#save(T)
	 */
	public Serializable save(T entity) {
		return super.getHibernateTemplate().save(entity);
	}

	/***************************************************************************
	 * 更新
	 **************************************************************************/
	/**
	 * 更新对象
	 * 
	 * @see com.chinarewards.framework.dao#update(T)
	 */
	public void update(T t) {
		super.getHibernateTemplate().update(t);
	}

	/**
	 * HQL语句更新
	 * 
	 * @see com.chinarewards.framework.dao#update(java.lang.String, java.lang.Object[])
	 */
	public void update(final String hql, final Object[] params) {
		// 使用回调接口完成操作
		super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						System.out.println(hql);
						for (int i = 0; i < params.length; i++) {
							query.setParameter(i, params[i]);
						}
						query.executeUpdate();
						return null;
					}
				});
	}

	/**
	 * 根据主键PK更新单个字段值
	 * 
	 * @see com.chinarewards.framework.dao#update(java.lang.Class, java.lang.String,
	 * java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void update(Class<T> entityClass, String pkName, Object pkValue,
			String propName, Object propValue) {
		this.update(entityClass, pkName, pkValue, new String[] { propName },
				new Object[] { propValue });
	}

	/**
	 * 根据主键PK更新多个字段值
	 * 
	 * @see com.chinarewards.framework.dao#update(java.lang.Class, java.lang.String,
	 * java.lang.Object, java.lang.String[], java.lang.Object[])
	 */
	public void update(Class<T> entityClass, String pkName, Object pkValue,
			String[] propNames, Object[] propValues) {
		if (!(propNames != null && propValues != null
				&& propNames.length == propValues.length && propNames.length > 0)) {
			throw new RuntimeException(
					"请确保提供的参数是正确的!属性名称的个数与属性值的个数必须一致!必须提供至少一个属性名称!");
		}
		String entityName = entityClass.getName();
		if (entityName.lastIndexOf(".") != -1) {
			entityName = entityName.substring(entityName.lastIndexOf(".") + 1);
		}
		String hql = "update " + entityName + " obj set ";
		for (int i = 0; i < propValues.length; i++) {
			hql += "obj." + propNames[i] + " =? and ";
		}
		if (hql.lastIndexOf("and") != -1) {
			hql = hql.substring(0, hql.lastIndexOf("and"));
		}
		hql += " where obj." + pkName + " = ?";
		Object[] ps = new Object[propValues.length + 1];
		for (int i = 0; i < propValues.length; i++) {
			ps[i] = propValues[i];
		}
		ps[ps.length - 1] = pkValue;
		this.update(hql, ps);
	}

	/**
	 * 更新或保存方法
	 * 
	 * @see com.chinarewards.framework.dao#saveorupdate(T)
	 */
	public void saveorupdate(T entity) {
		super.getHibernateTemplate().saveOrUpdate(entity);
	}

	/***************************************************************************
	 * 删除
	 **************************************************************************/

	/**
	 * 对象删除
	 * 
	 * @see com.chinarewards.framework.dao#delete(T)
	 */
	public void delete(T entity) {
		super.getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据主键删除对象
	 * 
	 * @see com.chinarewards.framework.dao#deleteById(java.lang.Class, PK)
	 */
	public void deleteById(Class<T> entityClass, PK id) {
		super.getHibernateTemplate()
				.delete(this.findEnityById(entityClass, id));
	}

	/**
	 * 根据字段值删除对象
	 * 
	 * @see com.chinarewards.framework.dao#deleteByProperty(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	public void deleteByProperty(final Class<T> entityClass,
			final String propName, final Object propValue) {
		this.deleteByProperty(entityClass, new String[] { propName },
				new Object[] { propValue });
	}

	/**
	 * 根据多个字段值删除对象 多个字段之间为AND关系  
	 * 
	 * @see com.chinarewards.framework.dao#deleteByProperty(java.lang.Class,
	 * java.lang.String[], java.lang.Object[])
	 */
	public void deleteByProperty(final Class<T> entityClass,
			final String[] propNames, final Object[] propValues) {
		if (!(propNames != null && propValues != null
				&& propNames.length == propValues.length && propNames.length > 0)) {
			throw new RuntimeException(
					"请确保提供的参数是正确的!属性名称的个数与属性值的个数必须一致!必须提供至少一个属性名称!");
		}
		String entityName = entityClass.getName();
		if (entityName.lastIndexOf(".") != -1) {
			entityName = entityName.substring(entityName.lastIndexOf(".") + 1);
		}
		String hql = "delete from " + entityName + " obj where ";
		for (int i = 0; i < propNames.length; i++) {
			hql += " obj." + propNames[i] + " = ? and ";
		}
		if (hql.lastIndexOf("and") != -1) {
			hql = hql.substring(0, hql.lastIndexOf("and"));
		}
		this.update(hql, propValues);
	}

	/***************************************************************************
	 * 查询
	 **************************************************************************/

	/**
	 * 根据主键查询记录
	 * 
	 * @see com.chinarewards.framework.dao#findEnityById(java.lang.Class, PK)
	 */
	public T findEnityById(Class<T> entityClass, PK id) {
		return (T) super.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 查询全部记录
	 * 
	 * @see com.chinarewards.framework.dao#findAll(java.lang.Class)
	 */
	public List<T> findAll(Class<T> entityClass) {
		return super.getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 按指定某个字段正序查询全部记录
	 * 
	 * @see com.chinarewards.framework.dao#findAllAsc(java.lang.Class, java.lang.String)
	 */
	public List<T> findAllAsc(Class<T> entityClass, String orderProperty) {
		return this.findAll_order(entityClass, orderProperty, "asc");
	}

	/**
	 * 按指定某个字段正序查询全部记录
	 * 
	 * @see com.chinarewards.framework.dao#findAllDesc(java.lang.Class, java.lang.String)
	 */
	public List<T> findAllDesc(Class<T> entityClass, String orderProperty) {
		return this.findAll_order(entityClass, orderProperty, "desc");
	}

	/**
	 * 按指定某个字段排序查询全部记录
	 * 
	 * @see com.chinarewards.framework.dao#findAll_order(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	public List<T> findAll_order(Class<T> entityClass, String orderProperty,
			String orderType) {
		String hql = "from " + entityClass.getName() + " obj order by obj."
				+ orderProperty + " " + orderType;
		return this.findAllByHQL(hql);
	}

	/*
	 * 自定义排序 查询所有实体集合
	 * 
	 * @param entityClass 实体类型信息
	 * 
	 * @return 查询到的实体对象集合
	 */
	/**
	 * 自定义排序 查询所有实体集合
	 * 
	 * @see com.chinarewards.framework.dao#findAll_order(java.lang.Class,
	 * java.lang.String)
	 */
	public List<T> findAll_order(Class<T> entityClass, String orderDesc) {
		String hql = "from " + entityClass.getName() + " obj order by "
				+ orderDesc;
		return this.findAllByHQL(hql);
	}

	/**
	 * 根据单个条件查询
	 * 
	 * @see com.chinarewards.framework.dao#findAllByProperty(java.lang.Class,
	 * java.lang.String, java.lang.Object)
	 */
	public List<T> findAllByProperty(Class<T> entityClass, String propertyName,
			Object propertyValue) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + "=?";
		return super.getHibernateTemplate().find(queryString, propertyValue);
	}

	/**
	 * 根据单个条件查询，自定义排序
	 * 
	 * @see com.chinarewards.framework.dao#findAllByProperty_order(java.lang.Class,
	 * java.lang.String, java.lang.Object, java.lang.String)
	 */
	public List<T> findAllByProperty_order(Class<T> entityClass,
			String propertyName, Object propertyValue, String orderDesc) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + "=? order by "
				+ orderDesc;
		return super.getHibernateTemplate().find(queryString, propertyValue);
	}

	/**
	 * 根据多个条件查询
	 * 
	 * @see com.chinarewards.framework.dao#findAllByProperties(java.lang.Class,
	 * java.lang.String[], java.lang.Object[])
	 */
	public List<T> findAllByProperties(Class<T> entityClass,
			String[] propertyNames, Object[] propertyValues) {
		if (!(propertyNames != null && propertyValues != null && propertyValues.length == propertyNames.length)) {
			throw new RuntimeException(
					"请提供正确的参数值！propertyNames与propertyValues必须一一对应!");
		}
		String queryString = "from " + entityClass.getName()
				+ " as model where ";
		for (int i = 0; i < propertyValues.length; i++) {
			queryString += " model." + propertyNames[i] + " = ? ";
			if (i != propertyValues.length - 1) {
				queryString += " and ";
			}
		}
		return this.findAllByHQL(queryString, propertyValues);
	}

	/**
	 * 根据多个条件查询，自定义排序
	 * 
	 * @see com.chinarewards.framework.dao#findAllByProperties_order(java.lang.Class,
	 * java.lang.String[], java.lang.Object[], java.lang.String)
	 */
	public List<T> findAllByProperties_order(Class<T> entityClass,
			String[] propertyNames, Object[] propertyValues, String orderDesc) {
		if (!(propertyNames != null && propertyValues != null && propertyValues.length == propertyNames.length)) {
			throw new RuntimeException(
					"请提供正确的参数值！propertyNames与propertyValues必须一一对应!");
		}
		String queryString = "from " + entityClass.getName()
				+ " as model where ";
		for (int i = 0; i < propertyValues.length; i++) {
			queryString += " model." + propertyNames[i] + " = ? ";
			if (i != propertyValues.length - 1) {
				queryString += " and ";
			}
		}
		queryString += " order by " + orderDesc;
		return this.findAllByHQL(queryString, propertyValues);
	}

	/**
	 * 根据单个条件模糊查询
	 * 
	 * @see com.chinarewards.framework.dao#findAllByLikeProperty(java.lang.Class,
	 * java.lang.String, java.lang.String)
	 */
	public List<T> findAllByLikeProperty(Class<T> entityClass,
			String propertyName, String propertyValue) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + " like '%"
				+ propertyValue + "%'";
		return super.getHibernateTemplate().find(queryString);
	}

	/**
	 * 根据单个条件模糊查询，自定义排序
	 * 
	 * @see com.chinarewards.framework.dao#findAllByLikeProperty_order(java.lang.Class,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<T> findAllByLikeProperty_order(Class<T> entityClass,
			String propertyName, String propertyValue, String orderDesc) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + " like '%"
				+ propertyValue + "%'";
		return super.getHibernateTemplate().find(queryString);
	}

	/**
	 * 自定义HQL查询全部记录
	 * 
	 * @see com.chinarewards.framework.dao#findAllByHQL(java.lang.String)
	 */
	public List<T> findAllByHQL(final String hql) {
		return this.findAllByHQLPage(hql, null, -1, -1);
	}

	/**
	 * 自定义HQL查询全部记录 以及查询参数
	 * 
	 * @see com.chinarewards.framework.dao#findAllByHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public List<T> findAllByHQL(final String hql, final Object[] params) {
		return this.findAllByHQLPage(hql, params, -1, -1);
	}

	/**
	 * 分页查询记录
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPage(java.lang.Class, int, int)
	 */
	public List<T> findAllByPage(Class<T> entityClass, int start, int limit) {
		String hql = "from " + entityClass.getName() + "  model";
		return this.findAllByHQLPage(hql, null, start, limit);
	}

	/**
	 * 分页查询记录，自定义排序记录
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPage_order(java.lang.Class,
	 * java.lang.String, int, int)
	 */
	public List<T> findAllByPage_order(Class<T> entityClass, String orderDesc,
			int start, int limit) {
		String hql = "from " + entityClass.getName() + "  model order by "
				+ orderDesc;
		return this.findAllByHQLPage(hql, null, start, limit);
	}

	/**
	 * 自定义单个分页查询条件
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPropertyPage(java.lang.Class,
	 * java.lang.String, java.lang.Object, int, int)
	 */
	public List<T> findAllByPropertyPage(Class<T> entityClass,
			String propertyName, Object propertyValue, int start, int limit) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + "= ? ";
		return this.findAllByHQLPage(queryString,
				new Object[] { propertyValue }, start, limit);
	}

	/**
	 * 自定义单个分页查询条件，自定义排序
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPropertyPage_order(java.lang.Class,
	 * java.lang.String, java.lang.Object, java.lang.String, int, int)
	 */
	public List<T> findAllByPropertyPage_order(Class<T> entityClass,
			String propertyName, Object propertyValue, String orderDesc,
			int start, int limit) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + "=? order by "
				+ orderDesc;
		return this.findAllByHQLPage(queryString,
				new Object[] { propertyValue }, start, limit);
	}

	/**
	 * 自定义多个分页查询条件
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPropertiesPage(java.lang.Class,
	 * java.lang.String[], java.lang.Object[], int, int)
	 */
	public List<T> findAllByPropertiesPage(Class<T> entityClass,
			String[] propertyNames, Object[] propertyValues, int start,
			int limit) {
		if (!(propertyNames != null && propertyValues != null && propertyValues.length == propertyNames.length)) {
			throw new RuntimeException(
					"请提供正确的参数值！propertyNames与propertyValues必须一一对应!");
		}
		String queryString = "from " + entityClass.getName()
				+ " as model where ";
		for (int i = 0; i < propertyValues.length; i++) {
			queryString += " model." + propertyNames[i] + " = ? ";
			if (i != propertyValues.length - 1) {
				queryString += " and ";
			}
		}
		return this.findAllByHQLPage(queryString, propertyValues, start, limit);
	}

	/**
	 * 自定义多个分页查询条件，自定义排序
	 * 
	 * @see com.chinarewards.framework.dao#findAllByPropertiesPage_order(java.lang.Class,
	 * java.lang.String[], java.lang.Object[], java.lang.String, int, int)
	 */
	public List<T> findAllByPropertiesPage_order(Class<T> entityClass,
			String[] propertyNames, Object[] propertyValues, String orderDesc,
			int start, int limit) {
		if (!(propertyNames != null && propertyValues != null && propertyValues.length == propertyNames.length)) {
			throw new RuntimeException(
					"请提供正确的参数值！propertyNames与propertyValues必须一一对应!");
		}
		String queryString = "from " + entityClass.getName()
				+ " as model where ";
		for (int i = 0; i < propertyValues.length; i++) {
			queryString += " model." + propertyNames[i] + " = ? ";
			if (i != propertyValues.length - 1) {
				queryString += " and ";
			}
		}
		queryString += " order by " + orderDesc;
		return this.findAllByHQLPage(queryString, propertyValues, start, limit);
	}

	/**
	 * 单个条件模糊查询
	 * 
	 * @see com.chinarewards.framework.dao#findAllByLikePropertyPage(java.lang.Class,
	 * java.lang.String, java.lang.String, int, int)
	 */
	public List<T> findAllByLikePropertyPage(Class<T> entityClass,
			String propertyName, String propertyValue, int start, int limit) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + " like '%"
				+ propertyValue + "%'";
		return this.findAllByHQLPage(queryString, null, start, limit);
	}

	/**
	 * 单个条件模糊查询,自定义排序
	 * 
	 * @see
	 * com.chinarewards.framework.dao#findAllByLikePropertyPage_order(java.lang.Class,
	 * java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<T> findAllByLikePropertyPage_order(Class<T> entityClass,
			String propertyName, String propertyValue, String orderDesc,
			int start, int limit) {
		String queryString = "from " + entityClass.getName()
				+ " as model where model." + propertyName + " like '%"
				+ propertyValue + "%'";
		queryString += " order by " + orderDesc;
		return this.findAllByHQLPage(queryString, null, start, limit);
	}

	/**
	 * 多字段自定义查询
	 * 
	 * @see com.chinarewards.framework.dao#findAllByHQLPage(java.lang.String,
	 * java.lang.Object[], int, int)
	 */
	public List<T> findAllByHQLPage(final String hql, final Object[] params,
			final int start, final int limit) {
		return (List<T>) super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						if (params != null && params.length > 0) {
							for (int i = 0; i < params.length; i++) {
								query.setParameter(i, params[i]);
							}
						}
						// 表示是分页查询
						if (start != -1 && limit != -1) {
							query.setFirstResult(start);
							query.setMaxResults(limit);
						}
						return query.list();
					}
				});
	}

	/**
	 * 获取对象记录总数
	 * 
	 * @see com.chinarewards.framework.dao#getTotalCount(java.lang.Class)
	 */
	public Long getTotalCount(final Class<T> entityClass) {
		return (Long) super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "select count(o) from "
								+ entityClass.getName() + " o";
						Query query = session.createQuery(hql);
						Object obj = query.uniqueResult();
						return obj;
					}
				});
	}

	/**
	 * 自定义HQL查询条件记录总数
	 * 
	 * @see com.chinarewards.framework.dao#getTotalCountByHQL(java.lang.String,
	 * java.lang.Object[])
	 */
	public Long getTotalCountByHQL(final String hql, final Object[] params) {
		return (Long) super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						if (params != null && params.length > 0) {
							for (int i = 0; i < params.length; i++) {
								query.setParameter(i, params[i]);
							}
						}
						return query.uniqueResult();
					}
				});
	}

	/**
	 * 自定义HQL查询总数
	 * 
	 * @see com.chinarewards.framework.dao#getTotalCountByHQL(java.lang.String)
	 */
	public Long getTotalCountByHQL(final String hql) {
		return this.getTotalCountByHQL(hql, null);
	}

	/***************************************************************************
	 * 以下部分是QBE查询
	 **************************************************************************/

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBE(java.lang.Class, T)
	 */
	public List<T> findAllByQBE(final Class<T> entityClass, final T example) {
		return this.findAllByQBEPage(entityClass, example, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBEPage(java.lang.Class, T, int, int)
	 */
	public List<T> findAllByQBEPage(final Class<T> entityClass,
			final T example, final int start, final int limit) {
		return this.findAllByQBEPage(entityClass, example, start, limit, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBEPage(java.lang.Class, T, int, int,
	 * org.hibernate.criterion.Order[])
	 */
	public List<T> findAllByQBEPage(final Class<T> entityClass,
			final T example, final int start, final int limit,
			final Order[] orders) {
		return (List<T>) super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(entityClass);
						criteria.add(Example.create(example));
						// 设置排序
						if (orders != null && orders.length > 0) {
							for (int i = 0; i < orders.length; i++) {
								criteria.addOrder(orders[i]);
							}
						}
						if (start != -1 && limit != -1) {
							criteria.setFirstResult(start);
							criteria.setMaxResults(limit);
						}
						return criteria.list();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#getStatisticalValueByQBE(java.lang.Class, T,
	 * org.hibernate.criterion.Projection)
	 */
	public Object getStatisticalValueByQBE(final Class<T> entityClass,
			final T example, final Projection projection) {
		return super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(entityClass);
						criteria.add(Example.create(example));
						criteria.setProjection(projection);
						return criteria.uniqueResult();
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#getTotalCountByExample(java.lang.Class, T)
	 */
	public Integer getTotalCountByExample(final Class<T> entityClass,
			final T example) {
		return (Integer) super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(entityClass);
						criteria.add(Example.create(example));
						criteria.setProjection(Projections.rowCount());// 总行数
						return criteria.uniqueResult();
					}

				});
	}

	/***************************************************************************
	 * 以下是QBC查询
	 **************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBCPage(java.lang.Class, int, int,
	 * org.hibernate.criterion.Criterion[], org.hibernate.criterion.Order[],
	 * org.hibernate.criterion.Projection[], boolean)
	 */
	public Object findAllByQBCPage(final Class<T> entityClass, final int start,
			final int limit, final Criterion[] criterions,
			final Order[] orders, final Projection[] projs,
			final boolean isUniqueResult) {
		return super.getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(entityClass);
						// 添加条件
						if (criterions != null && criterions.length > 0) {
							for (int i = 0; i < criterions.length; i++) {
								criteria.add(criterions[i]);
							}
						}
						// 添加排序
						if (orders != null && orders.length > 0) {
							for (int i = 0; i < orders.length; i++) {
								criteria.addOrder(orders[i]);
							}
						}
						// 添加分组统计
						if (projs != null && projs.length > 0) {
							for (int i = 0; i < projs.length; i++) {
								criteria.setProjection(projs[i]);
							}
						}
						// 查看是否要分页
						if (start != -1 && limit != -1) {
							criteria.setFirstResult(start);
							criteria.setMaxResults(limit);
						}

						if (isUniqueResult) {
							return criteria.uniqueResult();
						} else {
							return criteria.list();
						}
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBCPage(java.lang.Class, int, int,
	 * org.hibernate.criterion.Criterion[], org.hibernate.criterion.Order[])
	 */
	public List<T> findAllByQBCPage(final Class<T> entityClass,
			final int start, final int limit, final Criterion[] criterions,
			final Order[] orders) {
		return (List<T>) this.findAllByQBCPage(entityClass, start, limit,
				criterions, orders, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBCPage(java.lang.Class, int, int,
	 * org.hibernate.criterion.Criterion[])
	 */
	public List<T> findAllByQBCPage(final Class<T> entityClass,
			final int start, final int limit, final Criterion[] criterions) {
		return (List<T>) this.findAllByQBCPage(entityClass, start, limit,
				criterions, null, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#findAllByQBCPage(java.lang.Class,
	 * org.hibernate.criterion.Criterion[])
	 */
	public List<T> findAllByQBCPage(final Class<T> entityClass,
			final Criterion[] criterions) {
		return (List<T>) this.findAllByQBCPage(entityClass, -1, -1, criterions,
				null, null, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.framework.dao#getTotalCountByQBC(java.lang.Class,
	 * org.hibernate.criterion.Criterion[])
	 */
	public Integer getTotalCountByQBC(final Class<T> entityClass,
			final Criterion[] criterions) {
		return (Integer) this.findAllByQBCPage(entityClass, -1, -1, criterions,
				null, new Projection[] { Projections.rowCount() }, true);
	}

}