package com.emgcy.core.system.sys.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.common.excel.dto.ImportExcelLogDTO;
import com.emgcy.core.common.excel.dto.UserExcelDTO;
import com.emgcy.core.system.sys.entity.User;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-24
 */
public interface IUserService extends IService<User> {
	
	/**
	 * 查询用户列表
	 */
	Page<User> queryList(Page<User> page, Wrapper<User> wrapper);

	/**
	 * 查询用户列表(不分页)
	 */
	List<User> queryList(Wrapper<User> wrapper);
	
	/**
	 * 判断用户是否已存在
	 */
	boolean isUserExist(User user);
	
	/**
	 * 新增用户
	 */
	boolean insertUser(User user,Integer userType);
	
	/**
	 * 修改用户
	 */
	boolean updateUser(User user);
	
	/**
	 * 修改用户(不修改培训教育)
	 */
	boolean updateUserNoTrain(User user);
	
	/**
	 * 导入用户签名
	 */
	BaseMsg importUserSign(Long userId, MultipartFile multipartFile) throws Exception;
	
	/**
	 * 导入用户签名(批量)
	 */
	BaseMsg importUserSignBatch(MultipartFile multipartFile) throws Exception;
	
	/**
	 * 获取角色绑定的用户列表
	 */
	Page<User> selectRoleUserList(Long roleId, Page<User> page, Wrapper<User> wrapper);
	
	/**
	 * 获取未绑定的用户列表
	 */
	Page<User> selectUnbindingRoleUserList(Long roleId, Page<User> page, Wrapper<User> wrapper);

	/**
	 * 根据角色获取用户列表
	 * @param wrapper
	 * @return
	 */
	List<User> selectUserListByRole(Wrapper<User> wrapper);
	
	/**
	 * 修改用户密码
	 */
	boolean updatePassword(Long userId, String password);
	
	/**
	 * 修改电子签名密码
	 */
	boolean updateSignPassword(Long userId, String password);
	
	/**
	 * excel导入用户
	 */
	ImportExcelLogDTO insertUserExcel(List<UserExcelDTO> list,int userType);

	
	/**
	 * 
	 * 查询拥有该菜单的用户id集合
	 * @param menuCode 菜单代码
	 * @return
	 */
	List<User> selectMenuUserId(String menuCode);
	
}
