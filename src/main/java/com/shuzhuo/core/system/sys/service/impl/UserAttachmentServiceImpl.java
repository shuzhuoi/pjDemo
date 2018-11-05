package com.shuzhuo.core.system.sys.service.impl;

import com.shuzhuo.core.system.sys.entity.UserAttachment;
import com.shuzhuo.core.system.sys.mapper.UserAttachmentMapper;
import com.shuzhuo.core.system.sys.service.IUserAttachmentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 用户附件表 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2018-01-10
 */
@Service
public class UserAttachmentServiceImpl extends ServiceImpl<UserAttachmentMapper, UserAttachment> implements IUserAttachmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAttachmentServiceImpl.class);
	
	@Autowired
	private UserAttachmentMapper userAttachmentDao;
	
}
