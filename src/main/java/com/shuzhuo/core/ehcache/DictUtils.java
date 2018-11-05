package com.shuzhuo.core.ehcache;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.util.CacheUtils;
import com.shuzhuo.core.common.util.SpringContextHolder;
import com.shuzhuo.core.system.dict.bean.DictAllInfo;
import com.shuzhuo.core.system.dict.entity.DictItem;
import com.shuzhuo.core.system.dict.service.IDictItemService;
import com.shuzhuo.core.system.dict.service.impl.DictItemServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-04
 */
public class DictUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DictUtils.class);

	private static IDictItemService dictItemService = SpringContextHolder.getBean(DictItemServiceImpl.class);
	
	/**
	 * 从缓存中获取数据,
	 * 缓存不存在则查询数据库
	 * @param dictItemKey 字典详情表代码
	 * @return
	 */
	public static DictAllInfo getDictCache(String dictItemKey){
		if(StringUtils.isBlank(dictItemKey)){
			logger.warn("dictItemKey is null");
			return null;
		}
		if(logger.isInfoEnabled())
			logger.info("{}:from caching", dictItemKey);
		DictAllInfo dictAllInfo = (DictAllInfo) CacheUtils.get(Constant.DICT_CACHE, dictItemKey);
		if(dictAllInfo==null){
			dictAllInfo = dictItemService.selectAllInfoByKey(dictItemKey);
			if(dictAllInfo==null){
				logger.warn("result data is empty , key:{}", dictItemKey);
				return null;
			}
			if(logger.isInfoEnabled())
				logger.info("put ehcache"+Constant.DICT_CACHE+" :{}", dictAllInfo);
			CacheUtils.put(Constant.DICT_CACHE, dictItemKey, dictAllInfo);
		}
		return dictAllInfo;
	}
	
	/**
	 * 根据字典项代码获取字典项id
	 * @param dictItemKey
	 * @return 
	 */
	public static Long getDictItemIdByKey(String dictItemKey){
		DictAllInfo info = getDictCache(dictItemKey);
		
		if( info!=null ){
			return info.getId();
		} else {
			return null;
		}
	}
	
	/**
	 * 根据字典项id获取字典项代码
	 */
	public static String getDictItemKeyById(Long id){
		String key="";
		DictItem item = dictItemService.selectById(id);
		if (item!=null) {
			key = item.getDictItemKey();
		}
		return key;
	}
	
	/**
	 * 根据字典项id获取字典项值
	 */
	public static String getDictItemValueById(Long id){
		String value="";
		DictItem  item = dictItemService.selectById(id);
		if (item!=null) {
			value = item.getDictItemValue();
		}
		return value;
	}
}
