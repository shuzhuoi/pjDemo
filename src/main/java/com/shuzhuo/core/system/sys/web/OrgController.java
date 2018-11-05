package com.shuzhuo.core.system.sys.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.system.base.AbstractController;
import com.shuzhuo.core.system.base.BasePageDataDTO;
import com.shuzhuo.core.system.dict.entity.DictItem;
import com.shuzhuo.core.system.dict.service.IDictItemService;
import com.shuzhuo.core.system.sys.dto.SysDeptDTO;
import com.shuzhuo.core.system.sys.entity.Org;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.service.IOrgService;
import com.shuzhuo.core.system.sys.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
@Api(tags="医院科室管理")
@RestController
@RequestMapping("/sys/org")
public class OrgController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(OrgController.class);
	
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDictItemService dictItemService;
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取医院列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isPage", value = "是否分页", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/hospitalList")
	public BasePageDataDTO<Org> hospitalList(HttpServletRequest request) {
		
		//分页
		Page page = buildPage(request);
		Wrapper<Org> wrapper = new EntityWrapper<Org>(new Org());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);
		
		wrapper.eq("type", Constant.ORG_HTYPE);
		
	    Page<Org> selectPage = orgService.queryList(page, wrapper);
		return new BasePageDataDTO<Org>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id获取医院信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getHospitalInfo")
	public BaseMsg getHospitalInfo(@RequestParam(required=false) @ApiParam(name = "id", value = "医院标识ID") Long id) {
		if (getUserType()!=Constant.SYS_ADMIN_TYPE) {
			id = getHospitalId();
		}
		
		Org entity= orgService.selectById(id);
		if (entity==null) {
			return BaseMsg.errorMsg("医院不存在");
		}
		if(entity.getLevel()!=null){
			DictItem item = dictItemService.selectById(entity.getLevel());
			if(item!=null){
				entity.setLevelName(item.getDictItemValue());
			}
		}
		if(entity.getHospitalType()!=null){
			DictItem item = dictItemService.selectById(entity.getHospitalType());
			if(item!=null){
				entity.setHospitalTypeName(item.getDictItemValue());
			}
		}
		User user = new User();
		user.setOrgHospitalId(id);
		user.setUserType(2);
		Wrapper<User> wrapper = new EntityWrapper<User>(user);
		User bean = userService.selectOne(wrapper);
		if (bean!=null) {
			entity.setAccount(bean.getEmployeeId());
		}
		
		return BaseMsg.successData(entity);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id删除医院信息")
	@GetMapping("/delHospital")
	public BaseMsg delHospital(@RequestParam @ApiParam(name = "id", value = "医院id", required = true) long id) {
		if (getUserType()!=Constant.SYS_ADMIN_TYPE) {
			return BaseMsg.errorMsg("非系统管理员不能删除医院");
		}
		
		if(orgService.deleteById(id)){
			return BaseMsg.successMsg("删除成功");
		}
		return BaseMsg.errorMsg("删除失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@PostMapping("/addOrUpdate")
	@ApiOperation(value = "新增或修改医院信息")
	public BaseMsg addOrUpdate(@Validated Org entity) {
		if (getUserType()!=Constant.SYS_ADMIN_TYPE) {
			return BaseMsg.errorMsg("非系统管理员不能新增或编辑医院");
		}
		if(entity.getId()==null){//新增医院
			if (StringUtils.isBlank(entity.getAccount())) {
				return BaseMsg.errorMsg("管理员账号不能为空");
			}
			if (orgService.insertHospital(entity)) {
				return BaseMsg.successMsg("新增成功");
			}else{
				return BaseMsg.errorMsg("新增失败");
			}
		}else{//修改医院信息
			if(orgService.updateHospital(entity)){
				return BaseMsg.successMsg("修改成功");
			}else {
				return BaseMsg.errorMsg("修改失败");
			}
		}
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取科室列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "isPage", value = "是否分页", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/deptList")
	public BasePageDataDTO<SysDeptDTO> deptList(HttpServletRequest request,
                                                @RequestParam(required=false) @ApiParam(name = "name", value = "科室名称") String name,
                                                @RequestParam(required=false) @ApiParam(name = "hospitalId", value = "医院ID") Long hospitalId) {
		
		//分页
		Page page = buildPage(request);
		//科室名称或医院ID查询
		SysDeptDTO dept = new SysDeptDTO();
		if (StringUtils.isNotBlank(name)) {
			dept.setName(name);
		}
		
		//不是超级管理员则只查询本院数据
		if(getUserType()!=Constant.SYS_ADMIN_TYPE){
			dept.setHospitalId(getHospitalId());
		}else if(hospitalId!=null) {
			dept.setHospitalId(hospitalId);
		}
		
		Wrapper<SysDeptDTO> wrapper = new EntityWrapper<SysDeptDTO>(dept);
		//排序
		buildOrder(request,wrapper);
	    Page<SysDeptDTO> selectPage = orgService.selectDept(page, wrapper);
		return new BasePageDataDTO<SysDeptDTO>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取当前用户的科室列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/currentUserDeptList")
	public BaseMsg currentUserDeptList() {
		
		SysDeptDTO dept = new SysDeptDTO();
		dept.setHospitalId(getHospitalId());
		Wrapper<SysDeptDTO> wrapper = new EntityWrapper<SysDeptDTO>(dept);
		
		List<Org> lists = orgService.selectCurrentUserDept(wrapper);
		
		return BaseMsg.successData(lists);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取上级科室列表")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/parentDeptList")
	public BaseMsg parentDeptList(@RequestParam(required=false) @ApiParam(name = "hospitalId", value = "医院ID") Long hospitalId) {
		
		
		//不是超级管理员则只查询本院数据
		if(getUserType()!=Constant.SYS_ADMIN_TYPE){
			hospitalId=getHospitalId();
		}
		if (hospitalId==null) {
			return BaseMsg.errorMsg("医院ID不能为空");
		}
		//查询一级科室
		Org dept = new Org();
		dept.setParentId(hospitalId);
		dept.setType(2);
		
		Wrapper<Org> wrapper = new EntityWrapper<Org>(dept);
		
		List<Org> lists = orgService.selectList(wrapper);
		return BaseMsg.successData(lists);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id获取科室信息",response=SysDeptDTO.class)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getDeptInfo")
	public BaseMsg getDeptInfo(@RequestParam @ApiParam(name = "id", value = "科室标识ID", required = true) long id) {
		Org entity= orgService.selectById(id);
		if (entity==null) {
			return BaseMsg.errorMsg("科室不存在");
		}
		SysDeptDTO deptDTO = new SysDeptDTO();
		deptDTO.setId(entity.getId());
		deptDTO.setCode(entity.getCode());
		deptDTO.setName(entity.getName());
		//上级医院或科室
		Org parent = orgService.selectById(entity.getParentId());
		
		if(entity.getType()==2){
			deptDTO.setHospitalId(entity.getParentId());
			if (parent!=null) {
				deptDTO.setHospitalName(parent.getName());	
			}
		} else if (entity.getType()==3) {
			deptDTO.setParentId(entity.getParentId());
			if (parent!=null) {
				deptDTO.setParentName(parent.getName());				
				//上级医院
				Org hospital = orgService.selectById(parent.getParentId());
				deptDTO.setHospitalId(parent.getParentId());
				if (hospital!=null) {
					deptDTO.setHospitalName(hospital.getName());
				}
			}
			
		} else {
			return BaseMsg.errorMsg("科室ID不正确");
		}
		
		return BaseMsg.successData(deptDTO);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id删除科室信息")
	@GetMapping("/delDept")
	public BaseMsg delDept(@RequestParam @ApiParam(name = "id", value = "科室id", required = true) long id) {
		Org org = orgService.selectById(id);
		if (org==null) {
			return BaseMsg.errorMsg("科室不存在");
		}
		//删除一级科室
		if (org.getType()==2) {
			if (!(getUserType()==Constant.SYS_ADMIN_TYPE||getHospitalId().equals(org.getParentId()))) {
				return BaseMsg.errorMsg("非系统管理员不能删除其他医院科室");
			}
			Org bean = new Org();
			bean.setParentId(id);
			Wrapper<Org> wrapper = new EntityWrapper<Org>(bean);
			List<Org> lists = orgService.selectList(wrapper);
			if (lists!=null&&lists.size()>0) {
				Long[] ids = new Long[lists.size()+1];
				ids[0] = id;
				for (int i = 1; i < ids.length; i++) {
					ids[i] = lists.get(i-1).getId();
				}
				if (orgService.delDeptBatch(ids)) {
					return BaseMsg.successMsg("删除成功");
				}
			} else {
				if (orgService.deleteById(id)) {
					return BaseMsg.successMsg("删除成功");
				}
			}
		} else if (org.getType()==3) {//删除二级科室
			Org secOrg = orgService.selectById(org.getParentId());
			if (secOrg!=null&&getUserType()!=Constant.SYS_ADMIN_TYPE&&!getHospitalId().equals(secOrg.getParentId())) {
				return BaseMsg.errorMsg("非系统管理员不能删除其他医院科室");
			}
			if (orgService.deleteById(id)) {
				return BaseMsg.successMsg("删除成功");
			}
		}
		return BaseMsg.errorMsg("删除失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@PostMapping("/addOrUpdateDept")
	@ApiOperation(value = "新增或修改科室信息")
	public BaseMsg addOrUpdateDept(@Validated SysDeptDTO entity) {
		//填充医院
		if (getUserType()!=Constant.SYS_ADMIN_TYPE) {
			entity.setHospitalId(getHospitalId());
		}
		if(entity.getId()==null){//新增科室
			if (entity.getHospitalId()==null) {
				return BaseMsg.errorMsg("医院ID不能为空");
			}
			if (orgService.insertDept(entity)) {
				return BaseMsg.successMsg("新增成功");
			}
		} else {
			if (orgService.updateDept(entity)) {
				return BaseMsg.successMsg("修改成功");
			}
		}
		return BaseMsg.errorMsg("操作失败");
	}
	
}
