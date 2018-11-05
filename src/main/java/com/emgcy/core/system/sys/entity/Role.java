package com.emgcy.core.system.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.emgcy.core.system.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-15
 */
@TableName("sys_role")
@ApiModel(value = "角色表类")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 医院机构ID
     */
	@JsonIgnore
	@TableField("org_hospital_id")
    @ApiModelProperty(value = "医院机构ID",hidden=true)
	private Long orgHospitalId;
    /**
     * 角色类别：1前台角色，2后台角色
     */
	@TableField("role_type")
    @ApiModelProperty(value = "角色类别：1前台角色，2后台角色")
	@NotNull(message="角色类别不能为空")
	private Integer roleType;
    /**
     * 角色名称
     */
	@TableField("role_name")
    @ApiModelProperty(value = "角色名称")
	@NotNull(message="角色名称不能为空")
	private String roleName;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
	private String summary;
    /**
     * 状态：0禁用，1启用
     */
    @ApiModelProperty(value = "状态：0禁用，1启用")
	private Integer status;
    
    @TableField(exist = false)
    @ApiModelProperty(hidden=true)
    private List<Menu> menuList = Lists.newArrayList(); //菜单列表


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgHospitalId() {
		return orgHospitalId;
	}

	public void setOrgHospitalId(Long orgHospitalId) {
		this.orgHospitalId = orgHospitalId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Override
	public String toString() {
		return "Role{" +
			"id=" + id +
			", orgHospitalId=" + orgHospitalId +
			", roleType=" + roleType +
			", roleName=" + roleName +
			", summary=" + summary +
			", status=" + status +
			"}";
	}
}
