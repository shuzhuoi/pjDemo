package com.emgcy.core.system.sys.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emgcy.core.common.util.SpringContextHolder;
import com.emgcy.core.system.attachment.util.AttachmentUtils;
import com.emgcy.core.system.sys.entity.User;
import com.emgcy.core.system.sys.mapper.UserMapper;

public class UserUtil {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentUtils.class);

	private static UserMapper userDao = SpringContextHolder.getBean(UserMapper.class);
	
	
	public static String getUserName(Long userId) {
		User user = userDao.selectById(userId);
		if(user==null)return null;
		return user.getName();
	}


	public static String getUserPhoto(Long userId) {
		User user = userDao.selectById(userId);
		if(user==null)return null;
		return AttachmentUtils.getSmallFilePath(user.getPhoto());
	}
	

	
	
	
}
