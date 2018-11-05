package com.emgcy.core.system.sys.service.impl;

import com.emgcy.core.system.sys.entity.UserRole;
import com.emgcy.core.system.sys.mapper.UserRoleMapper;
import com.emgcy.core.system.sys.service.IUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	private UserRoleMapper userRoleDao;
	
}
