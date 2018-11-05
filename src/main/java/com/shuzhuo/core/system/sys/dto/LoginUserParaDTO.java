package com.shuzhuo.core.system.sys.dto;

public class LoginUserParaDTO {
	
    /**
     * 医院ID(手机号登录时为空)
     */
	private String hospitalId;
    /**
     * 用户名(手机号或工号)
     */
	private String username;
    /**
     * 密码
     */
	private String password;
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
