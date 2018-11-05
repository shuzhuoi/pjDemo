package com.emgcy.core.system.sys.service.impl;

import com.emgcy.core.system.sys.entity.RoleMenu;
import com.emgcy.core.system.sys.mapper.RoleMenuMapper;
import com.emgcy.core.system.sys.service.IRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-29
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);
	
	@Autowired
	private RoleMenuMapper roleMenuDao;
	
}
