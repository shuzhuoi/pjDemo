package com.shuzhuo.core.system.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.shuzhuo.core.system.sys.entity.Menu;


/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
public interface IMenuService extends IService<Menu> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<Menu> queryListParentId(Long parentId);
	
	/**
	 * 获取角色的菜单列表
	 */
	List<Menu> getRoleMenuList(Long roleId);
	
	/**
	 * 获取菜单列表
	 */
	List<Menu> getMenuList(int menuType);
	
	/**
	 * 获取用户的菜单列表
	 */
	List<Menu> getUserMenuList(Long userId, Integer userType);
	
	/**
	 * 获取用户的菜单列表
	 */
	List<Menu> getUserMenuList2(Long userId, Integer userType);

}
