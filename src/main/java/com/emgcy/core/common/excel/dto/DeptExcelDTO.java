package com.emgcy.core.common.excel.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jeecgframework.poi.excel.annotation.Excel;

public class DeptExcelDTO {
	
	/**
     * 导入序号
     */
    @Excel(name = "序号", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\d+$", message="序号不正确")
    private String num;

	/**
	 * 科室名称
	 */
	@Excel(name = "科室名称", isImportField = "true")
    @NotNull
    private String name;
	
	/**
     * 医院ID
     */
	private Long hospitalId;
	
	/**
	 * 医院名称
	 */
	@Excel(name = "医院名称", isImportField = "true")
    @NotNull
    private String hospitalName;
	
	/**
     * 上级科室ID
     */
	private Long parentId;

    /**
     * 上级科室
     */
	@Excel(name = "上级科室", isImportField = "true")
    @NotNull
    private String parentDeptName;
    
	/**
	 * 编码
     */
	@Excel(name = "科室编码", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\w{4,10}$", message="不是正确的科室编码")
    private String code;

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

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
