package com.emgcy.core.system.sys.service;


import java.util.Set;

import com.emgcy.core.system.sys.entity.User;
import com.emgcy.core.system.sys.entity.UserToken;

/**
 * shiro相关接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    UserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    User queryUser(Long userId);
}
