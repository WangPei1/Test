package org.wp.elearn.dao; 

 
 
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.wp.elearn.model.Pager;
import org.wp.elearn.model.User;

 
 
 //@Repository是spring基于持久层（dao）的注入注解；而相对应的业务层为@Service;控制器层为@Controller 
 @Repository("userDao") 
 public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public Pager<User> find(String hql, Object[] args, Map<String, Object> alias) {
		return super.find(hql, args, alias);
	}

	@Override
	public Pager<User> findByParameter(String hql, Object[] args) {
		
		return super.findByParameter(hql, args);
	}

	@Override
	public Pager<User> findByOneArgs(String hql, Object arg) {
		
		return super.findByOneArgs(hql, arg);
	}

	@Override
	public Pager<User> findByAlias(String hql, Map<String, Object> alias) {
		
		return super.findByAlias(hql, alias);
	} 
	
	 
 } 

