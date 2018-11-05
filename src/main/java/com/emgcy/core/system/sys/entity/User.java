package com.emgcy.core.system.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.emgcy.core.common.validator.ArrayValid;
import com.emgcy.core.common.validator.group.AddGroup;
import com.emgcy.core.common.validator.group.UpdateGroup;
import com.emgcy.core.system.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author buguangyi
 * @since 2018-01-17
 */
@TableName("sys_user")
@ApiModel(value = "系统用户表类")
public class User extends BaseEntity {

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
	@JsonIgnore
    @ApiModelProperty(value = "UUID",hidden=true)
	private String uuid;
    /**
     * 医院机构ID
     */
    @JsonIgnore
	@TableField("org_hospital_id")
    @ApiModelProperty(value = "医院机构ID",hidden=true)
	private Long orgHospitalId;
	/**
     * 医院名称
     */
	@TableField(exist = false)
	@ApiModelProperty(value = "医院名称",hidden=true)
	private String hospitalName;
    /**
     * 科室机构ID
     */
	@TableField("org_dept_id")
    @ApiModelProperty(value = "科室机构ID")
	@NotNull(message="科室不能为空")
	private Long orgDeptId;
	/**
     * 科室名称
     */
	@TableField(exist = false)
	@ApiModelProperty(value = "科室名称",hidden=true)
	private String deptName;
    /**
     * 员工号
     */
	@TableField("employee_id")
    @ApiModelProperty(value = "员工号")
	@NotNull(message="员工号不能为空")
	private String employeeId;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    @NotNull(message="姓名不能为空")
	private String name;
    /**
     * 用户名
     */
    @JsonIgnore
	@TableField("user_name")
    @ApiModelProperty(value = "用户名",hidden=true)
	private String userName;
    /**
     * 电子签名密码
     */
	@JsonIgnore
	@TableField("sign_password")
    @ApiModelProperty(value = "电子签名密码",hidden=true)
	private String signPassword;
    /**
     * 密码
     */
	@JsonIgnore
    @ApiModelProperty(value = "密码",hidden=true)
	private String password;
    /**
     * 盐
     */
	@JsonIgnore
    @ApiModelProperty(value = "盐",hidden=true)
	private String salt;
    /**
     * 性别：1男，2女
     */
    @ApiModelProperty(value = "性别：1男，2女")
    @ArrayValid(value={"1","2"}, message="性别不正确", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message="性别不能为空")
	private Integer sex;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
	private Date birthday;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    @NotNull(message="手机号不能为空")
	private String phone;
    /**
     * 身份证号
     */
	@TableField("id_card")
    @ApiModelProperty(value = "身份证号")
	private String idCard;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
	private String email;
    /**
     * 地址
     */
    @JsonIgnore
    @ApiModelProperty(value = "地址",hidden=true)
	private String address;
    /**
     * 头像照片
     */
    @ApiModelProperty(value = "头像照片")
	private String photo;
    /**
     * 个人签名
     */
    @ApiModelProperty(value = "个人签名",hidden=true)
	private String signature;
    /**
     * 职称（对应字典表）
     */
	@TableField("job_title")
    @ApiModelProperty(value = "职称（对应字典表）")
	@NotNull(message="职称不能为空")
	private Long jobTitle;
	/**
     * 职称(显示用)
     */
	@TableField(exist = false)
    @ApiModelProperty(value = "职称(显示用)")
	private String jobTitleName;
    /**
     * 职务
     */
    @ApiModelProperty(value = "职务")
	private String position;
    /**
     * 是否可派单：0否，1是
     */
    @ApiModelProperty(value = "是否可派单：0否，1是")
	private Integer dispatch;
    /**
     * 用户类型：1系统管理员，2医院管理员，3急救人员，4后台用户
     */
    @JsonIgnore
	@TableField("user_type")
    @ApiModelProperty(value = "用户类型：1系统管理员，2医院管理员，3急救人员，4后台用户",hidden=true)
	private Integer userType;
    /**
     * 擅长
     */
    @ApiModelProperty(value = "擅长")
	private String major;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
	private String summary;
    /**
     * 使用状态：-1停用，0未激活，1可用
     */
    @ApiModelProperty(value = "使用状态：-1停用，0未激活，1可用",hidden=true)
	private Integer status;
    /**
     * 人员状态：1站内待命，2途中待命，3待出诊，4出诊中，5会诊中
     */
	@TableField("staff_status")
    @ApiModelProperty(value = "人员状态：1站内待命，2途中待命，3待出诊，4出诊中，5会诊中",hidden=true)
	private Integer staffStatus;
    /**
     * 注册时间
     */
	@JsonIgnore
	@TableField("register_time")
    @ApiModelProperty(value = "注册时间",hidden=true)
	private Date registerTime;
    /**
     * 上次登录时间
     */
	@JsonIgnore
	@TableField("login_time")
    @ApiModelProperty(value = "上次登录时间",hidden=true)
	private Date loginTime;
    /**
     * 上次登录IP
     */
	@JsonIgnore
	@TableField("login_ip")
    @ApiModelProperty(value = "上次登录IP",hidden=true)
	private String loginIp;
    /**
     * 是否在线：0不在线，1在线
     */
	@JsonIgnore
    @ApiModelProperty(value = "是否在线：0不在线，1在线",hidden=true)
	private Integer online;
    /**
     * 是否为专家：0否，1是
     */
    @ApiModelProperty(value = "是否为专家：0否，1是")
	private Integer expert;
    
    @JsonIgnore
    @TableField(exist = false)
    @ApiModelProperty(hidden=true)
    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
    
    /**
     * 医师资格证
     */
	@TableField(exist = false)
	@ApiModelProperty(value = "医师资格证")
	private String qualification;
	
	/**
     * 医师执业证
     */
	@TableField(exist = false)
	@ApiModelProperty(value = "医师执业证")
	private String practice;
	
	/**
     * 培训教育
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "培训教育")
    private List<UserTrain> trainList = Lists.newArrayList();
    
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

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSignPassword() {
		return signPassword;
	}

	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public Integer getDispatch() {
		return dispatch;
	}

	public void setDispatch(Integer dispatch) {
		this.dispatch = dispatch;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStaffStatus() {
		return staffStatus;
	}

	public void setStaffStatus(Integer staffStatus) {
		this.staffStatus = staffStatus;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getExpert() {
		return expert;
	}

	public void setExpert(Integer expert) {
		this.expert = expert;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public List<UserTrain> getTrainList() {
		return trainList;
	}

	public void setTrainList(List<UserTrain> trainList) {
		this.trainList = trainList;
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", uuid=" + uuid +
			", orgHospitalId=" + orgHospitalId +
			", orgDeptId=" + orgDeptId +
			", employeeId=" + employeeId +
			", name=" + name +
			", userName=" + userName +
			", signPassword=" + signPassword +
			", password=" + password +
			", salt=" + salt +
			", sex=" + sex +
			", birthday=" + birthday +
			", phone=" + phone +
			", idCard=" + idCard +
			", email=" + email +
			", address=" + address +
			", photo=" + photo +
			", signature=" + signature +
			", jobTitle=" + jobTitle +
			", position=" + position +
			", dispatch=" + dispatch +
			", userType=" + userType +
			", major=" + major +
			", summary=" + summary +
			", status=" + status +
			", staffStatus=" + staffStatus +
			", registerTime=" + registerTime +
			", loginTime=" + loginTime +
			", loginIp=" + loginIp +
			", online=" + online +
			", expert=" + expert +
			"}";
	}
}
