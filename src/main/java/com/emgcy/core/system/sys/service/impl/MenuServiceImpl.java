package com.emgcy.core.system.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.emgcy.core.system.sys.entity.Menu;
import com.emgcy.core.system.sys.mapper.MenuMapper;
import com.emgcy.core.system.sys.service.IMenuService;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements com.emgcy.core.system.sys.service.IMenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuDao;

	@Override
	public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<Menu> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<Menu> userMenuList = new ArrayList<>();
		for(Menu menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<Menu> queryListParentId(Long parentId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parent_id", parentId);
		return menuDao.selectByMap(map);
	}
	
	@Override
	public List<Menu> getRoleMenuList(Long roleId) {
		//角色对应的菜单列表
		List<Long> menuIdList = menuDao.queryRoleMenuId(roleId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public List<Menu> getMenuList(int menuType) {
		//医院管理员的菜单列表
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("menuType", menuType);
		map.put("userType", 2);
		List<Long> menuIdList = menuDao.getMenuList(map);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public List<Menu> getUserMenuList(Long userId, Integer userType) {
		List<Long> menuIdList = new ArrayList<>();
		//系统管理员或医院管理员菜单
		if (userType==1||userType==2) {
			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("menuType", menuType);
			map.put("userType", userType);
			menuIdList = menuDao.getMenuList(map);
		} else {
			//普通用户菜单
			menuIdList = menuDao.queryAllMenuId(userId);
		}
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public List<Menu> getUserMenuList2(Long userId, Integer userType) {
		List<Menu> lists = new ArrayList<Menu>();
		//系统管理员或医院管理员菜单
		if (userType==1||userType==2) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userType", userType);
			lists = menuDao.getMenuList2(map);
		} else {
			//普通用户菜单
			lists = menuDao.queryAllMenuId2(userId);
		}
		return lists;
	}
	
	/**
	 * 获取所有菜单列表
	 */
	private List<Menu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<Menu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList){
		List<Menu> subMenuList = new ArrayList<Menu>();
		
		for(Menu entity : menuList){
			if(entity.getType() == 0){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}


	
}
