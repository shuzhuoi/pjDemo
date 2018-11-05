package com.emgcy.core.common.excel.dto;

public class ExcelLogDetailDTO {
	
	private int num;
	
    private boolean success;
    
    private String message;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
