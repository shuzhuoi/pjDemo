package com.shuzhuo.core.system.sys.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户附件表
 * </p>
 *
 * @author buguangyi
 * @since 2018-01-10
 */
@TableName("sys_user_attachment")
public class UserAttachment implements Serializable {

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
	@TableField("user_id")
    @ApiModelProperty(value = "用户ID")
	private Long userId;
    /**
     * 附件MD5
     */
	@TableField("attach_md5")
    @ApiModelProperty(value = "附件MD5")
	private String attachMd5;
    /**
     * 附件类型：1电子签名，2医师资格证，3医师执业证
     */
	@TableField("attach_type")
    @ApiModelProperty(value = "附件类型：1电子签名，2医师资格证，3医师执业证")
	private Integer attachType;
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
	@ApiModelProperty(hidden=true)
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

	public String getAttachMd5() {
		return attachMd5;
	}

	public void setAttachMd5(String attachMd5) {
		this.attachMd5 = attachMd5;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
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
		return "UserAttachment{" +
			"id=" + id +
			", userId=" + userId +
			", attachMd5=" + attachMd5 +
			", attachType=" + attachType +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", updateBy=" + updateBy +
			", updateTime=" + updateTime +
			", remark=" + remark +
			"}";
	}
}
