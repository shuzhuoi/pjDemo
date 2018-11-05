package com.emgcy.core.system.base;

public class SearchEntity {
	
	/**
     * 查询字段key
     */
	private String fieldKey;
	/**
     * 查询字段value
     */
	private String fieldVal;
	/**
     * 查询类型：1普通，2模糊，3范围
     */
	private String flag;
	public String getFieldKey() {
		return fieldKey;
	}
	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}
	public String getFieldVal() {
		return fieldVal;
	}
	public void setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
