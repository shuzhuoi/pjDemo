package com.emgcy.core.system.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.emgcy.core.common.validator.group.AddGroup;
import com.emgcy.core.common.validator.group.UpdateGroup;
import com.emgcy.core.system.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-04
 */
@TableName("sys_org")
public class Org extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID",hidden=true)
	private String uuid;
    /**
     * 父级ID
     */
	@JsonIgnore
	@TableField("parent_id")
    @ApiModelProperty(value = "父级ID",hidden=true)
	private Long parentId;
	/**
     * 父级名称
     */
	@JsonIgnore
	@TableField(exist = false)
    @ApiModelProperty(value = "父级名称",hidden=true)
	private String parentName;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码")
	private String code;
    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    @NotNull(message="机构名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
    /**
     * 类型：1医院，2一级科室，3二级科室
     */
    @JsonIgnore
    @ApiModelProperty(value = "类型：1医院，2一级科室，3二级科室",hidden=true)
	private Integer type;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
	private String description;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @NotNull(message="电话不能为空")
	private String telephone;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    @NotNull(message="地址不能为空")
	private String address;
    /**
     * 网站
     */
    @JsonIgnore
    @ApiModelProperty(value = "网站",hidden=true)
	private String website;
    /**
     * logo图
     */
    @JsonIgnore
    @ApiModelProperty(value = "logo图")
	private String logo;
    /**
     * 医院等级（字典数据）
     */
    @ApiModelProperty(value = "医院等级")
    @NotNull(message="医院等级不能为空")
	private Long level;
    /**
     * 医院等级名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "医院等级名称",hidden=true)
	private String levelName;
    /**
     * 医院类型（字典数据）
     */
    @JsonIgnore
	@TableField("hospital_type")
    @ApiModelProperty(value = "医院类型",hidden=true)
	private Long hospitalType;
    /**
     * 医院类型名称
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "医院类型名称",hidden=true)
	private String hospitalTypeName;
    /**
     * 经度
     */
	@JsonIgnore
    @ApiModelProperty(value = "经度",hidden=true)
	private Double longitude;
    /**
     * 纬度
     */
	@JsonIgnore
    @ApiModelProperty(value = "纬度",hidden=true)
	private Double latitude;
    /**
     * 排序
     */
    @JsonIgnore
	@TableField("order_num")
    @ApiModelProperty(value = "排序",hidden=true)
	private Integer orderNum;
    
    /**
     * 管理员账号
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "管理员账号")
	private String account;
	
	/**
     * 管理员密码
     */
	@JsonIgnore
	@TableField(exist = false)
    @ApiModelProperty(value = "管理员密码")
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(Long hospitalType) {
		this.hospitalType = hospitalType;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getHospitalTypeName() {
		return hospitalTypeName;
	}

	public void setHospitalTypeName(String hospitalTypeName) {
		this.hospitalTypeName = hospitalTypeName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "Org{" +
			"id=" + id +
			", uuid=" + uuid +
			", parentId=" + parentId +
			", code=" + code +
			", name=" + name +
			", type=" + type +
			", description=" + description +
			", telephone=" + telephone +
			", address=" + address +
			", website=" + website +
			", logo=" + logo +
			", level=" + level +
			", hospitalType=" + hospitalType +
			", longitude=" + longitude +
			", latitude=" + latitude +
			", orderNum=" + orderNum +
			"}";
	}
}
