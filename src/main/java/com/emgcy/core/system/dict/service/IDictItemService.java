package com.emgcy.core.system.dict.service;

import java.util.List;
import com.baomidou.mybatisplus.service.IService;
import com.emgcy.core.system.dict.bean.DictAllInfo;
import com.emgcy.core.system.dict.entity.DictItem;

/**
 * <p>
 * 字典明细表 服务类
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
public interface IDictItemService extends IService<DictItem> {

	/**
	 * 查询字典所有信息
	 * 
	 * @param dictCode 字典表代码
	 * @return
	 */
	List<DictAllInfo> selectAllInfoByCode(String dictCode);
	
	/**
	 *  查询字典所有信息
	 * @param dictItemKey 详情表key
	 * @return
	 */
	DictAllInfo selectAllInfoByKey(String dictItemKey);

	/**
	 * 查询字典所有信息
	 * 
	 * @return
	 */
	List<DictAllInfo> selectDictAllInfo();
}
