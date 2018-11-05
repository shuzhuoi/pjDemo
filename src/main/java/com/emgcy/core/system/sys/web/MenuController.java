package com.emgcy.core.system.sys.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.emgcy.core.common.annotation.RepeatUrlData;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.system.base.AbstractController;
import com.emgcy.core.system.base.BasePageDataDTO;
import com.emgcy.core.system.sys.entity.Menu;
import com.emgcy.core.system.sys.service.IMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-28
 */
@Api(tags="菜单管理类")
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private IMenuService menuService;
	
	@ApiOperation(value = "获取用户的菜单列表",response=Menu.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getMenuList")
	public BaseMsg getMenuList() {
		
		//登录成功后的菜单
		List<Menu> lists = menuService.getUserMenuList(getUserId(), getUserType());
		if (lists==null||lists.size()==0) {
			return BaseMsg.errorMsg("该用户暂无任何权限，请联系管理员分配权限");
		}
		return BaseMsg.successData(lists);
	}
	
	@ApiOperation(value = "获取用户的菜单列表(结果不嵌套)",response=Menu.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getMenuList2")
	public BaseMsg getMenuList2() {
		
		//登录成功后的菜单
		List<Menu> lists = menuService.getUserMenuList2(getUserId(), getUserType());
		if (lists==null||lists.size()==0) {
			return BaseMsg.errorMsg("该用户暂无任何权限，请联系管理员分配权限");
		}
		return BaseMsg.successData(lists);
	}
	
	
}
