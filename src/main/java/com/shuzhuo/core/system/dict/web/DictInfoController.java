package com.shuzhuo.core.system.dict.web;


import com.shuzhuo.core.system.dict.service.IDictInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@Api(tags="字典表类")
@Controller
@RequestMapping("/dict/dictInfo")
public class DictInfoController {

	private static final Logger logger = LoggerFactory.getLogger(DictInfoController.class);
	
	@Autowired
	private IDictInfoService dictInfoService;
	
	
	
	
}
