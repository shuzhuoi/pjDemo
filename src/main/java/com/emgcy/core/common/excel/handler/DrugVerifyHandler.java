package com.emgcy.core.common.excel.handler;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import org.springframework.stereotype.Component;


import com.emgcy.core.common.excel.dto.DrugExcelDTO;


@Component
public class DrugVerifyHandler implements IExcelVerifyHandler<DrugExcelDTO>{
	
	/*@Autowired
	private IDrugService drugService;*/

	@Override
	public ExcelVerifyHanlderResult verifyHandler(DrugExcelDTO obj) {
		
		/*//判断编号是否已存在
		EntityWrapper<Drug> wrapper = new EntityWrapper<>(new Drug());
		wrapper.eq("org_hospital_id", ShiroUserUtil.getHospitalId());
		wrapper.eq("num", obj.getNum());
		int numCount = drugService.selectCount(wrapper);
		if (numCount>0) {
			return new ExcelVerifyHanlderResult(false, "药品编号已存在");
		}
*/
		return new ExcelVerifyHanlderResult(true);
	}

}
