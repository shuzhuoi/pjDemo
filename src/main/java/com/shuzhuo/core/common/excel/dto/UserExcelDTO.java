package com.shuzhuo.core.common.excel.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.jeecgframework.poi.excel.annotation.Excel;

public class UserExcelDTO {
	
    /**
     * 导入序号
     */
    @Excel(name = "序号", isImportField = "true")
    @NotNull
    @Pattern(regexp="^\\d+$", message="序号不正确")
    private String num;
    
    /**
     * 姓名
     */
    @Excel(name = "姓名", isImportField = "true")
    @NotNull
    private String name;
    
    /**
     * 性别
     */
    @Excel(name = "性别", replace = {"男_1","女_2"}, isImportField = "true")
    @Pattern(regexp="^1|2$", message="不是正确的性别")
    private String sex;
    
    /**
     * 所属医院ID
     */
	private Long orgHospitalId;
    /**
     * 所属科室ID
     */
	private Long orgDeptId;

    /**
     * 所属科室名称
     */
    @Excel(name = "所属科室", isImportField = "true")
    @NotNull
    private String deptName;
    /**
     * 员工号
     */
    @Excel(name = "员工号", isImportField = "true")
    @NotNull
    private String employeeId;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", isImportField = "true")
    @NotNull
    @Pattern(regexp="^(13[0-9]|15[012356789]|17[012356789]|18[0-9]|14[57])[0-9]{8}$", message="手机号码不正确")
    private String phone;
    
    /**
     * 职称
     */
    @Excel(name = "职称", replace = {"住院医师_dzhuyuan","主治医师_dzhuzhi","副主任医师_dfuzhuren","主任医师_dzhuren","初级护士_nprimary","初级护师_njunior",
    		"主管护师_nzhuguan","副主任护师_nfuzhuren","主任护师_nzhuren","司机_osiji","其他_other"}, isImportField = "true")
    @NotNull
    private String jobTitleKey;
    
    /**
     * 职称(字典id)
     */
	private Long jobTitle;
    
    /**
     * 职务
     */
    @Excel(name = "职务", isImportField = "true")
    private String position;
    
    /**
     * 身份证
     */
    @Excel(name = "身份证", isImportField = "true")
    private String idCard;
    
    /**
     * 电子邮件
     */
    @Excel(name = "电子邮件", isImportField = "true")
    private String email;
    
    /**
     * 出生日期
     */
    @Excel(name = "出生日期",format = "yyyy-MM-dd",isImportField = "true")
    private Date birthday;
    
    /**
     * 是否可派单
     */
    @Excel(name = "是否可派单", replace = {"是_1","否_0"}, isImportField = "true")
    private String dispatch;
    
    /**
     * 是否专家
     */
    @Excel(name = "是否专家", replace = {"是_1","否_0"}, isImportField = "true")
    private String expert;
    
    /**
     * 擅长
     */
    @Excel(name = "擅长", isImportField = "true")
    private String major;
    
    /**
     * 简介
     */
    @Excel(name = "简介", isImportField = "true")
    private String summary;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getJobTitleKey() {
		return jobTitleKey;
	}

	public void setJobTitleKey(String jobTitleKey) {
		this.jobTitleKey = jobTitleKey;
	}

	public Long getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(Long jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getOrgHospitalId() {
		return orgHospitalId;
	}

	public void setOrgHospitalId(Long orgHospitalId) {
		this.orgHospitalId = orgHospitalId;
	}

	public Long getOrgDeptId() {
		return orgDeptId;
	}

	public void setOrgDeptId(Long orgDeptId) {
		this.orgDeptId = orgDeptId;
	}
	
}
