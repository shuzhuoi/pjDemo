package com.shuzhuo.core.common.excel.handler;

import com.shuzhuo.core.common.excel.dto.DeptExcelDTO;
import com.shuzhuo.core.system.sys.entity.Org;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.shiro.ShiroUserUtil;

@Component
public class DeptVerifyHandler implements IExcelVerifyHandler<DeptExcelDTO>{
	
	@Autowired
	private IOrgService orgService;

	@Override
	public ExcelVerifyHanlderResult verifyHandler(DeptExcelDTO dept) {
		String hospitalName = dept.getHospitalName();
		EntityWrapper<Org> wrapper = new EntityWrapper<>(new Org());
		wrapper.eq("name", hospitalName);
		wrapper.eq("type", 1);
		
		Org hospital = orgService.selectOne(wrapper);
		
		if (hospital == null){
			return new ExcelVerifyHanlderResult(false, "医院名称不正确");
		}
		
		//不是超级管理员则医院要为用户所在医院
		if(ShiroUserUtil.getUserType()!=Constant.SYS_ADMIN_TYPE){
			if (!ShiroUserUtil.getHospitalId().equals(hospital.getId())) {
				return new ExcelVerifyHanlderResult(false, "医院名称不正确");
			}
		}
		dept.setHospitalId(hospital.getId());
		
//		EntityWrapper<Org> deptWrapper = new EntityWrapper<>(new Org());
//		//一级科室
//		if (StringUtils.isBlank(dept.getParentDeptName())||"无".equals(dept.getParentDeptName())) {
//			deptWrapper.eq("parent_id", hospital.getId());
//			deptWrapper.eq("name", dept.getName());
//			deptWrapper.eq("type", 2);
//			int count = orgService.selectCount(deptWrapper);
//			if (count>0) {
//				return new ExcelVerifyHanlderResult(false, "科室名称已经存在");
//			}
//		} else {//二级科室
//			deptWrapper.eq("parent_id", hospital.getId());
//			deptWrapper.eq("name", dept.getParentDeptName());
//			deptWrapper.eq("type", 2);
//			Org oneDept = orgService.selectOne(deptWrapper);
//			if (oneDept==null) {
//				return new ExcelVerifyHanlderResult(false, "上级科室名称不存在");
//			}
//			
//			EntityWrapper<Org> deptWrapper2 = new EntityWrapper<>(new Org());
//			deptWrapper2.eq("parent_id", oneDept.getId());
//			deptWrapper2.eq("name", dept.getName());
//			deptWrapper2.eq("type", 3);
//			int count = orgService.selectCount(deptWrapper2);
//			if (count>0) {
//				return new ExcelVerifyHanlderResult(false, "科室名称已经存在");
//			}
//			
//			dept.setParentId(oneDept.getId());
//		}
		return new ExcelVerifyHanlderResult(true);
	}

}
