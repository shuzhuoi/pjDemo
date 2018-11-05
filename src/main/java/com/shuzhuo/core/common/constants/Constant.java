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
	 * 机构表类型:1医院,2一级科室,3二级科室
	 */
	public static final int ORG_HTYPE = 1;
	public static final int ORG_DOTYPE = 2;
	public static final int ORG_DTTYPE = 3;

	/**
	 * 导入excel,头部标题占用行数
	 */
	public static final int IMPORT_EXCEL_TITLE_ROW = 1;

	/**
	 * es 相关配置
	 */
	public static final String ES_INDEXNAME="emgcy";
	public static final String ES_REFRESHINTERVAL="-1";
	public static final String ES_PRETAGS="<em style='color:red'>";
	public static final String ES_POSTTAGS="</em>";
	/**
	 * 是否可派单：0否,1是
	 */
	public static final int DISPATCH_TRUE=1;
	public static final int DISPATCH_FALSE=0;
	
	/**
	 * 点赞状态(0:未点赞,1:已点赞)
	 */
	public static final int PRAISE_NOT=0;
	public static final int PRAISE_OK=1;
	
	/**
	 * 收藏状态(0:未收藏,1:已收藏)
	 */
	public static final int COLLECT_NOT=0;
	public static final int COLLECT_OK=1;
	
	/**
	 * 事件类型(0:个人事件,大于0:群体事件)
	 */
	public static final long EVENT_TYPE=0; 
	
	/**
	 * 人员状态:1站内待命,2途中待命,3待出诊,4出诊中,5会诊中,6休假
	 */
	public static final int STAFF_STATUS_STATION = 1;
	public static final int STAFF_STATUS_PASSAGE = 2;
	public static final int STAFF_STATUS_STAY = 3;
	public static final int STAFF_STATUS_OUT = 4;
	public static final int STAFF_STATUS_CONSULTATION = 5;
	public static final int STAFF_STATUS_RECESS = 6;

	/**
	 * 调度车辆状态 0-结束任务 1-暂停调用 2-收到指令 3-驶向现场 4-到达现场 5-病人上车 6-到达医院 7-移交完成 8-途中待命
	 */
	public static final int DIS_VE_STATE_PASSAGE = 8;

	/**
	 * 医生护士工作站急救出查询状态(-1:取消,0:未出诊,1:待出诊,2:出诊中,3:已结束)
	 */
	public static final String DOC_NUR_STATE_CAL = "-1";
	public static final String DOC_NUR_STATE_NOT = "0";
	public static final String DOC_NUR_STATE_STAY = "1";
	public static final String DOC_NUR_STATE_OUT = "2";
	public static final String DOC_NUR_STATE_END = "3";

	/**
	 * 120 调度任务状态(-1:已取消,0:待受理,1:未受理,2:已受理,3:超时)
	 */
	public static final String DISPATCH_CANCEL = "-1";
	public static final String DISPATCH_WAIT = "0";
	public static final String DISPATCH_NOT = "1";
	public static final String DISPATCH_SUCCESS = "2";
	public static final String DISPATCH_OVERTIME = "3";
	
	/**
	 * 120 调度记录 前台医生护士响应状态 (0:未结束,1:已结束,2:未出诊)
	 */
	public static final int DIS_REC_STATE_ING= 0 ;
	public static final int DIS_REC_STATE_END= 1;
	public static final int DIS_REC_STATE_NOT= 2;

	/**
	 * 大文件存放位置
	 */
	public static final String BIGFILEFOLDER = "bigFileFolder";

	/**
	 * 调度员/管理员工作台 菜单代码
	 */
	public static final String MENUCODE_DISPATCH = "front:dispatch";

	/**
	 * 培训管理模块 状态(-1:取消,0:草稿,1:审核中,2:发布失败,3:已发布,4:已完成)
	 */
	public static final int STATE_CANCEL = -1;
	public static final int STATE_DRAFT = 0;
	public static final int STATE_AUDITI = 1;
	public static final int STATE_FAIL = 2;
	public static final int STATE_SUCCESS = 3;
	public static final int STATE_COMPLETED = 4;

	/**
	 * 0-未发送 1-已发送
	 */
	public static final String UNSEND = "0";
	public static final String SEND = "1";

	/**
	 * 0-未读状态 1-已读状态
	 */
	public static final String UNREAD = "0";
	public static final String READ = "1";

	/**
	 * 默认审核为一审
	 */
	public static final Integer FIRST_AUDIT = 1;

	/**
	 * 审核权限代码:flow:consultation;flow:article;flow:video;flow:album;flow:offline
	 */
	public static final String CONSULTATION_AUDIT_CODE = "flow:consultation";
	public static final String ARTICLE_AUDIT_CODE = "flow:article";
	public static final String VIDEO_AUDIT_CODE = "flow:video";
	public static final String ALBUM_AUDIT_CODE = "flow:album";
	public static final String OFFICELINE_AUDIT_CODE = "flow:offline";

	/**
	 * 审核名称
	 */
	public static Map<Integer, String> auditNameMap = new HashMap<Integer, String>() {
		{
			put(1, "一审");
			put(2, "二审");
			put(3, "三审");
			put(4, "四审");
			put(5, "五审");
			put(6, "六审");
			put(7, "七审");
			put(8, "八审");
			put(9, "九审");
			put(10, "十审");
			put(11, "十一审");
			put(12, "十二审");
			put(13, "十三审");
			put(14, "十四审");
			put(15, "十五审");
			put(16, "十六审");
			put(17, "十七审");
			put(18, "十八审");
			put(19, "十九审");
			put(20, "二十审");
		}
	};

	/**
	 * 审核的权限code
	 */
	public static final String AUDIT_CONSULTATION_APPLY = "audit:consultation:apply";
	public static final String AUDIT_ARTICLE_APPLY = "audit:article:apply";
	public static final String AUDIT_ALBUM_APPLY = "audit:album:apply";
	public static final String AUDIT_OFFLINETRAIN_APPLY = "audit:offlinetrain:apply";
	public static final String RECORD_VIDEO_APPLY = "audit:video:apply";

	/**
	 * 审核申请的业务标题
	 */
	public static final String TITLE_CONSULTATION_APPLY = "您有一条新的远程会诊业务审核请求";
	public static final String TITLE_ARTICLE_APPLY = "您有一条新的文章业务审核请求";
	public static final String TITLE_ALBUM_APPLY = "您有一条新的专辑业务审核请求";
	public static final String TITLE_OFFLINETRAIN_APPLY = "您有一条新的线下培训业务审核请求";
	public static final String TITLE_VIDEO_APPLY = "您有一条新的视频业务审核请求";

	/**
	 * 业务类型: 1-远程会诊 2-文章 3-视频 4-专辑 5-线下培训 6-调度任务 7-急救出诊(用于审核和消息)
	 */
	public static final String RECORD_TYPE_REMOTE_CONSULTATION = "1";
	public static final String RECORD_TYPE_ARTICLE = "2";
	public static final String RECORD_TYPE_VIDEO = "3";
	public static final String RECORD_TYPE_ALBUM = "4";
	public static final String RECORD_TYPE_OFFLINETRAIN = "5";
	public static final String RECORD_TYPE_SCHEDUL_TASK = "6";
	public static final String RECORD_TYPE_FIRST_AID = "7";

	/**
	 * 审核类型(1-转出审核 2-转入审核)
	 */
	public static final String CHECK_OUT_TYPE = "1";
	public static final String CHECK_IN_TYPE = "2";

	/**
	 * 基本审核状态(0-未审核 1-已审核 2-结束)
	 */
	public static final String CHECK_WAIT = "0";
	public static final String ALREADY_AUDITED = "1";
	public static final String End_AUDITED = "2";

	/**
	 * 审核是否通过(1-通过 0-不通过)
	 */
	public static final String CHECK_NOT_PASS = "2";
	public static final String CHECK_PASS = "1";

	/**
	 * 基本审核返回信息
	 */
	public static final String CHECK_MAP_MOVEID = "moveId";
	public static final String CHECK_MAP_MYFILE = "myFile";
	public static final String CHECK_MAP_RESULT = "result";
	public static final String CHECK_MAP_MSG = "msg";
	public static final String CHECK_MAP_OUR_STATE = "ourState";
	public static final String CHECK_MAP_RESULT_TRUE = "true";
	public static final String CHECK_MAP_RESULT_FALSE = "false";

	/**
	 * 多审命名(名称)
	 */
	public static final String CHECK_FIRST_AUDIT = "一审";
	public static final String CHECK_SECOND_AUDIT = "二审";
	public static final String CHECK_THIRD_AUDIT = "三审";
	public static final String CHECK_FOUR_AUDIT = "四审";
	public static final String CHECK_FIFTH_AUDIT = "五审";

	/**
	 * 多审命名(数字)
	 */
	public static final Integer NUMBER_ZERO_AUDIT = 0;
	public static final Integer NUMBER_FIRST_AUDIT = 1;
	public static final Integer NUMBER_SECOND_AUDIT = 2;
	public static final Integer NUMBER_THIRD_AUDIT = 3;
	public static final Integer NUMBER_FOUR_AUDIT = 4;
	public static final Integer NUMBER_FIFTH_AUDIT = 5;

	/**
	 * 删除审核步骤返回值
	 */
	public static final int DELETE_STEP_NULL = 0;
	public static final int DELETE_STEP_SUCCESS = 1;
	public static final int DELETE_STEP_FAIL = 2;
	public static final int DELETE_STEP_CONSULTATION_FAIL = 3;

	/**
	 * 校验app端token返回值
	 */
	public static final int TOKEN_NULL = 0;
	public static final int TOKEN_INVALID = 1;
	public static final int TOKEN_EFFECT = 2;
	public static final int TOKEN_NON = 3;

	/**
	  车辆状态出诊状态
	 */
	public static final int IN_VISIT = 1;
	public static final int END_VISIT = 2;

	/**
	 * App端调度情况车辆状态: 1-暂停调用 2-收到指令 3-驶向现场 4-到达现场 5-病人上车 6-到达医院 7-移交完成 8-途中待命 9-结束任务
	 */
	public static final int END_TASK = 9;
	public static final int PAUSE_CALL = 1;
	public static final int RECEIVE_ORDER = 2;
	public static final int GO_SCENE = 3;
	public static final int ARRIVE_SCENE = 4;
	public static final int ON_CAR = 5;
	public static final int ARRIVE_HOSPITAL = 6;
	public static final int TRANSFER_COMPLETE = 7;
	public static final int ON_WAY = 8;

	/**
	 * App端返回值
	 */
	public static final int REASON_NULL = 0;
	public static final int UPDATE_SUCCESS = 1;
	public static final int UPDATE_FAIL = 2;
	public static final int PATIENT_NULL = 3;
	public static final int PATIENT_NO_EXIST = 4;

	/**
	 * 急救记录状态(1-草稿 2-归档)
	 */
	public static final int DRAFT_STATE = 1;
	public static final int FORMAL_STATE = 2;

	/**
	 * 急救记录病历项目类别(1-辅助检查 2-药品 3-材料 4-治疗 5-救护)
	 */
	public static final String ASSIST_CHECK = "1";
	public static final String DRUG = "2";
	public static final String MATERIAL = "3";
	public static final String TREATMENT = "4";
	public static final String RESCUE = "5";

	/**
	 * 急救项目管理(类型：1救护，2检查，3治疗)
	 */
	public static final int ITEM_RESCUE = 1;
	public static final int ITEM_CHECK = 2;
	public static final int ITEM_TREATMENT = 3;


	/**
	 * 存放webscoket变量
	 */
	public static Map<Long, Session> WEBSOCKET_USER_MAP = new ConcurrentHashMap<Long, Session>();
	public static Map<Long, Map<Long, Session>> WEBSOCKET_ORG_MAP = new ConcurrentHashMap<Long, Map<Long, Session>>();

}
