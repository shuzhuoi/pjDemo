package com.shuzhuo.core.system.dict.service.impl;

import java.util.List;

import com.shuzhuo.core.system.dict.bean.DictAllInfo;
import com.shuzhuo.core.system.dict.entity.DictItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.system.dict.mapper.DictItemMapper;
import com.shuzhuo.core.system.dict.service.IDictItemService;

/**
 * <p>
 * 字典明细表 服务实现类
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements IDictItemService {
	
	private static final Logger logger = LoggerFactory.getLogger(DictItemServiceImpl.class);
	
	@Autowired
	private DictItemMapper dictItemDao;
	
	@Override
	public DictAllInfo selectAllInfoByKey(String dictItemKey){
		return getDictAllInfo(null, dictItemKey).get(0);
	}

	@Override
	public List<DictAllInfo> selectDictAllInfo() {
		return getDictAllInfo(null, null);
	}
	

	@Override
	public List<DictAllInfo> selectAllInfoByCode(String dictCode) {
		return getDictAllInfo(dictCode, null);
	}
	
	/**
	 * 查询字典表所有信息
	 * @param dictCode 字典代码
	 * @param dictItemKey 详情表 key
	 * @return
	 */
	private List<DictAllInfo> getDictAllInfo(String dictCode, String dictItemKey){
		DictAllInfo dictAllInfo = new DictAllInfo();
		dictAllInfo.setDictCode(dictCode);
		dictAllInfo.setDictItemKey(dictItemKey);
		dictAllInfo.setIsEnable(Constant.DICT_ENABLED);
		if(logger.isInfoEnabled())
			logger.info("查询字典详情信息:{}", dictAllInfo);
		return dictItemDao.selectDictAllInfo(dictAllInfo);
	}


	
}
