package com.shuzhuo.core.system.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuzhuo.core.system.sys.entity.Role;
import com.shuzhuo.core.system.sys.mapper.RoleMenuMapper;
import com.shuzhuo.core.system.sys.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shuzhuo.core.system.sys.entity.Menu;
import com.shuzhuo.core.system.sys.mapper.RoleMapper;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private RoleMenuMapper roleMenuDao;
	
	@Override
	public List<Role> selectRoleUserList(Long userId, Wrapper<Role> wrapper) {
		wrapper.andNew("ur.user_id = {0}",userId);
		return roleDao.selectUserRoleList(wrapper);
	}
	
	@Override
	@Transactional
	public boolean insertRole(Role role) {
		//默认角色状态为启用
		role.setStatus(1);
		Integer count = roleDao.insert(role);
		if(count!=null && count>0){
			if (role.getMenuList()!=null&&role.getMenuList().size()>0) {
				List<Long> menuIdList = new ArrayList<>();
				for (Menu menu:role.getMenuList()) {
					menuIdList.add(menu.getId());
				}
				//保存角色对应的菜单
				Map<String, Object> map = new HashMap<>();
				map.put("roleId", role.getId());
				map.put("menuIdList", menuIdList);
				roleDao.insertMenuRole(map);
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateRole(Role role) {
		Role bean = roleDao.selectById(role.getId());
		if (role.getRoleType()!=null) {
			bean.setRoleType(role.getRoleType());
		}
		if (role.getRoleName()!=null) {
			bean.setRoleName(role.getRoleName());
		}
		if (role.getSummary()!=null) {
			bean.setSummary(role.getSummary());
		}
		Integer count = roleDao.updateAllColumnById(bean);
		if (count!=null && count>0){
			//先删除角色的所有菜单
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("role_id", role.getId());
			roleMenuDao.deleteByMap(map);
			
			//保存角色对应的菜单
			if (role.getMenuList()!=null&&role.getMenuList().size()>0) {
				List<Long> menuIdList = new ArrayList<>();
				for (Menu menu:role.getMenuList()) {
					menuIdList.add(menu.getId());
				}
				
				Map<String, Object> menuMap = new HashMap<>();
				menuMap.put("roleId", role.getId());
				menuMap.put("menuIdList", menuIdList);
				roleDao.insertMenuRole(menuMap);
			}
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public void insertUserRole(List<Long> userIdList, Long roleId) {
		if(userIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userIdList", userIdList);
		map.put("roleId", roleId);
		roleDao.insertUserRole(map);
	}

	/**
	 * 获取角色列表
	 * @param wrapper
	 * @return
	 */
	@Override
	public List<Role> selectRoleList(EntityWrapper<Role> wrapper) {
		return roleDao.selectRoleList(wrapper);
	}
	
}
