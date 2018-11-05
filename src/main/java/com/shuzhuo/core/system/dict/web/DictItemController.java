package com.shuzhuo.core.system.dict.web;


import java.util.List;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.system.dict.bean.DictAllInfo;
import com.shuzhuo.core.system.dict.service.IDictItemService;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 字典明细表 前端控制器
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@Api(tags="字典明细表类")
@Controller
@Validated
@RequestMapping("/dict/dictItem")
public class DictItemController {

	private static final Logger logger = LoggerFactory.getLogger(DictItemController.class);
	
	@Autowired
	private IDictItemService dictItemService;
	
	@ApiOperation(value = "根据字典表代码查询信息")
	@GetMapping("/getAllInfoByCode")
	@ResponseBody
	public BaseMsg getAllInfoByCode(@RequestParam(name = "dictCode") @NotBlank String dictCode){
		if (logger.isInfoEnabled())
			logger.info("根据字典表代码查询信息", dictCode);
		List<DictAllInfo> selectAllInfoByCode = dictItemService.selectAllInfoByCode(dictCode);
		return BaseMsg.successData(selectAllInfoByCode);
	}
	
}
