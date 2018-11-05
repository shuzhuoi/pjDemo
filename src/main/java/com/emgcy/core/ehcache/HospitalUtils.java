package com.emgcy.core.ehcache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.emgcy.core.common.constants.Constant;
import com.emgcy.core.common.util.CacheUtils;
import com.emgcy.core.common.util.SpringContextHolder;
import com.emgcy.core.system.sys.entity.Org;
import com.emgcy.core.system.sys.service.IOrgService;
import com.emgcy.core.system.sys.service.impl.OrgServiceImpl;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-04
 *
 */
public class HospitalUtils {

	private static final Logger logger = LoggerFactory.getLogger(HospitalUtils.class);

	private static IOrgService iOrgService = SpringContextHolder.getBean(OrgServiceImpl.class);
	
	/**
	 * 从缓存中获取数据,
	 * 缓存不存在则查询数据库
	 * @param hospitalUuid 
	 * @return
	 */
	public static Org getHospitalCache(String hospitalUuid){
		if(StringUtils.isBlank(hospitalUuid)){
			logger.warn("hospitalUuid is null");
			return null;
		}
		if(logger.isInfoEnabled())
			logger.info("{}:from caching", hospitalUuid);
		Org org = (Org) CacheUtils.get(Constant.HOSPITAL_CACHE, hospitalUuid);
		if(org==null){
			org = new Org();
			org.setType(Constant.ORG_HTYPE);
			org.setUuid(hospitalUuid);
  			Wrapper<Org> wrapper=new EntityWrapper<Org>(org);
  			org = iOrgService.selectOne(wrapper);
			if(org==null){
				logger.warn("result data is empty , key:{}", hospitalUuid);
				return null;
			}
			if(logger.isInfoEnabled())
				logger.info("put ehcache"+Constant.HOSPITAL_CACHE+" :{}", org);
			CacheUtils.put(Constant.HOSPITAL_CACHE, hospitalUuid, org);
		}
		return org;
	}
}
