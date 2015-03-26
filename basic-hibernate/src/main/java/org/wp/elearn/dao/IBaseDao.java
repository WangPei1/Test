package org.wp.elearn.dao;

public interface IBaseDao<T> {
/*
 * 增加一个对象
 */
	public T add(T t );
	/*
	 * 更新一个对象
	 */
	public  void update(T t);
	/*
	 * 根据ID删除对象
	 */
	public void  delete(Integer id);
	/*
	 * 根据ID查询对象
	 */
	public T select(Integer id);
}
