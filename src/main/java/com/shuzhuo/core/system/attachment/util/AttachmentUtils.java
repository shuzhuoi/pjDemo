package com.shuzhuo.core.system.attachment.util;

import java.io.File;
import java.util.Date;

import com.shuzhuo.core.common.config.ApplicationYmlConfig;
import com.shuzhuo.core.common.util.DateUtil;
import com.shuzhuo.core.system.attachment.entity.Attachment;
import com.shuzhuo.core.system.attachment.mapper.AttachmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.util.SpringContextHolder;
import com.shuzhuo.core.common.util.StringUtil;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-16
 *
 */
public class AttachmentUtils {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentUtils.class);

	private static ApplicationYmlConfig appConfig = SpringContextHolder.getBean(ApplicationYmlConfig.class);
	
	private static AttachmentMapper attachmentDao = SpringContextHolder.getBean(AttachmentMapper.class);

	/**
	 * 获取小文件路径
	 * @param groupUuid 文件唯一标识
	 * @return
	 */
	public static String getSmallFilePath(String groupUuid){
		Attachment attachment = new Attachment();
		attachment.setGroupUuid(groupUuid);
		attachment = attachmentDao.selectOne(attachment);
		return attachment.getImagePath();
	}
	/**
	 * 获取小文件路径
	 * @param newFileName
	 * @param FileMd5
	 * @return
	 */
	public static String getSmallFilePath(String newFileName,String FileMd5,String path){
		return appConfig.getHostURI() + getSaveFileTypePath(newFileName,path).replaceAll("\\\\","/") +FileMd5 + getFileDotSuffix(newFileName);
	}

	/**
	 * 获取图片路径
	 * @param newFileName
	 * @param FileMd5
	 * @return
	 */
	public static String getPhotoPath(String newFileName,String FileMd5){
		return  getSaveFileTypePath(newFileName).replaceAll("\\\\","/")+ FileMd5 + getFileDotSuffix(newFileName);
	}
	

	/**
	 * 上传服务器文件的路径
	 * @return
	 */
	public static String getServerFileSavePath(){
		return appConfig.getResourceRootPath();
	}
	/**
	 * 获取文件后缀
	 * 
	 * <pre>
	 * AttachmentUtils.getSuffix("xxxxx.txt") = txt
	 * </pre>
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		return getFileSuffix(fileName, true);
	}

	/**
	 * 获取文件后缀
	 * 
	 * <pre>
	 * AttachmentUtils.getSuffix("xxxxx.txt")  = .txt
	 * </pre>
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileDotSuffix(String fileName) {
		return getFileSuffix(fileName, false);
	}
	
	/**
	 * 获取上传路径
	 * <pre>
	 * AttachmentUtils.getFileUploadPath("1.txt") = D:\ user\doc\20171206\
	 * </pre>
	 * @return 服务器路径+文件名称对应的文件夹+当前日期
	 */
	public static String getFileUploadPath(String fileName){
		return getServerFileSavePath() + File.separator + getSaveFileTypePath(fileName);
	}
	
	/**
	 * 获取上传路径
	 * <pre>
	 * 参数 attachment: {"path":"\\doc\\20171206"}
	 * AttachmentUtils.getFileUploadPath(attachment) = D:\ user\doc\20171206\
	 * </pre>
	 * @param attachment
	 * @return 服务器路径+path
	 */
	public static String getFileUploadPath(Attachment attachment) {
		return getFilePath(attachment, false);
	}

	/**
	 * 获取文件路径
	 * <pre>
	 * 参数：attachment: {"mdFive":"mdFive","name":"1.txt","path":"\\doc\\20171206"}
	 * 
	 * AttachmentUtils.getFileUploadPath(attachment) = D:\ user\doc\20171206\mdFive.txt
	 * </pre>
	 * @param attachment
	 * @return  服务器路径+path+mdFive+name的后缀
	 */
	public static String getFilePath(Attachment attachment) {
		return getFilePath(attachment, true);
	}
	
	/**
	 * 获取文件类型
	 * <pre>
	 * 根据文件返回文件类型
	 * (-1:错误，
	 * 1:文档,
	 * 2:压缩文件,
	 * 3:图片,
	 * 4:语音文件,
	 * 5:视频文件,
	 * 6:报表文件,
	 * 0:未知)
	 * </pre>
	 * @param fileName
	 * @return
	 */
	public static Integer getFileType(String fileName) {
		if(StringUtils.isBlank(fileName)){
			logger.warn("file name not is blank !");
			return null;
		}
		return StringUtil.getFileType(getFileSuffix(fileName));
	}
	
	/**
	 * 根据文件的名称获取相应保存的文件夹
	 * <pre>
	 * getSavePathByFileType("1.jpg")  = image
	 * getSavePathByFileType("2.png")  = image
	 * getSavePathByFileType("3.mp4")  = video
	 * getSavePathByFileType("4.avi")  = video
	 * getSavePathByFileType("xxxxx")  = other
	 * </pre>
	 * @param fileName
	 * @return
	 */
	public static String getSavePathByFileType(String fileName){
		if(StringUtils.isBlank(fileName)){
			logger.warn("file name not is blank !");
			return null;
		}
		return StringUtil.getRelativeSavePathByFileType(getFileType(fileName));
	}
	
	/**
	 * 根据文件的名称获取数据保存的文件类型路径
	 * <pre>
	 * 	getSaveFileTypePath("1.txt")  = \doc\20171206\
	 * </pre>
	 * @param fileName
	 * @return 文件名称对应的文件夹+当前日期
	 */
	public static String getSaveFileTypePath(String fileName){
		return  getSaveFileTypePath(fileName, null);
	}
	/**
	 * 根据文件的名称获取数据保存的文件类型路径
	 * <pre>
	 * 	getSaveFileTypePath("1.txt")  = \doc\20171206\
	 * </pre>
	 * @param fileName
	 * @return 文件名称对应的文件夹+当前日期
	 */
	public static String getSaveFileTypePath(String fileName,String path){
		if(StringUtils.isBlank(fileName)){
			logger.warn("file name not is blank !");
			return null;
		}
		
		if(StringUtils.isBlank(path)){
			logger.info("path is blank !");
			String relativeSavePathByFileType = StringUtil.getRelativeSavePathByFileType(getFileType(fileName));
			path=relativeSavePathByFileType+File.separator+ DateUtil.formatDate(new Date(), "yyyyMMdd");
		}
		return File.separator +path+ File.separator;
		
	}
	

	/**
	 * @param fileName
	 * @param flag
	 * @return
	 */
	private static String getFileSuffix(String fileName, boolean flag) {
		if (StringUtils.isBlank(fileName)) {
			logger.warn("file name not is blank !");
			return null;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			logger.warn("file name not suffix !");
			return null;
		}
		if (flag) {
			lastIndexOf++;
		}
		return fileName.substring(lastIndexOf).toLowerCase();
	}

	/**
	 * 
	 * @param attachment
	 * @param flag
	 * @return
	 */
	private static String getFilePath(Attachment attachment, boolean flag) {
		if (attachment == null){
			logger.warn("Attachment not is null !");
			return null;
		}
			
		String path = attachment.getPath();
		if(StringUtils.isBlank(path)){
			logger.warn("file path not is blank !");
			return null;
		}
		String filePath = getServerFileSavePath() + File.separator + path + File.separator;
		String logInfo = "文件上传路径:{}";
		if (flag) {
			
			String name = attachment.getName();
			if(StringUtils.isBlank(name)){
				logger.warn("file name not is blank !");
				return null;
			}
			
			String mdFive = attachment.getMdFive();
			if(StringUtils.isBlank(mdFive)){
				logger.warn("file mdFive not is blank !");
				return null;
			}
			
			filePath = filePath + mdFive + getFileDotSuffix(name);
			logInfo = "文件路径:{}";
		}
		if (logger.isInfoEnabled())
			logger.info(logInfo, filePath);
		return filePath;
	}
	
	/**
	 * 获取大文件的路径
	 * @param fileName
	 * @return 服务器路径+bigFileFolder+分类路径
	 */
	public static String getBigFilePath(String fileName){
		if(StringUtils.isBlank(fileName)){
			logger.warn("file name not is blank !");
			return null;
		}
		String relativeSavePathByFileType = StringUtil.getRelativeSavePathByFileType(getFileType(fileName));
		return getServerFileSavePath()+File.separator+Constant.BIGFILEFOLDER+File.separator+relativeSavePathByFileType;
	}
	
	/**
	 * 获取大文件的路径
	 * @param fileName
	 * @param mdfive
	 * @return 服务器路径+bigFileFolder+分类路径+mdfive+suffix
	 */
	public static String getBigFilePath(String fileName,String mdfive){
		if(StringUtils.isBlank(mdfive)){
			logger.warn("file mdfive not is blank !");
			return null;
		}
		return getBigFilePath(fileName)+File.separator+getFileDotSuffix(fileName);
	}
	
	/**
	 * 获取大文件的nginx路径
	 * @param fileName
	 * @param mdfive
	 * @return 服务器路径+bigFileFolder+分类路径+mdfive+suffix
	 */
	public static String getBigFileNginxPath(String fileName,String mdfive){
		if(StringUtils.isBlank(mdfive)){
			logger.warn("file mdfive not is blank !");
			return null;
		}
		if(StringUtils.isBlank(fileName)){
			logger.warn("file name not is blank !");
			return null;
		}
		String relativeSavePathByFileType = StringUtil.getRelativeSavePathByFileType(getFileType(fileName));
		return appConfig.getHostURI()+File.separator+Constant.BIGFILEFOLDER+File.separator+relativeSavePathByFileType+File.separator+mdfive+getFileDotSuffix(fileName);
	}
	
	/**
	 * 获取大文件的路径
	 * @param Attachment
	 * @return 服务器路径+bigFileFolder+分类路径+mdfive+suffix
	 */
	public static String getBigFilePath(Attachment attachment){
		return getBigFilePath(attachment.getName(),attachment.getMdFive());
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefix(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			logger.warn("file name not is blank !");
			return null;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			logger.warn("file name not suffix !");
			return null;
		}
		return fileName.substring(0,lastIndexOf);
	}
	
//	public static void main(String[] args) {
//		String fileName="1.txt";
//				
//		Attachment attachment = new Attachment();
//		attachment.setName(fileName);
//		attachment.setPath("\\doc\\20171206");
//		attachment.setMdFive("mdFive");
//		System.out.println(AttachmentUtils.getFileDotSuffix(fileName));
//		System.out.println(AttachmentUtils.getFilePath(attachment));
//		System.out.println(AttachmentUtils.getFileSuffix(fileName));
//		
//		System.out.println("------------------------------");
//		System.out.println(AttachmentUtils.getFileUploadPath(attachment));
//		System.out.println(AttachmentUtils.getFileUploadPath(fileName));
//		
//		System.out.println("------------------------------");
//		
//		System.out.println(AttachmentUtils.getSaveFileTypePath(fileName));
//		System.out.println(AttachmentUtils.getSavePathByFileType(fileName));
//		System.out.println(AttachmentUtils.getFileType(fileName));
//		
//	}
	

}
