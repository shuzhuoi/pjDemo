package com.shuzhuo.core.common.enm;


/**
 * 返回结果代码类
 * @author chenshuzhuo
 * @date 2017-11-23
 */
public enum ResultCode {

	fail									(0,"fail"),
	OK										(1,"success"),
	PARAMETER_ERROR							(301,"请求参数错误"),
	INVALID_TOKEN                           (401, "invalid token"),
	UNKNOWN									(500,"未知错误");

	/**
	 * http状态码
	 */
	private int code;
	/**
	 * http状态码对应的消息
	 */
	private String message;

	ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return this.message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return this.message;
	}

	/**
	 * 自定义错误提示, 错误代码为9999
	 * 
	 * @param desc
	 * @return
	 */
	public static ResultCode customDesc(String desc) {
		UNKNOWN.message = desc;
		return UNKNOWN;
	}

	/**
	 * 根据代码获取消息
	 * 
	 * @param code
	 * @return
	 */
	public static ResultCode getMsg(int code) {
		for (ResultCode resultCode : values()) {
			if (resultCode.code == code) {
				return resultCode;
			}
		}
		return null;
	}
	
}
