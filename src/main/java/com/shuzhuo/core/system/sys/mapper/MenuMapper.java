package com.shuzhuo.core.system.sys.mapper;

import com.shuzhuo.core.system.sys.entity.Menu;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;


/**
 * <p>
  * 菜单管理 Mapper 接口
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
public interface MenuMapper extends BaseMapper<Menu> {
	
	/**
	 * 查询角色的所有菜单ID
	 */
	List<Long> queryRoleMenuId(Long roleId);
	
	/**
	 * 根据用户类型查询前台或后台的菜单ID
	 */
	List<Long> getMenuList(Map<String, Object> map);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户类型查询前台或后台的菜单
	 */
	List<Menu> getMenuList2(Map<String, Object> map);
	
	/**
	 * 查询用户的所有菜单
	 */
	List<Menu> queryAllMenuId2(Long userId);


}