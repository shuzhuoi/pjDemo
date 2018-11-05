package com.emgcy.core.system.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.emgcy.core.system.sys.entity.User;


/**
 * <p>
  * 系统用户表 Mapper 接口
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-24
 */
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 查询用户列表
	 */
	List<User> queryList(RowBounds rb, @Param("ew") Wrapper<User> wrapper);

	/**
	 * 查询用户列表(不分页)
	 */
	List<User> queryList(@Param("ew") Wrapper<User> wrapper);
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 获取角色绑定的用户列表
	 */
	List<User> selectRoleUserList(RowBounds rb, @Param("ew") Wrapper<User> wrapper);
	
	/**
	 * 获取未绑定的用户列表
	 */
	List<User> selectUnbindingRoleUserList(RowBounds rb, @Param("ew") Wrapper<User> wrapper);

	/**
	 * 根据角色获取用户列表
	 * @param wrapper
	 * @return
	 */
	List<User> selectUserListByRole(@Param("ew") Wrapper<User> wrapper);
	
	/**
	 * 查询拥有该菜单的用户id集合
	 * @param menuCode
	 * @return
	 */
	List<User> selectMenuUserId(String menuCode);
	
	/**
     * 根据id查询用户信息
     */
	User selectUserById(Long userId);
	
}