package com.shuzhuo.core.ehcache;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.system.dict.service.IDictItemService;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-30
 */
@Component
public class InitCache implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(InitCache.class);

	@Autowired
	private IDictItemService dictItemService;

	@Autowired
	private IOrgService orgService;

	/**
	 * 多线程初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (logger.isInfoEnabled())
			logger.info("init all cache ing ...");

		new Thread(new InitCacheThread(Constant.DICT_CACHE, dictItemService)).start();
		new Thread(new InitCacheThread(Constant.HOSPITAL_CACHE, orgService)).start();

	}

}
