package com.shuzhuo.core.system.dict.service.impl;


import com.shuzhuo.core.system.dict.entity.DictInfo;
import com.shuzhuo.core.system.dict.mapper.DictInfoMapper;
import com.shuzhuo.core.system.dict.service.IDictInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@Service
public class DictInfoServiceImpl extends ServiceImpl<DictInfoMapper, DictInfo> implements IDictInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(DictInfoServiceImpl.class);
	
	@Autowired
	private DictInfoMapper dictInfoDao;


	
}
