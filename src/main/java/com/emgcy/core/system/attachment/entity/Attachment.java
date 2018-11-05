package com.emgcy.core.system.attachment.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.emgcy.core.system.attachment.util.AttachmentUtils;
import com.emgcy.core.system.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-20
 */
@TableName("cr_attachment")
@ApiModel(value = "cr_attachment类")
public class Attachment extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "id", hidden = true)
	private Long id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
	/**
	 * 附件项类型(1:文档,2:压缩文件,3:图片,4:语音文件,5:视频文件,0:未知)
	 */
	@TableField("file_type")
	@ApiModelProperty(value = "附件项类型(1:文档,2:压缩文件,3:图片,4:语音文件,5:视频文件,0:未知)")
	private Integer fileType;
	/**
	 * 路径
	 */
	@ApiModelProperty(value = "路径", hidden = true)
	private String path;
	/**
	 * MD5散列值
	 */
	@TableField("md_five")
	@ApiModelProperty(value = "MD5")
	private String mdFive;
	/**
	 * 文件大小
	 */
	@ApiModelProperty(value = "文件大小")
	private Long size;
	/**
	 * 分组uuid
	 */
	@TableField("group_uuid")
	@ApiModelProperty(value = "分组uuid")
	private String groupUuid;
	/**
	 * 创建者人姓名
	 */
	@TableField("creator_name")
	@ApiModelProperty(value = "创建者人姓名")
	private String creatorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	@JsonIgnore
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMdFive() {
		return mdFive;
	}

	public void setMdFive(String mdFive) {
		this.mdFive = mdFive;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	@JsonIgnore
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}
	
	@ApiModelProperty(value = "其他文件路径")
	public String getOtherPath() {
		if (fileType!=null && fileType!=3 && fileType!=5)
			return AttachmentUtils.getSmallFilePath(name, mdFive,path);
		return null;
	}

	@ApiModelProperty(value = "图片路径")
	public String getImagePath() {
		if (fileType!=null && fileType==3)
			return AttachmentUtils.getSmallFilePath(name, mdFive,path);
		return null;
	}
	
	@ApiModelProperty(value = "视频文件路径")
	public String getVideoPath() {
		if (fileType!=null && fileType==5)
			return AttachmentUtils.getBigFileNginxPath(name, mdFive);
		return null;
	}

	@ApiModelProperty(value = "存入用户表的相对路径")
	public String getPhotoPath() {
		return AttachmentUtils.getPhotoPath(name, mdFive);
	}

	@Override
	public String toString() {
		return "Attachment [id=" + id + ", name=" + name + ", fileType=" + fileType + ", path=" + path + ", mdFive="
				+ mdFive + ", size=" + size + ", groupUuid=" + groupUuid + ", creatorName=" + creatorName + "]";
	}

}
