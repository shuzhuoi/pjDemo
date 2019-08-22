package com.shuzhuo.core.common.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

public class Constant {

	/** 超级管理员ID */
	public static final long SUPER_ADMIN = 1;

	/** 用户类型:1系统管理员,2医院管理员,3急救人员,4后台用户 */
	public static final int SYS_ADMIN_TYPE = 1;
	public static final int HOSPITAL_ADMIN_TYPE = 2;
	public static final int FIRST_AID_TYPE = 3;
	public static final int BACK_USER_TYPE = 4;


	/**
	 * 角色类型:1-前台角色,2-后台角色
	 */
	public static final int FRONT_ROLE_TYPE = 1;
	public static final int BACK_ROLE_TYPE = 2;

	// session存放用户对象的key
	public static final String USER_SESSION_KEY = "currentUser";

	/**
	 * 删除标记（0:正常;-1:删除;2:审核;）
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = -1;
	public static final String DEL_FLAG_AUDIT = "2";

	/**
	 * 字典缓存和医院缓存、临时上传缓存
	 */
	public static final String DICT_CACHE = "dictCache";
	public static final String HOSPITAL_CACHE = "hospitalCache";

	/**
	 * 字典表(1:可用,0:不可用)
	 */
	public static final int DICT_ENABLED = 1;
	public static final int DICT_DISABLED = 0;


	/**
	 * 导入excel,头部标题占用行数
	 */
	public static final int IMPORT_EXCEL_TITLE_ROW = 1;

	/**
	 * es 相关配置
	 */
	public static final String ES_INDEXNAME="shuzhuo";
	public static final String ES_REFRESHINTERVAL="-1";
	public static final String ES_PRETAGS="<em style='color:red'>";
	public static final String ES_POSTTAGS="</em>";



}
