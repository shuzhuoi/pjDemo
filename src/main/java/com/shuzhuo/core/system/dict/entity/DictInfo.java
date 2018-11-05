package com.shuzhuo.core.system.dict.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-30
 */
@TableName("cr_dict_info")
@ApiModel(value = "字典表类")
public class DictInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "字典ID")
	private Long id;
    /**
     * 字典代码
     */
	@TableField("dict_code")
    @ApiModelProperty(value = "字典代码")
	private String dictCode;
    /**
     * 字典名称
     */
	@TableField("dict_name")
    @ApiModelProperty(value = "字典名称")
	private String dictName;
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

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "DictInfo{" +
			"id=" + id +
			", dictCode=" + dictCode +
			", dictName=" + dictName +
			", isEnable=" + isEnable +
			"}";
	}
}
