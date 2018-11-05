package com.emgcy.core.system.dict.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.emgcy.core.system.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典明细表
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@TableName("cr_dict_item")
@ApiModel(value = "字典明细表类")
public class DictItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典明细ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "字典明细ID")
	private Long id;
    /**
     * 字典ID
     */
	@TableField("dict_id")
    @ApiModelProperty(value = "字典ID")
	private Long dictId;
    /**
     * 字典项代码
     */
	@TableField("dict_item_key")
    @ApiModelProperty(value = "字典项代码")
	private String dictItemKey;
    /**
     * 字典项值
     */
	@TableField("dict_item_value")
    @ApiModelProperty(value = "字典项值")
	private String dictItemValue;
    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
	private Integer sort;
    /**
     * 是否启用(0:禁用,1:启用)
     */
	@TableField("is_enable")
    @ApiModelProperty(value = "是否启用(0:禁用,1:启用)")
	private Integer isEnable;

	
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

	@Override
	public String toString() {
		return "DictItem{" +
			"id=" + id +
			", dictId=" + dictId +
			", dictItemKey=" + dictItemKey +
			", dictItemValue=" + dictItemValue +
			", sort=" + sort +
			", isEnable=" + isEnable +
			"}";
	}
}
