package com.shuzhuo.core.system.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.shuzhuo.core.system.sys.entity.Role;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-29
 */
public interface IRoleService extends IService<Role> {
	
	List<Role> selectRoleUserList(Long userId, Wrapper<Role> wrapper);
	
	/**
	 * 新增角色
	 */
	boolean insertRole(Role role);
	
	/**
	 * 修改角色
	 */
	boolean updateRole(Role role);
	
	/**
	 * 插入角色对应的用户
	 */
	void insertUserRole(List<Long> userIdList, Long roleId);

    /**
     * 获取角色列表
     * @param wrapper
     * @return
     */
    List<Role> selectRoleList(EntityWrapper<Role> wrapper);
}
