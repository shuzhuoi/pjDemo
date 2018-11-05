package com.emgcy.core.system.dict.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * 字典表所有详情信息
 * 
 * @author chenshuzhuo
 * @date 2017-11-30
 */
public class DictAllInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5514324709217952423L;
	/**
	 * 字典明细ID
	 */
	@ApiModelProperty(value = "字典明细ID")
	private Long id;
	/**
	 * 字典ID
	 */
	@ApiModelProperty(value = "字典ID",hidden=true)
	@JsonIgnore
	private Long dictId;
	/**
	 * 字典项代码
	 */
	@ApiModelProperty(value = "字典项代码key")
	@JsonIgnore
	private String dictItemKey;
	/**
	 * 字典项值
	 */
	@ApiModelProperty(value = "字典项值val")
	private String dictItemValue;
	/**
	 * 排序号
	 */
	@ApiModelProperty(value = "排序号",hidden=true)
	@JsonIgnore
	private Integer sort;
	/**
	 * 明细表,是否启用(0:禁用,1:启用)
	 */
	@ApiModelProperty(value = "是否启用(0:禁用,1:启用)",hidden=true)
	@JsonIgnore
	private Integer isEnable;

	/**
	 * 字典代码
	 */
	@ApiModelProperty(value = "字典代码")
	@JsonIgnore
	private String dictCode;
	/**
	 * 字典名称
	 */
	@ApiModelProperty(value = "字典名称",hidden=true)
	@JsonIgnore
	private String dictName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictItemKey() {
		return dictItemKey;
	}

	public void setDictItemKey(String dictItemKey) {
		this.dictItemKey = dictItemKey;
	}

	public String getDictItemValue() {
		return dictItemValue;
	}

	public void setDictItemValue(String dictItemValue) {
		this.dictItemValue = dictItemValue;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@Override
	public String toString() {
		return "DictAllInfo [id=" + id + ", dictId=" + dictId + ", dictItemKey=" + dictItemKey + ", dictItemValue="
				+ dictItemValue + ", sort=" + sort + ", isEnable=" + isEnable + ", dictCode=" + dictCode
				+ ", dictName=" + dictName + "]";
	}

}
