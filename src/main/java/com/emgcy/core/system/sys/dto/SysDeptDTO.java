package com.emgcy.core.system.sys.dto;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.emgcy.core.system.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

public class SysDeptDTO extends BaseEntity {
	
	private static final long serialVersionUID = 2752157777753190899L;
	
	/**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
	/**
     * 医院ID
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "医院ID")
	private Long hospitalId;
	/**
     * 医院名称
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "医院名称",hidden=true)
	private String hospitalName;
	/**
     * 上级科室ID
     */
	@TableField("parent_id")
    @ApiModelProperty(value = "上级科室ID")
	private Long parentId;
	/**
     * 上级科室名称
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "上级科室名称",hidden=true)
	private String parentName;
	/**
     * 编码
     */
    @ApiModelProperty(value = "编码")
	private String code;
    /**
     * 科室名称
     */
    @ApiModelProperty(value = "科室名称")
    @NotNull(message="科室名称不能为空")
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
