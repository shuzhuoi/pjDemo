package com.shuzhuo.core.system.sys.mapper;

import java.util.List;
import java.util.Map;

import com.shuzhuo.core.system.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;


/**
 * <p>
  * 角色表 Mapper 接口
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-29
 */
public interface RoleMapper extends BaseMapper<Role> {
	
	/**
	 * 查询用户的所有角色
	 * @param userId  用户ID
	 */
	List<Role> selectUserRole(Long userId);
	
	/**
	 * 查询用户的所有角色
	 * @param wrapper  包装类
	 */
	List<Role> selectUserRoleList(@Param("ew") Wrapper<Role> wrapper);
	
	/**
	 * 插入角色对应的用户
	 */
	void insertUserRole(Map<String, Object> map);
	
	/**
	 * 插入角色对应的菜单
	 */
	void insertMenuRole(Map<String, Object> map);

	/**
	 * 获取角色列表
	 * @param wrapper
	 * @return
	 */
	List<Role> selectRoleList(@Param("ew") EntityWrapper<Role> wrapper);
}