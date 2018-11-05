package com.emgcy.core.system.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户培训表
 * </p>
 *
 * @author buguangyi
 * @since 2018-01-11
 */
@TableName("sys_user_train")
public class UserTrain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 用户ID
     */
	@JsonIgnore
	@TableField("user_id")
    @ApiModelProperty(value = "用户ID",hidden=true)
	private Long userId;
    /**
     * 培训课程
     */
    @ApiModelProperty(value = "培训课程")
	private String course;
    /**
     * 培训时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("train_time")
    @ApiModelProperty(value = "培训时间")
	private Date trainTime;
    /**
     * 状态：-1取消，0草稿，1审核中，2发布失败，3已发布，4已完成
     */
    @ApiModelProperty(value = "状态：-1取消，0草稿，1审核中，2发布失败，3已发布，4已完成",hidden=true)
	private Integer status;
    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(hidden=true)
	private Long createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(hidden=true)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
    /**
     * 修改人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    @ApiModelProperty(hidden=true)
	private Long updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(hidden=true)
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
	private String remark;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public Date getTrainTime() {
		return trainTime;
	}

	public void setTrainTime(Date trainTime) {
		this.trainTime = trainTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserTrain{" +
			"id=" + id +
			", userId=" + userId +
			", course=" + course +
			", trainTime=" + trainTime +
			", status=" + status +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", updateBy=" + updateBy +
			", updateTime=" + updateTime +
			", remark=" + remark +
			"}";
	}
}
