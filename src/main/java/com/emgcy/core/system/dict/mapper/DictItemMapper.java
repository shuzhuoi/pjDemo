package com.emgcy.core.system.dict.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.emgcy.core.system.dict.bean.DictAllInfo;
import com.emgcy.core.system.dict.entity.DictItem;


/**
 * <p>
  * 字典明细表 Mapper 接口
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
public interface DictItemMapper extends BaseMapper<DictItem> {
	
	List<DictAllInfo> selectDictAllInfo(DictAllInfo dictAllInfo);
	
}