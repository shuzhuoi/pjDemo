package com.shuzhuo.core.system.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.shuzhuo.core.system.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author buguangyi
 * @since 2017-12-28
 */
@TableName("sys_menu")
@ApiModel(value = "菜单管理类")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 菜单类别：1前台菜单，2后台菜单
     */
	@TableField("menu_type")
    @ApiModelProperty(value = "菜单类别：1前台菜单，2后台菜单",hidden=true)
	private Integer menuType;
    /**
     * 父级ID
     */
	@JsonIgnore
	@TableField("parent_id")
    @ApiModelProperty(value = "父级ID")
	private Long parentId;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
	private String name;
    /**
     * 菜单代码
     */
    @ApiModelProperty(value = "菜单代码")
	private String code;
    /**
     * 授权(多个用逗号分隔)
     */
    @JsonIgnore
    @ApiModelProperty(value = "授权(多个用逗号分隔)")
	private String perms;
    /**
     * 类型：0目录，1菜单，2按钮
     */
    @JsonIgnore
    @ApiModelProperty(value = "类型：0目录，1菜单，2按钮")
	private Integer type;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
	private String icon;
    /**
     * 用户类型：1系统管理员，2医院管理员（多个用逗号分隔）
     */
    @JsonIgnore
	@TableField("user_type")
    @ApiModelProperty(value = "用户类型：1系统管理员，2医院管理员（多个用逗号分隔）",hidden=true)
	private String userType;
    /**
     * 是否显示：0不显示，1显示
     */
    @JsonIgnore
	@TableField("is_show")
    @ApiModelProperty(value = "是否显示：0不显示，1显示",hidden=true)
	private Integer isShow;
    /**
     * 排序
     */
    @JsonIgnore
	@TableField("order_num")
    @ApiModelProperty(value = "排序",hidden=true)
	private Integer orderNum;
	
    @TableField(exist = false)
    @ApiModelProperty(hidden=true)
	private List<?> list;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Menu{" +
			"id=" + id +
			", menuType=" + menuType +
			", parentId=" + parentId +
			", name=" + name +
			", code=" + code +
			", perms=" + perms +
			", type=" + type +
			", icon=" + icon +
			", userType=" + userType +
			", isShow=" + isShow +
			", orderNum=" + orderNum +
			"}";
	}
}
