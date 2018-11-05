package com.shuzhuo.core.system.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.entity.UserToken;

/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-10
 */
public interface IUserTokenService extends IService<UserToken> {
	
	/**
	 * 生成token
	 * @param User
	 */
	BaseMsg createToken(User user);
	
	/**
	 * 验证token
	 * @param token
	 */
	BaseMsg checkToken(String token);
	
}
