package com.emgcy.core.system.attachment.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-06
 *
 */
public class BaiduUploaderBean {

	@NotBlank(groups = { UploadBigFile.class,UploadCheck.class,UploadHandler.class })
	@Length(min = 32, max = 32, message = "长度必须32位", groups = {UploadBigFile.class,UploadCheck.class,UploadHandler.class })
	private String fileMd5;
	
	@NotBlank(groups = { UploadBigFile.class })
	private String id;
	
	@NotBlank(groups = { UploadBigFile.class,UploadCheck.class ,UploadHandler.class})
	private String name;
	
	@NotBlank(groups = { UploadBigFile.class })
	private String type;
	
	@NotNull(groups = { UploadBigFile.class })
	private Date lastModifiedDate;
	
	@NotNull(groups = { UploadBigFile.class ,UploadHandler.class})
	private Long size;
	
	@NotBlank(groups = { UploadBigFile.class })
	private String chunks;
	
	@NotBlank(groups = { UploadBigFile.class,UploadCheck.class })
	private String chunk;

	private String remark;

	@NotNull(groups={UploadCheck.class})
	private Long chunkSize;

	public interface UploadCheck {
	}

	public interface UploadBigFile {
	}
	public interface UploadHandler {
	}

	public String getFileMd5() {
		return fileMd5;
	}

	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getChunks() {
		return chunks;
	}

	public void setChunks(String chunks) {
		this.chunks = chunks;
	}

	public String getChunk() {
		return chunk;
	}

	public void setChunk(String chunk) {
		this.chunk = chunk;
	}

	public Long getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(Long chunkSize) {
		this.chunkSize = chunkSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BaiduUploaderBean [fileMd5=" + fileMd5 + ", id=" + id + ", name=" + name + ", type=" + type
				+ ", lastModifiedDate=" + lastModifiedDate + ", size=" + size + ", chunks=" + chunks + ", chunk="
				+ chunk + ", remark=" + remark + ", chunkSize=" + chunkSize + "]";
	}

}
