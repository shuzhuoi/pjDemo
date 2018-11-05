package com.shuzhuo.core.system.sys.service.impl;

import com.shuzhuo.core.system.sys.entity.Flow;
import com.shuzhuo.core.system.sys.mapper.FlowMapper;
import com.shuzhuo.core.system.sys.service.IFlowService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 审核流程表 服务实现类
 * </p>
 *
 * @author ouyuming
 * @since 2017-12-08
 */
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements IFlowService {
	
	private static final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);
	
	@Autowired
	private FlowMapper flowDao;


}
