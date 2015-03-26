package org.wp.elearn.dao; 

 
 
import org.springframework.stereotype.Repository;
import org.wp.elearn.model.User;

 
 
 //@Repository是spring基于持久层（dao）的注入注解；而相对应的业务层为@Service;控制器层为@Controller 
 @Repository("userDao") 
 public class UserDao extends BaseDao<User> implements IUserDao { 
 
 
 } 

