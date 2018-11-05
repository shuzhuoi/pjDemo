package com.emgcy.core.system.attachment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.system.attachment.entity.Attachment;

/**
 * <p>
 * 附件表 服务类
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-20
 */
public interface IAttachmentService extends IService<Attachment> {

	
	/**
	 * 
	 * @param multipartFile 上传文件
	 * @return
	 * @throws Exception
	 */
	BaseMsg saveFile(MultipartFile multipartFile) throws Exception;
	
	/**
	 * 
	 * @param multipartFile 上传文件
	 * @param newFileName 新文件名称
	 * @param remark 文件备注
	 * @return
	 * @throws Exception
	 */
	BaseMsg saveFile(MultipartFile multipartFile,String newFileName, String remark) throws Exception;
	
	/**
	 * 
	 * @param multipartFile 上传文件
	 * @param groupUuid 分组uuid
	 * @param newFileName 新文件名称 
	 * @param remark 文件备注
	 * @return
	 * @throws Exception
	 */
	BaseMsg saveFile(MultipartFile multipartFile,String groupUuid, String newFileName, String remark) throws Exception;

	/**
	 * 根据md5列表查询文件列表
	 * @param mdFiveList
	 * @return
	 */
	List<Attachment> selectByMdFiveList(List<String> mdFiveList);
	
	/**
	 * 根据GroupUuid 获取小文件路径
	 * @param mdfive
	 * @return
	 */
	String getPathByGroupUuid(String groupUuid);
	
	/**
	 *根据groupUuid 查询一条文件 
	 *@param groupUuid
	 */
	Attachment selectByGroupUuidOne(String groupUuid);
	
	/**
	 * 根据groupUuid 和md5 确定唯一一条记录
	 * @param groupUuid
	 * @param fileMd5
	 * @return
	 */
	Attachment selectByGroupUuidOnly(String groupUuid, String fileMd5);
	
	/**
	 * 根据groupUuid 查询 文件
	 * @param groupUuid
	 * @return
	 */
	List<Attachment> selectByGroupUuidList(String groupUuid);
	
	/**
	 * 检查当前用户的是否上传了文件(true:存在,false:不存在)
	 * @param mdfive
	 * @param groupUuid
	 * @return
	 */
	boolean checkFileExist(String groupUuid);


}
