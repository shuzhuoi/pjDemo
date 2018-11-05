package com.shuzhuo.core.system.sys.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.system.sys.entity.Menu;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.entity.UserToken;
import com.shuzhuo.core.system.sys.mapper.MenuMapper;
import com.shuzhuo.core.system.sys.mapper.UserMapper;
import com.shuzhuo.core.system.sys.mapper.UserTokenMapper;
import com.shuzhuo.core.system.sys.service.ShiroService;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private MenuMapper sysMenuDao;
    @Autowired
    private UserMapper sysUserDao;
    @Autowired
    private UserTokenMapper sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<Menu> menuList = sysMenuDao.selectByMap(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for(Menu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public UserToken queryByToken(String token) {
    	UserToken entity = new UserToken();
    	entity.setToken(token);
        return sysUserTokenDao.selectOne(entity);
    }

    @Override
    public User queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
