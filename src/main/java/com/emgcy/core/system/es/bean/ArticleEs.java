//package com.emgcy.core.system.es.bean;
//
//import java.io.Serializable;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//
///**
// * 
// * @author chenshuzhuo
// * @date 2018-01-25
// */
//@Document(indexName="emgcy",type="es_article",refreshInterval="-1")
//public class ArticleEs implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * 主键ID
//	 */
//	@Id
//	private Long id;
//	/**
//	 * uuid
//	 */
//	private String uuid;
//	/**
//	 * 用户ID
//	 */
//	private Long userId;
//	/**
//	 * 医院id
//	 */
//	private Long orgHospitalId;
//	/**
//	 * 标题
//	 */
//	private String title;
//	/**
//	 * 图片文件MD5
//	 */
//	private String coverUuid;
//	/**
//	 * 概述
//	 */
//	private String summary;
//	/**
//	 * 分类uuid
//	 */
//	private String classifyUuid;
//	/**
//	 * 分类名称
//	 */
//	private String classifyName;
//	/**
//	 * 状态(0:草稿,1:审核中,2:发布失败,3:已发布)
//	 */
//	private Integer state;
//	/**
//	 * 标签
//	 */
//	private String label;
//	/**
//	 * 浏览数
//	 */
//	private Long browseNum;
//	/**
//	 * 点赞数
//	 */
//	private Long praiseNum;
//	/**
//	 * 收藏数
//	 */
//	private Long collectNum;
//	/**
//	 * 评论数
//	 */
//	private Long commentNum;
//
//	/**
//	 * 内容
//	 */
//	private String content;
//	/**
//	 * image路径
//	 */
//	private String imagePath;
//
//	/**
//	 * 推荐(1:首页,2:文章-收藏,3:文章-点赞)
//	 */
//	private String recommend;
//
//	/**
//	 * 
//	 * 创建人名称
//	 */
//	private String createName;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getUuid() {
//		return uuid;
//	}
//
//	public void setUuid(String uuid) {
//		this.uuid = uuid;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getCoverUuid() {
//		return coverUuid;
//	}
//
//	public void setCoverUuid(String coverUuid) {
//		this.coverUuid = coverUuid;
//	}
//
//	public String getSummary() {
//		return summary;
//	}
//
//	public void setSummary(String summary) {
//		this.summary = summary;
//	}
//
//	public String getClassifyUuid() {
//		return classifyUuid;
//	}
//
//	public void setClassifyUuid(String classifyUuid) {
//		this.classifyUuid = classifyUuid;
//	}
//
//	public String getClassifyName() {
//		return classifyName;
//	}
//
//	public void setClassifyName(String classifyName) {
//		this.classifyName = classifyName;
//	}
//
//	public Integer getState() {
//		return state;
//	}
//
//	public void setState(Integer state) {
//		this.state = state;
//	}
//
//	public String getLabel() {
//		return label;
//	}
//
//	public void setLabel(String label) {
//		this.label = label;
//	}
//
//	public Long getBrowseNum() {
//		return browseNum;
//	}
//
//	public void setBrowseNum(Long browseNum) {
//		this.browseNum = browseNum;
//	}
//
//	public Long getPraiseNum() {
//		return praiseNum;
//	}
//
//	public void setPraiseNum(Long praiseNum) {
//		this.praiseNum = praiseNum;
//	}
//
//	public Long getCollectNum() {
//		return collectNum;
//	}
//
//	public void setCollectNum(Long collectNum) {
//		this.collectNum = collectNum;
//	}
//
//	public Long getCommentNum() {
//		return commentNum;
//	}
//
//	public void setCommentNum(Long commentNum) {
//		this.commentNum = commentNum;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public String getImagePath() {
//		return imagePath;
//	}
//
//	public void setImagePath(String imagePath) {
//		this.imagePath = imagePath;
//	}
//
//	public String getRecommend() {
//		return recommend;
//	}
//
//	public void setRecommend(String recommend) {
//		this.recommend = recommend;
//	}
//
//	public String getCreateName() {
//		return createName;
//	}
//
//	public void setCreateName(String createName) {
//		this.createName = createName;
//	}
//
//	public Long getOrgHospitalId() {
//		return orgHospitalId;
//	}
//
//	public void setOrgHospitalId(Long orgHospitalId) {
//		this.orgHospitalId = orgHospitalId;
//	}
//
//	@Override
//	public String toString() {
//		return "ArticleEs [id=" + id + ", uuid=" + uuid + ", userId=" + userId + ", title=" + title + ", coverUuid="
//				+ coverUuid + ", summary=" + summary + ", classifyUuid=" + classifyUuid + ", classifyName="
//				+ classifyName + ", state=" + state + ", label=" + label + ", browseNum=" + browseNum + ", praiseNum="
//				+ praiseNum + ", collectNum=" + collectNum + ", commentNum=" + commentNum + ", content=" + content
//				+ ", imagePath=" + imagePath + ", recommend=" + recommend + ", createName=" + createName + "]";
//	}
//
//}
