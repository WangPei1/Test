package org.wp.elearn.model;



public class SystemContext {
private static ThreadLocal<Integer> pageSize=new ThreadLocal<Integer>();
private static ThreadLocal<Integer> pageOffset=new ThreadLocal<Integer>();
private static ThreadLocal<String> sort=new ThreadLocal<String>();
private static ThreadLocal<String> order=new ThreadLocal<String>();
private static ThreadLocal<String> realpath=new ThreadLocal<String>();
public static Integer getPageSize() {
	return pageSize.get();
}
public static void setPageSize(Integer pageSize) {
	SystemContext.pageSize.set(pageSize);
}
public static Integer getPageOffset() {
	return pageOffset.get();
}
public static void setPageOffset(Integer pageOffset) {
	SystemContext.pageOffset.set(pageOffset);
}
public static String getSort() {
	return sort.get();
}
public static void setSort(String sort) {
	SystemContext.sort.set(sort);
}
public static String getOrder() {
	return order.get();
}
public static void setOrder(String order) {
	SystemContext.order.set(order);
}
public static String getRealpath() {
	return realpath.get();
}
public static void setRealpath(String realpath) {
	SystemContext.realpath.set(realpath);
}
public  static void removePageSize(){
	pageSize.remove();
}
public  static void removePageOffset(){
	pageOffset.remove();
}
public  static void removeSort(){
	sort.remove();
}
public  static void removeOrder(){
	order.remove();
}

}


