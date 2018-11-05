package com.shuzhuo.core.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-24
 */
@Component
@ConfigurationProperties("application")
public class ApplicationYmlConfig {

	/**
	 * 上传文件的路径
	 */
	@Value("${application.resourceRootPath}")
    private String resourceRootPath;
	
	/**
	 * mybaitsPlus xml动态刷新(只允许开发环境开启)
	 */
	@Value("${application.mapperRefresh}")
    private boolean mapperRefresh;
    
    /**
	 * token过期时间
	 */
	@Value("${application.tokenExpireTime}")
	private int tokenExpireTime;

	/**
	 * nginx配置路径
	 */
	@Value("${application.hostURI}")
	private String hostURI;
	
	/**
	 * 服务器路径
	 */
	@Value("${application.resourceHostPort}")
	private String resourceHostPort;
	
	/**
	 * 科室excel模板路径
	 */
	@Value("${application.excelDeptTemplate}")
	private String excelDeptTemplate;
	
	/**
	 * 用户excel模板路径
	 */
	@Value("${application.excelUserTemplate}")
	private String excelUserTemplate;
	
	/**
	 * 急救项目excel模板路径
	 */
	@Value("${application.excelItemTemplate}")
	private String excelItemTemplate;
	
	/**
	 * 急救药品excel模板路径
	 */
	@Value("${application.excelDrugTemplate}")
	private String excelDrugTemplate;
	
	/**
	 * 急救材料excel模板路径
	 */
	@Value("${application.excelMaterialTemplate}")
	private String excelMaterialTemplate;
	
	/**
	 * 人员排班excel模板路径
	 */
	@Value("${application.excelScheduleTemplate}")
	private String excelScheduleTemplate;
	
	/**
	 * 等待出诊时间(毫秒)
	 */
	@Value("${application.waitVisitTime}")
	private long waitVisitTime; 

	public String getResourceRootPath() {
		return resourceRootPath;
	}

	public boolean isMapperRefresh() {
		return mapperRefresh;
	}

	public String getHostURI() {
		return hostURI;
	}

	public int getTokenExpireTime() {
		return tokenExpireTime;
	}

	public String getResourceHostPort() {
		return resourceHostPort;
	}

	public String getExcelDeptTemplate() {
		return excelDeptTemplate;
	}

	public String getExcelUserTemplate() {
		return excelUserTemplate;
	}

	public String getExcelItemTemplate() {
		return excelItemTemplate;
	}

	public String getExcelDrugTemplate() {
		return excelDrugTemplate;
	}

	public String getExcelMaterialTemplate() {
		return excelMaterialTemplate;
	}

	public long getWaitVisitTime() {
		return waitVisitTime;
	}

	public String getExcelScheduleTemplate() {
		return excelScheduleTemplate;
	}
	
}
