package com.shuzhuo.core.common.excel.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jeecgframework.poi.excel.annotation.Excel;

public class ItemExcelDTO {
	
	/**
     * 导入序号
     */
    @Excel(name = "序号", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\d+$", message="序号不正确")
    private String no;

	/**
	 * 项目编号
	 */
	@Excel(name = "项目编号", isImportField = "true")
    @NotNull
    private String num;
	
	/**
	 * 项目名称
	 */
	@Excel(name = "项目名称", isImportField = "true")
    @NotNull
    private String name;
	
    /**
     * 类型
     */
    @Excel(name = "类型", replace = {"救护_1","检查_2","治疗_3"}, isImportField = "true")
    @Pattern(regexp="^1|2|3$", message="类型不正确")
    private String type;
    
    /**
	 * 计价单位
	 */
	@Excel(name = "计价单位", isImportField = "true")
    @NotNull
    private String unit;
	
	/**
	 * 收费标准
	 */
	@Excel(name = "收费标准", isImportField = "true")
    @NotNull
    private String charge;
	
	/**
     * 项目内涵
     */
    @Excel(name = "项目内涵", isImportField = "true")
    private String connotation;
	
    /**
     * 除外内容
     */
    @Excel(name = "除外内容", isImportField = "true")
    private String except;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getConnotation() {
		return connotation;
	}

	public void setConnotation(String connotation) {
		this.connotation = connotation;
	}

	public String getExcept() {
		return except;
	}

	public void setExcept(String except) {
		this.except = except;
	}

}
