package com.shuzhuo.core.system.sys.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.common.config.ApplicationYmlConfig;
import com.shuzhuo.core.common.util.TokenGenerator;
import com.shuzhuo.core.system.sys.entity.Role;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.mapper.UserTokenMapper;
import com.shuzhuo.core.system.sys.service.IUserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shuzhuo.core.system.sys.entity.UserToken;
import com.shuzhuo.core.system.sys.mapper.RoleMapper;
import com.shuzhuo.core.system.sys.mapper.UserMapper;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-10
 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements IUserTokenService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);
	
	@Autowired
	private UserTokenMapper userTokenDao;
	@Autowired
    private RoleMapper roleMapper;
	@Autowired
	private UserMapper userDao;
	@Autowired
	private ApplicationYmlConfig config;
	//application配置过期时间
//	private int EXPIRE = 3600 * 12;

	@Override
	public BaseMsg createToken(User user) {
		int expire = 3600 * config.getTokenExpireTime();
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + expire * 1000);

		//判断是否生成过token
		UserToken tokenEntity = userTokenDao.selectById(user.getId());
		if(tokenEntity == null){
			tokenEntity = new UserToken();
			tokenEntity.setUserId(user.getId());
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			userTokenDao.insert(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			userTokenDao.updateById(tokenEntity);
		}
		//设置登录跳转到前台或后台
		Set<Integer> sets = new HashSet<Integer>();
		String loginSkip;
		//系统管理员和医院管理员默认跳转到后台
		if (user.getUserType()==1||user.getUserType()==2) {
			loginSkip = "2";
		}else {
			List<Role> roles = roleMapper.selectUserRole(user.getId());
			if(roles!=null&&roles.size()>0) {
				for(Role role:roles){
					sets.add(role.getRoleType());
				}
				if(sets.contains(1)) {
					loginSkip = "1";
				} else if(sets.contains(2)){
					loginSkip = "2";
				} else{
					return BaseMsg.errorMsg("该账号没绑定任何权限");
				}
			} else{
				return BaseMsg.errorMsg("该账号没绑定任何权限");
			}
		}

		BaseMsg msg = BaseMsg.success()
				.put("token", token)
				.put("expire", expire)
				.put("loginSkip", loginSkip)
				.put("userId", user.getId())
				.put("userType", user.getUserType());

		return msg;
	}

	@Override
	public BaseMsg checkToken(String token) {
		UserToken entity = new UserToken();
		entity.setToken(token);
		UserToken userToken = userTokenDao.selectOne(entity);
		if (userToken==null) {
			return BaseMsg.errorMsg("token不存在");
		}
		User user = userDao.selectById(userToken.getUserId());
		if (user==null) {
			return BaseMsg.errorMsg("用户不存在");
		}
		//设置登录跳转到前台或后台
		Set<Integer> sets = new HashSet<Integer>();
		String loginSkip;
		//系统管理员和医院管理员默认跳转到后台
		if (user.getUserType()==1||user.getUserType()==2) {
			loginSkip = "2";
		}else {
			List<Role> roles = roleMapper.selectUserRole(user.getId());
			if(roles!=null&&roles.size()>0) {
				for(Role role:roles){
					sets.add(role.getRoleType());
				}
				if(sets.contains(1)) {
					loginSkip = "1";
				} else if(sets.contains(2)){
					loginSkip = "2";
				} else{
					return BaseMsg.errorMsg("该账号没绑定任何权限");
				}
			} else{
				return BaseMsg.errorMsg("该账号没绑定任何权限");
			}
		}
		
		return BaseMsg.success()
				.put("loginSkip", loginSkip)
				.put("userId", user.getId())
				.put("userType", user.getUserType());
	}
	
}
