package com.emgcy.core.common.excel.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jeecgframework.poi.excel.annotation.Excel;

public class DrugExcelDTO {
	
	/**
     * 导入序号
     */
    @Excel(name = "序号", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\d+$", message="序号不正确")
    private String no;

	/**
	 * 药品编号
	 */
	@Excel(name = "药品编号", isImportField = "true")
    @NotNull
    private String num;
	
	/**
	 * 通用名
	 */
	@Excel(name = "通用名", isImportField = "true")
    @NotNull
    private String name;
	
    /**
     * 批准文号
     */
    @Excel(name = "批准文号", isImportField = "true")
    @NotNull
    private String approval;
    
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
     * 规格
     */
    @Excel(name = "规格", isImportField = "true")
    private String spec;
	
    /**
     * 厂家
     */
    @Excel(name = "厂家", isImportField = "true")
    private String company;

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

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
