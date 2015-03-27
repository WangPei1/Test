package org.wp.elearn.dao;

import java.lang.reflect.ParameterizedType;
import java.net.CookieHandler;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.internal.ast.HqlASTFactory;
import org.wp.elearn.model.Pager;
import org.wp.elearn.model.SystemContext;

public class BaseDao<T> implements IBaseDao<T> {
	// 通过依赖注入获得SessionFactory
	@Inject
	private SessionFactory sessionFactory;

	// 使用了inject注解过后可以省去getter 和setter方法
	/*
	 * public SessionFactory getSessionFactory() { return sessionFactory; }
	 * public void setSessionFactory(SessionFactory sessionFactory) {
	 * this.sessionFactory = sessionFactory; }
	 */
	/**
	 * 获取当前的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 定义了一个class，获取泛型的class；
	 */
	private Class<?> clz;

	public Class<?> getClz() {
		if (clz == null) {
			// 获取泛型的Class对象
			clz = ((Class<?>) (((ParameterizedType) (this.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void update(T t) {
		getSession().update(t);

	}

	@Override
	public void delete(Integer id) {
		getSession().delete(this.select(id));

	}

	@SuppressWarnings("unchecked")
	@Override
	public T select(Integer id) {
		return (T) getSession().load(getClz(), id);
	}

	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort != null && !"".equals(sort.trim())) {
			hql += "order by" + sort;
			if (!"desc".equals(order)) {
				hql += "asc ";

			} else {
				hql += "desc";
			}
		}
		return hql;
	}

	private void setAliasParameter(Query query, Map<String, Object> alias) {
		if (alias != null) {
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object val = alias.get(key);
				if (val instanceof Collection) {
					query.setParameterList(key, (Collection) val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}
//设置带参数的查询
	private void setParameter(Query query, Object[] args) {
		if (args != null && args.length > 0) {
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}
	public List<T> list(String hql,Object[] args,Map<String, Object> alias){
		hql = initSort(hql);
		Query query =getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}
	public List<T> listByParameter(String hql,Object[] args){
		return this.list(hql, args, null);
	}
	public List<T> listByAlias(String hql, Map<String , Object> alias){
		return this.list(hql, null, alias);
	}
	public List<T> list(String hql,Object obj){
		return this.list(hql, new Object[]{obj});
	}
	
	//分页查询
	private void setPagers(Query query,Pager pager){
		Integer pageSize= SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset  ==null || pageOffset<0){
			pageOffset=0;
		}
		if(pageSize == null || pageSize<0){
			pageSize=5;
		}
		pager.setPageOffset(pageOffset);
		pager.setPageSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	private String getCounthql(String hql,boolean isHql){
		String  e=hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+e;
		if(isHql){
			c=c.replaceAll("fetch", "");
		}
		return c;
	}
	@SuppressWarnings("unchecked")
	public Pager<T> find(String hql,Object[] args,Map<String, Object> alias){
		hql = initSort(hql);
		String cq =getCounthql(hql, true);
		Query query=getSession().createQuery(cq);
		Query query2=getSession().createQuery(hql);
		setAliasParameter(query2, alias);
		setAliasParameter(query, alias);
		setParameter(query2, args);
		setParameter(query, args);
		Pager<T> pages =new Pager<T>();
		setPagers(query2, pages);
		List<T> datas=query2.list();
		pages.setDatas(datas);
		Long total =(Long) query.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	
	public Pager<T> findByParameter(String hql,Object[] args){
		return this.find(hql, args, null);
	}
	
	public Pager<T> findByOneArgs(String hql,Object arg){
		return this.findByParameter(hql, new Object[]{arg});
	}
	
	public Pager<T> findByAlias(String hql,Map<String, Object> alias){
		return this.find(hql, null, alias);
	}
	
	
	
	
}
