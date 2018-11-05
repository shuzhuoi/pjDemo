package com.shuzhuo.core.common.excel.handler;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.stereotype.Component;

import com.shuzhuo.core.common.excel.dto.ItemExcelDTO;

@Component
public class ItemVerifyHandler implements IExcelVerifyHandler<ItemExcelDTO>{
	
	/*@Autowired
	private IItemService itemService;*/

	@Override
	public ExcelVerifyHanlderResult verifyHandler(ItemExcelDTO obj) {
		
		/*//判断编号是否已存在
		EntityWrapper<Item> wrapper = new EntityWrapper<>(new Item());
		wrapper.eq("org_hospital_id", ShiroUserUtil.getHospitalId());
		wrapper.eq("num", obj.getNum());
		int numCount = itemService.selectCount(wrapper);
		if (numCount>0) {
			return new ExcelVerifyHanlderResult(false, "项目编号已存在");
		}
		*/
		return new ExcelVerifyHanlderResult(true);
	}

}
