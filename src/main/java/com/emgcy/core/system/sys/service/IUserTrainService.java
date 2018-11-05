package com.emgcy.core.system.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.emgcy.core.system.sys.entity.UserTrain;

/**
 * <p>
 * 用户培训表 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2018-01-10
 */
public interface IUserTrainService extends IService<UserTrain> {
	
	/**
	 * 查询用户的培训教育
	 */
	List<UserTrain> selectUserTrain(Long userId);
	
}
