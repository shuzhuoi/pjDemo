package com.shuzhuo.core.common.excel.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jeecgframework.poi.excel.annotation.Excel;

public class ScheduleExcelDTO {
	
	/**
     * 导入序号
     */
    @Excel(name = "序号", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\d+$", message="序号不正确")
    private String num;
    
    /**
	 * 员工号
	 */
	@Excel(name = "员工号", isImportField = "true")
    @NotNull
    private String employeeId;
	
	/**
     * 用户ID
     */
	private Long userId;
	
	/**
     * 日期
     */
    @Excel(name = "日期",format = "yyyy/MM/dd",isImportField = "true")
    @NotNull
    private Date scheDate;
    
    /**
     * 班次ID
     */
	private Long scheId;
    
    /**
	 * 班次
	 */
	@Excel(name = "班次", isImportField = "true")
    @NotNull
    private String scheName;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getScheDate() {
		return scheDate;
	}

	public void setScheDate(Date scheDate) {
		this.scheDate = scheDate;
	}

	public Long getScheId() {
		return scheId;
	}

	public void setScheId(Long scheId) {
		this.scheId = scheId;
	}

	public String getScheName() {
		return scheName;
	}

	public void setScheName(String scheName) {
		this.scheName = scheName;
	}

}
