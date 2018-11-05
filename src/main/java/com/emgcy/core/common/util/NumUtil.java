package com.emgcy.core.common.util;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-16
 */
public class NumUtil {
	private static final int NUM = 6;

	/**
	 * 默认生成6位数
	 * 
	 * @param code代码
	 * @return
	 */
	public static String autoGenericCode(String code) {
		return autoGenericCode(code, NUM, true);
	}

	/**
	 * 
	 * @param code
	 *            代码
	 * @param num
	 *            生成数字代码的位数
	 * @demo
	 * @return
	 */
	public static String autoGenericCode(String code, int num) {
		return autoGenericCode(code, num, true);
	}

	/**
	 * 
	 * @param code
	 * @param num
	 *            生成数字代码的位数
	 * @param flag
	 *            true：当传入的数字代码大于生成数字代码的位数,则扩大位数
	 * @return
	 */
	public static String autoGenericCode(String code, int num, boolean flag) {
		if (flag && code.length() > num) {
			return code;
		}
		return String.format("%0" + num + "d", Integer.parseInt(code));

	}

//	public static void main(String[] args) {
//		System.out.println(autoGenericCode("12345"));
//		System.out.println(autoGenericCode("123456"));
//		System.out.println(autoGenericCode("1234567"));
//	}
}
