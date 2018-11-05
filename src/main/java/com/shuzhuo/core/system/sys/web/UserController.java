package com.shuzhuo.core.system.sys.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.system.attachment.service.IAttachmentService;
import com.shuzhuo.core.system.sys.entity.Org;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shuzhuo.core.common.util.TokenGenerator;
import com.shuzhuo.core.ehcache.DictUtils;
import com.shuzhuo.core.system.base.AbstractController;
import com.shuzhuo.core.system.base.BasePageDataDTO;
import com.shuzhuo.core.system.sys.entity.Role;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.entity.UserAttachment;
import com.shuzhuo.core.system.sys.entity.UserTrain;
import com.shuzhuo.core.system.sys.service.IOrgService;
import com.shuzhuo.core.system.sys.service.IRoleService;
import com.shuzhuo.core.system.sys.service.IUserAttachmentService;
import com.shuzhuo.core.system.sys.service.IUserService;
import com.shuzhuo.core.system.sys.service.IUserTrainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-24
 */
@Api(tags="系统用户管理")
@RestController
@RequestMapping("/sys/user")
public class UserController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private IUserAttachmentService userAttachmentSer;
	@Autowired
	private IUserTrainService userTrainSer;
	
//	@RequiresPermissions("sys:user:list")
	@ApiOperation(value = "获取用户列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "limit", value = "页数量", dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/list")
	public BasePageDataDTO<User> list(HttpServletRequest request,
			@RequestParam @ApiParam(name = "flag", value = "用户类型标识(1急救人员,2后台用户)", required = true) int flag) {
		
		//分页
		Page page = buildPage(request);
		Wrapper<User> wrapper = new EntityWrapper<User>(new User());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);
		//本院的用户
		wrapper.eq("org_hospital_id", getHospitalId());
		if (flag==1) {
			wrapper.eq("user_type", 3);
		} else  if (flag==2) {
			wrapper.eq("user_type", 4);
		} else {
			BasePageDataDTO<User> result = new BasePageDataDTO<User>();
			result.setCode(0);
			result.setMsg("用户类型标识不正确");
			return result;
		}
	    
	    Page<User> selectPage = userService.queryList(page, wrapper);
		return new BasePageDataDTO<User>(selectPage.getRecords(), selectPage.getTotal());
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id获取用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getInfo")
	public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "用户id", required = true) long id) {
		User entity= userService.selectById(id);
		if (entity.getOrgHospitalId()!=null) {
			Org org = orgService.selectById(entity.getOrgHospitalId());
			if (org!=null) {
				entity.setHospitalName(org.getName());
			}
		}
		if (entity.getOrgDeptId()!=null) {
			//一级科室直接显示科室名称，二级科室显示一级科室名称+"-"+二级科室名称
			Org org = orgService.selectById(entity.getOrgDeptId());
			if (org!=null&&org.getType()==2) {//一级科室
				entity.setDeptName(org.getName());
			} else if (org!=null&&org.getType()==3) {//二级级科室
				Org firOrg = orgService.selectById(org.getParentId());
				if (firOrg!=null) {
					entity.setDeptName(firOrg.getName()+"-"+org.getName());
				}
			}
		}
		if (entity.getJobTitle()!=null) {
			entity.setJobTitleName(DictUtils.getDictItemValueById(entity.getJobTitle()));
		}
		if (StringUtils.isNotBlank(entity.getPhoto())) {
			String photo = attachmentService.getPathByGroupUuid(entity.getPhoto());
			entity.setPhoto(photo);
		}
		
		//医师资格证和医师执业证
		UserAttachment attachment = new UserAttachment();
		attachment.setUserId(id);
		attachment.setAttachType(2);
		
		Wrapper<UserAttachment> wrapper1 = new EntityWrapper<UserAttachment>(attachment);
		UserAttachment bean1 = userAttachmentSer.selectOne(wrapper1);
		if (bean1!=null&&StringUtils.isNotBlank(bean1.getAttachMd5())) {
			String path = attachmentService.getPathByGroupUuid(bean1.getAttachMd5());
			entity.setQualification(path);
		}
		
		attachment.setAttachType(3);
		Wrapper<UserAttachment> wrapper2 = new EntityWrapper<UserAttachment>(attachment);
		UserAttachment bean2 = userAttachmentSer.selectOne(wrapper2);
		if (bean2!=null&&StringUtils.isNotBlank(bean2.getAttachMd5())) {
			String path = attachmentService.getPathByGroupUuid(bean2.getAttachMd5());
			entity.setPractice(path);
		}
		
		//培训教育
		List<UserTrain> trainLists = userTrainSer.selectUserTrain(id);
		if (trainLists!=null&&trainLists.size()>0) {
			entity.setTrainList(trainLists);
		}
		
		return BaseMsg.successData(entity);
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "根据id删除用户")
	@GetMapping("/deleteUser")
	public BaseMsg deleteUser(@RequestParam @ApiParam(name = "id", value = "用户id", required = true) long id) {
		User user = userService.selectById(id);
		if (user==null) {
			return BaseMsg.errorMsg("用户不存在");
		}
		if (user.getUserType()==1) {
			return BaseMsg.errorMsg("系统管理员不能删除");
		}
		if (user.getUserType()==2) {
			return BaseMsg.errorMsg("医院管理员不能删除");
		}
		if (!getHospitalId().equals(user.getOrgHospitalId())) {
			return BaseMsg.errorMsg("非本院用户不能删除");
		}
		if(userService.deleteById(id)){
			return BaseMsg.successMsg("删除成功");
		}
		return BaseMsg.errorMsg("删除失败");
	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "新增或修改用户信息")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/addOrUpdate")
	public BaseMsg addOrUpdate(@RequestBody @Validated User entity,
			@RequestParam @ApiParam(name = "flag", value = "用户类型标识(1急救人员,2后台用户)", required = true) int flag) {
		User user = new User();
		//新增用户
		if (entity.getId()==null) {
			user.setOrgHospitalId(getHospitalId());
			user.setEmployeeId(entity.getEmployeeId());
			user.setPhone(entity.getPhone());
			if (userService.isUserExist(user)){
				return BaseMsg.errorMsg("员工号或手机号已存在");
			}
			//用户类型
			Integer userType;
			if (flag==1) {
				userType = 3;
			} else if (flag==2) {
				userType = 4;
			} else {
				return BaseMsg.errorMsg("用户类型标识不正确");
			}
			if (userService.insertUser(entity,userType)) {
				return BaseMsg.successMsg("新增成功");
			}
		} else {//修改用户
			User bean = userService.selectById(entity.getId());
			//判断员工号或手机号是否存在
			if (!bean.getEmployeeId().equals(entity.getEmployeeId())) {
				user.setOrgHospitalId(bean.getOrgHospitalId());
				user.setEmployeeId(entity.getEmployeeId());
			}
			if (!bean.getPhone().equals(entity.getPhone())) {
				user.setPhone(entity.getPhone());
			}
			if (userService.isUserExist(user)){
				return BaseMsg.errorMsg("员工号或手机号已存在");
			}
			if (userService.updateUser(entity)) {
				return BaseMsg.successMsg("修改成功");
			}
			
		}
		
		return BaseMsg.errorMsg("操作失败");

	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "修改密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "password", value = "原密码", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/updatePwd")
	public BaseMsg updatePwd(String password, String newPassword) {
		
		if (StringUtils.isBlank(newPassword)||StringUtils.isBlank(password)) {
			return BaseMsg.errorMsg("密码不能为空");
		}
		
		//原密码sha256加密
		password = TokenGenerator.entryptPassword(password, getUser().getSalt());
		//新密码sha256加密
		newPassword = TokenGenerator.entryptPassword(newPassword, getUser().getSalt());
		
		if (!password.equals(getUser().getPassword())) {
			return BaseMsg.errorMsg("原密码不正确");
		}
		if (userService.updatePassword(getUserId(), newPassword)) {
			return BaseMsg.successMsg("修改密码成功");
		}
		
		return BaseMsg.errorMsg("修改密码失败");

	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "修改电子签名密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "password", value = "原密码", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/updateSignPwd")
	public BaseMsg updateSignPwd(String password, String newPassword) {
		
		if (StringUtils.isBlank(newPassword)||StringUtils.isBlank(password)) {
			return BaseMsg.errorMsg("密码不能为空");
		}
		
		//原密码sha256加密
		password = TokenGenerator.entryptPassword(password, getUser().getSalt());
		//新密码sha256加密
		newPassword = TokenGenerator.entryptPassword(newPassword, getUser().getSalt());
		
		if (!password.equals(getUser().getSignPassword())) {
			return BaseMsg.errorMsg("原密码不正确");
		}
		if (userService.updateSignPassword(getUserId(), newPassword)) {
			return BaseMsg.successMsg("修改密码成功");
		}
		
		return BaseMsg.errorMsg("修改密码失败");

	}
	
//	@RequiresPermissions(请填入权限编码)
	@ApiOperation(value = "获取用户的角色列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderField", value = "排序字段", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "order", value = "升降序", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "search", value = "查询Json", dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/getRoleList")
	public BaseMsg getRoleList(HttpServletRequest request,
			@RequestParam @ApiParam(name = "userId", value = "用户id", required = true) long userId) {
		
		Wrapper<Role> wrapper = new EntityWrapper<Role>(new Role());
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);
		
		List<Role> lists = roleService.selectRoleUserList(userId, wrapper);
		return BaseMsg.successData(lists);
	}
	
	@ApiOperation(value = "导入用户签名(单个)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importSingleUserSign")
	public BaseMsg importSingleUserSign(
			@ApiParam(name = "file", value = "用户签名图片") @RequestParam(value = "file") MultipartFile file,
			@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam Long userId)throws Exception{
		if (file == null || file.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		return userService.importUserSign(userId, file);
	}
	
	@ApiOperation(value = "导入用户签名(批量)")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/importBatchUserSign")
	public BaseMsg importBatchUserSign(
			@ApiParam(name = "file", value = "用户签名压缩包") @RequestParam(value = "file") MultipartFile file)throws Exception{
		if (file == null || file.isEmpty()){
			return BaseMsg.errorMsg("文件为空");
		}
		return userService.importUserSignBatch(file);
	}
	
}
