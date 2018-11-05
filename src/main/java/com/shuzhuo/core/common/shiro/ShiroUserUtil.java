package com.shuzhuo.core.common.shiro;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.util.SpringContextHolder;
import com.shuzhuo.core.system.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.shuzhuo.core.system.sys.mapper.RoleMapper;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-2
 */
public class ShiroUserUtil {
	
	private static RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
	
	/**
	 * 获取当前用户session
	 * @return session
	 */
	public static Session getCurrentUserSession(){
		return getSession(false);
	}
	
	/**
	 * 获取session对象
	 * @param flag
	 * @return
	 */
	public static Session getSession(boolean flag){
		Session session =SecurityUtils.getSubject().getSession(flag);
		return session;
	}
	
	/**
	 * 设置当前用户对象到session
	 * @return user
	 */
	public static void setUserToSession(User user){
		getSession(true).setAttribute(Constant.USER_SESSION_KEY, user);
	}
	
	/**
	 * session中获取当前用户对象
	 * @return user
	 */
	public static User getCurrentUser(){
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(user!=null){
			user.setRoleList(roleMapper.selectUserRole(user.getId()));
		}
		return user;
	}
	
	//查询用户类型
	public static Integer getUserType() {
		return getCurrentUser().getUserType();
	}

	public static Long getHospitalId() {
		return getCurrentUser().getOrgHospitalId();
	}
	
	public static Object getSessionAttribute(Object key) {
		return getSession(true).getAttribute(key);
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession(true).setAttribute(key, value);
	}
	

}
