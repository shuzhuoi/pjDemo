package com.shuzhuo.core.system.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.shuzhuo.core.system.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 审核流程表
 * </p>
 *
 * @author ouyuming
 * @since 2017-12-08
 */
@TableName("cr_flow")
@ApiModel(value = "审核流程表类")
public class Flow extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
	private Long id;
    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID",hidden = true)
	private String uuid;
    /**
     * 医院机构ID
     */
	@TableField("org_hospital_id")
    @ApiModelProperty(value = "医院机构ID",hidden = true)
	private Long orgHospitalId;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
	private String no;
    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称")
	private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
	private String description;
    /**
     * 权限编码
     */
	@TableField("perm_code")
    @ApiModelProperty(value = "权限编码")
	private String permCode;
	/**
	 * 业务类型（见Constant.RECORD_TYPE_*）
	 */
	@ApiModelProperty(value="业务类型:1-远程会诊 2-文章 3-视频 4-专辑 5-线下培训")
	@TableField("bus_type")
	private String busType;
    /**
     * 流程类型：1 只有转出 ， 2有转入和转出
     */
    @ApiModelProperty(value = "流程类型：1 只有转出 ， 2有转入和转出",hidden = true)
	private String type;
    /**
     * 转出描述
     */
	@TableField("out_desc")
    @ApiModelProperty(value = "转出描述",hidden = true)
	private String outDesc;
    /**
     * 转入描述
     */
	@TableField("in_desc")
    @ApiModelProperty(value = "转入描述",hidden = true)
	private String inDesc;
    /**
     * 状态：1可用、0不可用
     */
    @ApiModelProperty(value = "状态：1可用、0不可用",hidden = true)
	private String state;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsonIgnore
	public Long getOrgHospitalId() {
		return orgHospitalId;
	}

	public void setOrgHospitalId(Long orgHospitalId) {
		this.orgHospitalId = orgHospitalId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	@JsonIgnore
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public String getOutDesc() {
		return outDesc;
	}

	public void setOutDesc(String outDesc) {
		this.outDesc = outDesc;
	}

	@JsonIgnore
	public String getInDesc() {
		return inDesc;
	}

	public void setInDesc(String inDesc) {
		this.inDesc = inDesc;
	}

	@JsonIgnore
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Flow{" +
			"id=" + id +
			", uuid=" + uuid +
			", orgHospitalId=" + orgHospitalId +
			", no=" + no +
			", name=" + name +
			", description=" + description +
			", permCode=" + permCode +
			", type=" + type +
			", outDesc=" + outDesc +
			", inDesc=" + inDesc +
			", state=" + state +
			"}";
	}
}
