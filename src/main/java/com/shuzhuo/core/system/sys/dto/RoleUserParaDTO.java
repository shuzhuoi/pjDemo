package com.shuzhuo.core.system.sys.dto;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;

public class RoleUserParaDTO implements Serializable {
	
	private static final long serialVersionUID = -2942284328552698266L;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;
    
    @ApiModelProperty(value = "用户ID列表")
    private List<Long> userIdList = Lists.newArrayList();

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Long> userIdList) {
		this.userIdList = userIdList;
	}

}
