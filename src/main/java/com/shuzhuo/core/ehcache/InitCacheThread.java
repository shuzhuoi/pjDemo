package com.shuzhuo.core.ehcache;

import java.util.List;

import com.shuzhuo.core.system.dict.bean.DictAllInfo;
import com.shuzhuo.core.system.sys.entity.Org;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.util.CacheUtils;
import com.shuzhuo.core.system.dict.service.IDictItemService;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-30
 *
 */
public class InitCacheThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(InitCacheThread.class);

	private String cacheKey;

	private Object obj;

	public InitCacheThread(String cacheKey, Object obj) {
		this.cacheKey = cacheKey;
		this.obj = obj;
	}

	@Override
	public void run() {
		switch (cacheKey) {
		case Constant.DICT_CACHE:
			try {
				if (logger.isInfoEnabled())
					logger.info(Thread.currentThread().getName() + " init dict cache ing...!");
				initDictCache();
				if (logger.isInfoEnabled())
					logger.info(Thread.currentThread().getName() + " init dict cache success!");
			} catch (Exception e) {
				logger.error(Thread.currentThread().getName() + " init dict exception!", e);
			}
			break;
		case Constant.HOSPITAL_CACHE:
			try {
				if (logger.isInfoEnabled())
					logger.info(Thread.currentThread().getName() + " init hospital cache ing...!");
				initHospitalCache();
				if (logger.isInfoEnabled())
					logger.info(Thread.currentThread().getName() + " init hospital cache success!");
			} catch (Exception e) {
				logger.error(Thread.currentThread().getName() + " init hospital exception!", e);
			}
			break;

		default:
			break;
		}

	}

	private void initDictCache() throws Exception {
		if (obj instanceof IDictItemService) {
			List<DictAllInfo> selectDictAllInfo = ((IDictItemService) obj).selectDictAllInfo();
			if (!selectDictAllInfo.isEmpty()) {
				DictAllInfo dictAllInfo = null;
				for (int i = 0; i < selectDictAllInfo.size(); i++) {
					dictAllInfo = selectDictAllInfo.get(i);
					String dictItemKey = dictAllInfo.getDictItemKey();
					if (StringUtils.isNotBlank(dictItemKey)) {
						CacheUtils.put(Constant.DICT_CACHE, dictItemKey, dictAllInfo);
					} else {
						if (logger.isWarnEnabled())
							logger.warn(Thread.currentThread().getName() + " dict key is null!", dictAllInfo);
					}

				}
			}
		}

	}

	private void initHospitalCache() throws Exception {
		if (obj instanceof IOrgService) {
			Org org = new Org();
			org.setType(Constant.ORG_HTYPE);
			Wrapper<Org> Wrapper = new EntityWrapper<Org>(org);
			List<Org> selectList = ((IOrgService) obj).selectList(Wrapper);
			if (!selectList.isEmpty()) {
				for (int i = 0; i < selectList.size(); i++) {
					org = selectList.get(i);
					String hospitalUuid = org.getUuid();
					if (StringUtils.isNotBlank(hospitalUuid)) {
						CacheUtils.put(Constant.HOSPITAL_CACHE, hospitalUuid, org);
					} else {
						if (logger.isWarnEnabled())
							logger.warn(Thread.currentThread().getName() + " hospitalUuid is null!", org);
					}
				}
			}
		}

	}

}
