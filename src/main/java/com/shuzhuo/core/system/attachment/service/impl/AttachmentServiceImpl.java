package com.shuzhuo.core.system.attachment.service.impl;

import java.io.File;
import java.util.List;

import com.shuzhuo.core.system.attachment.entity.Attachment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.common.shiro.ShiroUserUtil;
import com.shuzhuo.core.common.util.MD5Util;
import com.shuzhuo.core.common.util.UUIDGenerator;
import com.shuzhuo.core.system.attachment.mapper.AttachmentMapper;
import com.shuzhuo.core.system.attachment.service.IAttachmentService;
import com.shuzhuo.core.system.attachment.util.AttachmentUtils;

/**
 * <p>
 * 附件表 服务实现类
 * </p>
 *
 * @author chenshuzhuo
 * @since 2017-11-20
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	private AttachmentMapper attachmentDao;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public BaseMsg saveFile(MultipartFile multipartFile, String groupUuid, String newFileName, String remark)
			throws Exception {

		Attachment attachment = new Attachment();
		if (StringUtils.isNotBlank(groupUuid)) {
			attachment.setGroupUuid(groupUuid);
			Wrapper<Attachment> wrapper = new EntityWrapper<Attachment>(attachment);
			Integer selectCount = attachmentDao.selectCount(wrapper);
			if ( selectCount == null || selectCount==0) {
				if (logger.isInfoEnabled())
					logger.info("该文件分组不存在! classifyUuid:{}", groupUuid);
				return BaseMsg.errorMsg("该文件分组不存在!");
			}
		}else{
			attachment.setGroupUuid(UUIDGenerator.generate());
		}

		// 保存文件
		if (StringUtils.isBlank(newFileName))
			newFileName = multipartFile.getOriginalFilename();
		String filePath = AttachmentUtils.getFileUploadPath(newFileName);
		if (logger.isInfoEnabled())
			logger.info("上传文件路径:{}", filePath);
		File file = new File(filePath);
		if (!file.exists())
			file.mkdirs();

		File tempFile = new File(filePath + File.separator + newFileName);
		multipartFile.transferTo(tempFile);
		String FileMd5 = MD5Util.getMD5(tempFile);

		String suffix = AttachmentUtils.getFileDotSuffix(newFileName);
		String saveFile = filePath + File.separator + FileMd5 + suffix;
		File newFile = new File(saveFile);
		
		Attachment selectByGroupUuidOnly = selectByGroupUuidOnly(attachment.getGroupUuid(),FileMd5);
		if (selectByGroupUuidOnly != null) {
			if (logger.isInfoEnabled())
				logger.info("该文件:" + newFileName + " 已经存在! groupUuid:"+attachment.getGroupUuid()+" Mdfive:{}", FileMd5);
			tempFile.delete();
			return BaseMsg.errorMsg("文件已存在!");
		}

		if (!newFile.exists())
			tempFile.renameTo(newFile);
		
		// 保存数据库
		attachment.setName(newFileName);
		attachment.setMdFive(FileMd5);
		attachment.setFileType(AttachmentUtils.getFileType(newFileName));
		attachment.setPath(AttachmentUtils.getSaveFileTypePath(newFileName));
		attachment.setSize(multipartFile.getSize());
		attachment.setRemark(remark);
		Integer insert = attachmentDao.insert(attachment);
		if (insert > 0) {
			if (logger.isInfoEnabled())
				logger.info("上传文件:{}完成", newFileName);
		} else {
			if (logger.isInfoEnabled())
				logger.info("上传文件:失败");
		}

		return BaseMsg.successData(attachment);
	}

	@Override
	public BaseMsg saveFile(MultipartFile multipartFile, String newFileName, String remark) throws Exception {
		return saveFile(multipartFile, null, newFileName, remark);
	}

	@Override
	public BaseMsg saveFile(MultipartFile multipartFile) throws Exception {
		return saveFile(multipartFile, null, null, null);
	}

	@Override
	public List<Attachment> selectByMdFiveList(List<String> mdFiveList) {
		return attachmentDao.selectByMdFiveList(mdFiveList,ShiroUserUtil.getCurrentUser().getId());
	}

	@Override
	public String getPathByGroupUuid(String groupUuid) {
		Attachment selectByGroupUuidOne = selectByGroupUuidOne(groupUuid);
		if (selectByGroupUuidOne != null){
			if (selectByGroupUuidOne.getFileType() == 5)
				return selectByGroupUuidOne.getVideoPath();
			return selectByGroupUuidOne.getImagePath();
		}else {
			return null;
		}
	}

	@Override
	public List<Attachment> selectByGroupUuidList(String groupUuid) {
		if (StringUtils.isBlank(groupUuid))
			return null;
		Attachment attachment = new Attachment();
		attachment.setGroupUuid(groupUuid);
		Wrapper<Attachment> wrapper=new EntityWrapper<Attachment>(attachment);
		return  attachmentDao.selectList(wrapper);
		
	}
	
	@Override
	public Attachment selectByGroupUuidOne(String groupUuid) {
		List<Attachment> selectList = selectByGroupUuidList(groupUuid);
		if (!CollectionUtils.isEmpty(selectList)) {
			return selectList.get(0);
		}
		return null;
		
	}
	
	@Override
	public Attachment selectByGroupUuidOnly(String groupUuid, String fileMd5) {
		if (StringUtils.isBlank(groupUuid)||StringUtils.isBlank(fileMd5))
			return null;
		Attachment attachment = new Attachment();
		attachment.setGroupUuid(groupUuid);
		attachment.setMdFive(fileMd5);
		return attachmentDao.selectOne(attachment);
	}

	@Override
	public boolean checkFileExist(String groupUuid) {
		if (StringUtils.isBlank(groupUuid))
		    return false;
		if(CollectionUtils.isEmpty(selectByGroupUuidList(groupUuid)))
			return false;
		return true;
	}


}
