package org.wp.elearn.dao;

import java.util.Map;

import org.wp.elearn.model.Pager;
import org.wp.elearn.model.User;
import org.wp.elearn.dao.IBaseDao;

public interface IUserDao extends IBaseDao<User>{
	public Pager<User> find(String hql,Object[] args,Map<String, Object> alias);
	public Pager<User> findByParameter(String hql,Object[] args);
	public Pager<User> findByOneArgs(String hql,Object arg)	;
	public Pager<User> findByAlias(String hql,Map<String, Object> alias);
}
