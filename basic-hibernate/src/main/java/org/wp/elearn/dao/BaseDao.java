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
import org.hibernate.Session;
import org.hibernate.SessionFactory;



public class BaseDao<T> implements IBaseDao<T>{
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

}
