package com.emgcy.core.common.excel.dto;

import java.util.List;

import com.google.common.collect.Lists;

public class ImportExcelLogDTO {
	
	private int successCount = 0;

    private List<ExcelLogDetailDTO> list = Lists.newArrayList();

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public List<ExcelLogDetailDTO> getList() {
		return list;
	}

	public void setList(List<ExcelLogDetailDTO> list) {
		this.list = list;
	}

}
