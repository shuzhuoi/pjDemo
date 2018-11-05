package com.shuzhuo.core.common.excel.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shuzhuo.core.common.excel.dto.UserExcelDTO;
import com.shuzhuo.core.common.shiro.ShiroUserUtil;
import com.shuzhuo.core.ehcache.DictUtils;
import com.shuzhuo.core.system.sys.service.IUserService;

@Component
public class UserVerifyHandler implements IExcelVerifyHandler<UserExcelDTO>{
	
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IUserService userService;
	

	@Override
	public ExcelVerifyHanlderResult verifyHandler(UserExcelDTO user) {
		
		//判断是否超级管理员
		if(ShiroUserUtil.getUserType()== Constant.SYS_ADMIN_TYPE){
			return new ExcelVerifyHanlderResult(false, "请用医院管理员的账号导入用户");
		}
		
		String deptName = user.getDeptName();
		Long hospitalId = ShiroUserUtil.getHospitalId();
		
		user.setOrgHospitalId(hospitalId);
		
		//验证科室名称
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", deptName);
		map.put("hospitalId", hospitalId);
		
		List<Long> ids = orgService.selectDeptByName(map);
		
		if (ids==null||ids.size()==0) {
			return new ExcelVerifyHanlderResult(false, "科室名称不正确");
		}
		user.setOrgDeptId(ids.get(0));
		//验证员工号
		Map<String,Object> empMap = new HashMap<String,Object>();
		empMap.put("org_hospital_id", hospitalId);
		empMap.put("employee_id", user.getEmployeeId());
		List<User> empLists = userService.selectByMap(empMap);
		if (empLists!=null&&empLists.size()>0) {
			return new ExcelVerifyHanlderResult(false, "员工号已存在");
		}
		//验证手机号
		Map<String,Object> phoneMap = new HashMap<String,Object>();
		phoneMap.put("phone", user.getPhone());
		List<User> phoneLists = userService.selectByMap(phoneMap);
		if (phoneLists!=null&&phoneLists.size()>0) {
			return new ExcelVerifyHanlderResult(false, "手机号码已存在");
		}
		//验证职称
		String jobTitleKey = user.getJobTitleKey();
		Long jobTitle = DictUtils.getDictItemIdByKey(jobTitleKey);
		if (jobTitle==null) {
			return new ExcelVerifyHanlderResult(false, "职称不正确");
		}
		user.setJobTitle(jobTitle);
		
		return new ExcelVerifyHanlderResult(true);
	}

}
