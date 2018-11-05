package com.emgcy.core.common.excel.handler;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.emgcy.core.common.excel.dto.ScheduleExcelDTO;

import com.emgcy.core.system.sys.service.IUserService;

@Component
public class ScheduleVerifyHandler implements IExcelVerifyHandler<ScheduleExcelDTO>{
	
	@Autowired
	private IUserService userService;
/*	@Autowired
	private IScheduleSetService scheduleSetService;*/
	
	@Override
	public ExcelVerifyHanlderResult verifyHandler(ScheduleExcelDTO obj) {
		/*String employeeId = obj.getEmployeeId();
		String scheName = obj.getScheName();
		//判断员工号是否正确
		User user = new User();
		user.setOrgHospitalId(ShiroUserUtil.getHospitalId());
		user.setEmployeeId(employeeId);
		user.setUserType(Constant.FIRST_AID_TYPE);
		EntityWrapper<User> wrapper = new EntityWrapper<>(user);
		User bean = userService.selectOne(wrapper);
		if (bean==null) {
			return new ExcelVerifyHanlderResult(false, "员工号不正确或员工号对应的用户不是急救人员");
		}
		obj.setUserId(bean.getId());
		
		//判断班次是否正确
		ScheduleSet scheduleSet = new ScheduleSet();
		scheduleSet.setOrgHospitalId(ShiroUserUtil.getHospitalId());
		scheduleSet.setName(scheName);
		EntityWrapper<ScheduleSet> setWrapper = new EntityWrapper<>(scheduleSet);
		ScheduleSet setBean = scheduleSetService.selectOne(setWrapper);
		if (setBean==null) {
			return new ExcelVerifyHanlderResult(false, "班次名称不正确");
		}
		obj.setScheId(setBean.getId());*/
		
		return new ExcelVerifyHanlderResult(true);
	}

}
