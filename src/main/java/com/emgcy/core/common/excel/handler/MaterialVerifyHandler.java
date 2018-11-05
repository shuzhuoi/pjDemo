package com.emgcy.core.common.excel.handler;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.emgcy.core.common.excel.dto.MaterialExcelDTO;
import com.emgcy.core.common.shiro.ShiroUserUtil;


@Component
public class MaterialVerifyHandler implements IExcelVerifyHandler<MaterialExcelDTO>{
	
	/*@Autowired
	private IMaterialService materialService;*/

	@Override
	public ExcelVerifyHanlderResult verifyHandler(MaterialExcelDTO obj) {
		//判断编号是否已存在
		/*EntityWrapper<Material> wrapper = new EntityWrapper<>(new Material());
		wrapper.eq("org_hospital_id", ShiroUserUtil.getHospitalId());
		wrapper.eq("num", obj.getNum());
		int numCount = materialService.selectCount(wrapper);
		if (numCount>0) {
			return new ExcelVerifyHanlderResult(false, "材料编号已存在");
		}*/
		return new ExcelVerifyHanlderResult(true);
	}

}
