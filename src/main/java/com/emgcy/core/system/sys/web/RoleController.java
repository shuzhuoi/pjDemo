package com.emgcy.core.system.sys.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.common.constants.Constant;
import com.emgcy.core.system.base.AbstractController;
import com.emgcy.core.system.base.BasePageDataDTO;
import com.emgcy.core.system.sys.dto.RoleUserParaDTO;
import com.emgcy.core.system.sys.entity.Menu;
import com.emgcy.core.system.sys.entity.Role;
import com.emgcy.core.system.sys.entity.User;
import com.emgcy.core.system.sys.service.IMenuService;
import com.emgcy.core.system.sys.service.IRoleService;
import com.emgcy.core.system.sys.service.IUserRoleService;
import com.emgcy.core.system.sys.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-29
 */
@Api(tags="角色表类")
@RestController
@RequestMapping("/sys/role")
public class RoleController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserRoleService userRoleService;
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取角色列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isPage", value = "是否分页", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/list")
	public BasePageDataDTO<Role> list(HttpServletRequest request) {
		//分页
		Page page = buildPage(request);
		Wrapper<Role> wrapper = new EntityWrapper<Role>(new Role());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);

		//不是超级管理员则只查询本院数据
		if(getUserType()!=Constant.SYS_ADMIN_TYPE){
			wrapper.eq("org_hospital_id", getHospitalId());
		}

		Page<Role> selectPage = roleService.selectPage(page, wrapper);
		return new BasePageDataDTO<Role>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id获取角色信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getInfo")
	public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "角色id", required = true) long id) {
		Role entity= roleService.selectById(id);
		//菜单列表
		List<Menu> lists = menuService.getRoleMenuList(id);
		entity.setMenuList(lists);
		
		return BaseMsg.successData(entity);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取前台或后台菜单列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getMenuList")
	public BaseMsg getMenuList(@RequestParam @ApiParam(name = "flag", value = "前后台角色标识", required = true) int flag) {
		
		//菜单列表
		List<Menu> lists = menuService.getMenuList(flag);
		return BaseMsg.successData(lists);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id删除角色")
	@GetMapping("/del")
	public BaseMsg del(@RequestParam @ApiParam(name = "id", value = "角色id", required = true) long id) {
		Role role = roleService.selectById(id);
		if (role==null) {
			return BaseMsg.errorMsg("角色不存在");
		}
		if (!getHospitalId().equals(role.getOrgHospitalId())) {
			return BaseMsg.errorMsg("不能删除其他医院角色");
		}
		if(roleService.deleteById(id)){
			return BaseMsg.successMsg("删除成功");
		}
		return BaseMsg.errorMsg("删除失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id启用或禁用角色")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "角色id", dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "status", value = "状态(1启用，2停用)", dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/enableOrDisable")
	public BaseMsg enableOrDisable(@RequestParam Long id ,@RequestParam Long status) {
		if (id == null){
			return BaseMsg.errorMsg("角色ID不能为空");
		}
		if (status == null){
			return BaseMsg.errorMsg("状态不能为空");
		}
		Role role = roleService.selectById(id);
		if (role==null) {
			return BaseMsg.errorMsg("角色不存在");
		}
		if (!getHospitalId().equals(role.getOrgHospitalId())) {
			return BaseMsg.errorMsg("不能禁用或启用其他医院角色");
		}
		if (status==1) {
			role.setStatus(1);
		} else if (status==2) {
			role.setStatus(0);
		} else {
			return BaseMsg.errorMsg("状态不正确");
		}
		if(roleService.updateById(role)){
			return BaseMsg.successMsg("操作成功");
		}
		return BaseMsg.errorMsg("操作失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取角色已绑定的用户列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isPage", value = "是否分页", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/bindingUserList")
	public BasePageDataDTO<User> bindingUserList(HttpServletRequest request,
			@RequestParam @ApiParam(name = "roleId", value = "角色id", required = true) Long roleId) {
		
		//分页
		Page page = buildPage(request);
		Wrapper<User> wrapper = new EntityWrapper<User>(new User());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);

		Page<User> selectPage = userService.selectRoleUserList(roleId, page, wrapper);
		return new BasePageDataDTO<User>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取角色未绑定的用户列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isPage", value = "是否分页", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/unbindingUserList")
	public BasePageDataDTO<User> unbindingUserList(HttpServletRequest request,
			@RequestParam @ApiParam(name = "roleId", value = "角色id", required = true) Long roleId) {
		
		//分页
		Page page = buildPage(request);
		Wrapper<User> wrapper = new EntityWrapper<User>(new User());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);
		
		//本院的用户
		wrapper.eq("org_hospital_id", getHospitalId());

		Page<User> selectPage = userService.selectUnbindingRoleUserList(roleId, page, wrapper);
		return new BasePageDataDTO<User>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "绑定用户")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/bindingUser")
	public BaseMsg bindingUser(@RequestBody RoleUserParaDTO entity) {
		if (entity.getRoleId() == null){
			return BaseMsg.errorMsg("角色ID不能为空");
		}
		//绑定角色对应的用户
		roleService.insertUserRole(entity.getUserIdList(), entity.getRoleId());
		
		return BaseMsg.successMsg("操作成功");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "删除绑定用户")
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/delBindingUser")
	public BaseMsg delBindingUser(@RequestParam Long roleId ,@RequestParam Long userId) {
		if (roleId == null){
			return BaseMsg.errorMsg("角色ID不能为空");
		}
		if (userId == null){
			return BaseMsg.errorMsg("用户ID不能为空");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", userId);
		map.put("role_id", roleId);
		
		if (userRoleService.deleteByMap(map)) {
			return BaseMsg.successMsg("删除成功");			
		}
		return BaseMsg.errorMsg("删除失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@PostMapping("/addOrUpdate")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ApiOperation(value = "新增或修改角色信息")
	public BaseMsg addOrUpdate(@RequestBody @Validated Role entity) {
		entity.setOrgHospitalId(getHospitalId());
		
		if(entity.getId()==null){//新增角色
			if (roleService.insertRole(entity)) {
				return BaseMsg.successMsg("新增成功");
			}
		} else {//修改角色
			if (roleService.updateRole(entity)) {
				return BaseMsg.successMsg("修改成功");
			}
		}
		
		return BaseMsg.errorMsg("操作失败");
	}
	
}
