package com.shuzhuo.core.system.attachment.web;

import com.shuzhuo.core.common.base.BaseMsg;
import com.shuzhuo.core.common.util.UUIDGenerator;
import com.shuzhuo.core.system.attachment.bean.BaiduUploaderBean;
import com.shuzhuo.core.system.attachment.conf.AttachmentConstant;
import com.shuzhuo.core.system.attachment.entity.Attachment;
import com.shuzhuo.core.system.attachment.service.IAttachmentService;
import com.shuzhuo.core.system.attachment.util.AttachmentUtils;
import com.shuzhuo.core.system.base.AbstractController;
import com.shuzhuo.core.system.base.BasePageDataDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author chenshuzhuo
 * @date 2017-11-20
 * @since 2.0
 */
@Api(tags = "附件表类")
@Controller
@Validated
@RequestMapping("/attachment")
public class AttachmentController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	@Autowired
	private IAttachmentService attachmentService;


	@PostMapping("/uploadMultiFile")
	@ApiOperation(value = "上传多个文件接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "fileName", value = "新文件名", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "文件备注", paramType = "query"),
			@ApiImplicitParam(name = "groupUuid", value = "文件分组uuid", paramType = "query",dataType="string"),
			@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public List<BaseMsg> uploadFileArr(
			@ApiParam(name = "file", value = "上传文件") @RequestParam("file") @NotEmpty MultipartFile multipartFile[],
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false) String remark,@RequestParam(required = false) String classifyUuid)
			throws Exception {
		long startTime = 0;
		if (logger.isInfoEnabled()) {
			logger.info("文件上传开始");
			startTime = System.currentTimeMillis();
		}
		List<BaseMsg> resultList = new ArrayList<BaseMsg>();
		for (int i = 0; i < multipartFile.length; i++) {
			MultipartFile file = multipartFile[i];
			if (!file.isEmpty()) {
				String realFileName = file.getOriginalFilename();
				String suffix = AttachmentUtils.getFileDotSuffix(realFileName);

				if(suffix==null){
					resultList.add(BaseMsg.errorMsg("文件没有后缀!"));
					continue;
				}
				if (StringUtils.isNotBlank(fileName)) {
					fileName = fileName + suffix;
				} else {
					fileName = realFileName;
				}
				
				if (logger.isInfoEnabled())
					logger.info("开始上传文件{}", fileName);
				BaseMsg baseMsg = attachmentService.saveFile(file,classifyUuid, fileName, remark);
				resultList.add(baseMsg);
			}
		}
		if (logger.isInfoEnabled()) {
			long endTime = System.currentTimeMillis();
			logger.info("文件上传结束.共上传时间:{}", endTime - startTime);
		}

		return resultList;

	}

	@PostMapping("/uploadFile")
	@ApiOperation(value = "上传单个文件接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "fileName", value = "新文件名", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "文件备注", paramType = "query"),
			@ApiImplicitParam(name = "groupUuid", value = "文件分组uuid", paramType = "query",dataType="string"),
			@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public BaseMsg uploadFile(
			@ApiParam(name = "file", value = "上传文件类型:file,blob") @RequestParam(value = "file") MultipartFile multipartFile,
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(required = false) String remark,
			@RequestParam(required = false) String groupUuid) throws Exception {
		MultipartFile[] multipartFileArr = { multipartFile };
		return uploadFileArr(multipartFileArr, fileName, remark,groupUuid).get(0);
	}

	@GetMapping("/downFile")
	@ApiOperation(value = "下载单个文件接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "文件id", paramType = "query"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public BaseMsg downFile(
			@RequestParam long id,
			HttpServletRequest request, HttpServletResponse response) {
		Attachment attachment = attachmentService.selectById(id);
		if (attachment == null) {
			return BaseMsg.errorMsg("文件不存在!");
		}

		String filePath = AttachmentUtils.getFilePath(attachment);
		File serverFile = new File(filePath);
		if (!serverFile.exists()) {
			return BaseMsg.errorMsg("服务器文件不存在!");
		}

		String contentType = AttachmentConstant.mediaTypeMapper.get(AttachmentUtils.getFileDotSuffix(attachment.getName()).toLowerCase());
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		int skip = 0;
		try {
			String Range = request.getHeader("Range").replaceAll("byte=", "").replaceAll("-", "");
			skip = StringUtils.isNumeric(Range) ? Integer.valueOf(Range) : 0;
		} catch (Exception e) {
			logger.warn("断点下载异常,原因:{}", e.getMessage());
		}

		long fileSize = attachment.getSize();
		response.setContentLengthLong(fileSize);
		response.setContentType(contentType);

		try {
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode(attachment.getName(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(serverFile));
				BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream())) {
			if (skip > 0) {
				is.skip(skip);
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				String contentRange = new StringBuffer("bytes ").append(skip).append("-").append(fileSize - 1)
						.append("/").append(fileSize).toString();
				response.setHeader("Content-Range", contentRange);
			}
			byte b[] = new byte[1024 * 10];
			while (is.read(b) != -1) {
				os.write(b);
			}
			os.flush();
		} catch (IOException e) {
			logger.warn("下载文件出错，原因:{}", e.getMessage());
		}
		return BaseMsg.success();
	}

	@GetMapping("/downMultiFile")
	@ApiOperation(value = "下载多个文件接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public List<BaseMsg> downMultiFile(@RequestBody List<Long> idList,HttpServletRequest request, HttpServletResponse response) {
		List<BaseMsg> resultMsg = new ArrayList<BaseMsg>();
		List<Attachment> attachmentList=attachmentService.selectBatchIds(idList);
		if (attachmentList.isEmpty()) {
			resultMsg.add(BaseMsg.errorMsg("文件MD5列表不存在!"));
			return resultMsg;
		}

		byte[] buffer = new byte[1024];
		String format = String.format("%03d", new Random().nextInt(999));
		String zipName = System.nanoTime() + format + ".zip";
		ZipOutputStream out = null;
		FileInputStream fis = null;
		int index = 0;
		boolean flag = true;
		String encoding = "GBK";
		BaseMsg baseMsg = null;
		String filePath = null;
		Attachment attachment=null;
		File file=null;
		try {
			for (int i = 0; i < attachmentList.size(); i++) {
				attachment = attachmentList.get(i);
				filePath = AttachmentUtils.getFilePath(attachment);
				file = new File(filePath);
				if (!file.exists()) {
					baseMsg = BaseMsg.errorMsg("服务器文件不存在!");
					baseMsg.put("MD5", attachment.getMdFive());
					resultMsg.add(baseMsg);
					index++;
					if (index == attachmentList.size()) {
						return resultMsg;
					}
					continue;
				}
				if (flag) {
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
					flag = false;
				}
				out = new ZipOutputStream(response.getOutputStream());
				out.setEncoding(encoding);
				fis = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				out.setEncoding(encoding);
				int len;
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
				response.flushBuffer();
			}
		} catch (IOException e) {
			resultMsg.add(BaseMsg.errorMsg("文件下载出错!!"));
			return resultMsg;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return resultMsg;
	}

	@ApiOperation(value = "获取分组的附件列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "groupUuid", value = "分组uuid", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@GetMapping("/list")
	@ResponseBody
	public BasePageDataDTO<Attachment> currentUserlist(HttpServletRequest request, @RequestParam String groupUuid) {
		//分页
		Page<Attachment> currentPage = buildPage(request);
		Attachment attachment = new Attachment();
		attachment.setGroupUuid(groupUuid);
		Wrapper<Attachment> wrapper = new EntityWrapper<Attachment>(attachment);
		//构造查询条件
		buildSearch(request,wrapper);
		//排序
		buildOrder(request,wrapper);
		Page<Attachment> selectPage = attachmentService.selectPage(currentPage, wrapper);
		return new BasePageDataDTO<Attachment>(selectPage.getRecords(), selectPage.getTotal());
	}
	

	@ApiOperation(value = "根据id获取信息")
	@GetMapping("/getInfo")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public BaseMsg getInfo(@RequestParam long id) {
		Attachment attachment = attachmentService.selectById(id);
		return BaseMsg.successData(attachment);
	}
	
	@ApiOperation(value = "根据id删除信息")
	@GetMapping("/del")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@ResponseBody
	public BaseMsg del(@RequestParam long id) {
		if(attachmentService.deleteById(id))
			return BaseMsg.successMsg("删除成功!");
		return BaseMsg.errorMsg("删除失败!");
		
	}
	
	
	@ApiOperation(value = "Baidu WebUploader上传大文件接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "groupUuid", value = "分组uuid", paramType = "query",dataType="string"),
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/uploadBigFile")
	@ResponseBody
	public BaseMsg uploadBigFile(@RequestParam(value="file") MultipartFile multipartFile,@Validated(BaiduUploaderBean.UploadBigFile.class) BaiduUploaderBean bean,
			@RequestParam(required=false) String groupUuid) {
		if(multipartFile.isEmpty()){
			return BaseMsg.error();
		}
		
		String filePath=AttachmentUtils.getBigFilePath(bean.getName())+File.separator+bean.getFileMd5()+File.separator;
		File saveFile = new File(filePath);
		if(!saveFile.exists()){
			saveFile.mkdirs();
		}
		saveFile=new File(filePath+File.separator+bean.getChunk());
		
		try {
			multipartFile.transferTo(saveFile);
			return BaseMsg.success();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return BaseMsg.error();
	}
	
	@ApiOperation(value = "Baidu WebUploader上传大文件检查接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/uploadCheck")
	@ResponseBody
	public BaseMsg uploadCheck(@Validated(BaiduUploaderBean.UploadCheck.class) BaiduUploaderBean bean){
		String savePath =AttachmentUtils.getBigFilePath(bean.getName())+File.separator+bean.getFileMd5();
		File checkFile = new File(savePath+AttachmentUtils.getFileDotSuffix(bean.getName()));
		if(checkFile.exists()){
			return BaseMsg.success();
		}
		checkFile = new File(savePath+File.separator+bean.getChunk());
		if(checkFile.exists() && checkFile.length()==bean.getChunkSize().longValue()){
			return BaseMsg.success();
		}
		return BaseMsg.error();
	}
	
	@ApiOperation(value = "Baidu WebUploader上传大文件处理接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="header")})
	@PostMapping("/uploadHandler")
	@ResponseBody
	public BaseMsg uploadHandler(@Validated(BaiduUploaderBean.UploadHandler.class) BaiduUploaderBean bean, @RequestParam(required=false) String groupUuid){
		if (logger.isInfoEnabled())
			logger.info("参数:{}", bean,groupUuid);
		String savePath=AttachmentUtils.getBigFilePath(bean.getName())+File.separator+bean.getFileMd5();
		File tempFile = new File(savePath);
		File outputFile = new File(savePath+AttachmentUtils.getFileDotSuffix(bean.getName()));
		
		boolean flag=false;
		if(tempFile.exists()){
			if(tempFile.listFiles().length==0){
				logger.warn("Folder is empty:{}" ,tempFile);
			    return BaseMsg.errorMsg("请先上传完文件");
			}
		}else{
			if(!outputFile.exists()){
				logger.warn("temp file is empty:{}" ,tempFile);
			    return BaseMsg.errorMsg("请先上传完文件");
			}
			flag=true;
		}

		Attachment attachment = new Attachment();
		attachment.setName(bean.getName());
		attachment.setFileType(AttachmentUtils.getFileType(bean.getName()));
		attachment.setPath(AttachmentUtils.getSaveFileTypePath(bean.getName()));
		attachment.setMdFive(bean.getFileMd5());
		attachment.setSize(bean.getSize());
		if (StringUtils.isBlank(groupUuid)) {
			groupUuid= UUIDGenerator.generate();
		}
		attachment.setGroupUuid(groupUuid);
		attachment.setRemark(bean.getRemark());
		
		if(!attachmentService.insert(attachment)){
			if (logger.isInfoEnabled())
				logger.warn("保存数据库失败:{}",attachment);
			return BaseMsg.errorMsg("保存失败!");
		}
		
		if(flag){
			 if (logger.isInfoEnabled())
					logger.info("秒传文件:{} 完成:"+savePath, bean.getName());
			return BaseMsg.successData(attachment);
		}
		File[] fileArray = tempFile.listFiles(new FileFilter(){
			@Override
			public boolean accept(File fName) {
				if(fName.isDirectory()){
					return false;
				}
				return true;
			}
		});
		List<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				if(Integer.parseInt(file1.getName()) < Integer.parseInt(file2.getName()))
					return -1;
				return 1;
			}
				
		});
		
		FileInputStream fis=null;
		FileChannel inChannel=null;
		FileChannel outChannel=null;
		FileOutputStream fos=null;
		 try {
			 outputFile.createNewFile();
			 fos = new FileOutputStream(outputFile);
	         outChannel = fos.getChannel();
	         for(File file : fileList){
	        	 fis = new FileInputStream(file);
	             inChannel = fis.getChannel();
	             inChannel.transferTo(0, inChannel.size(), outChannel);
	             inChannel.close();
	             fis.close();
	             file.delete();
	         }
	         if (logger.isInfoEnabled())
					logger.info("上传文件:{} 完成", bean.getName());
	         
	         if(tempFile.isDirectory() && tempFile.exists()){
	        	 tempFile.delete();
	         }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inChannel!=null){
				try {
					inChannel.close();
				} catch (IOException e) {
				}
			}
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if(outChannel!=null){
				try {
					outChannel.close();
				} catch (IOException e) {
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			
		}
		
		return BaseMsg.successData(attachment);
		
	}
	
	//TODO 待修改多线程下载
	@GetMapping("/downBigFile")
	@ApiOperation(value = "临时下载大文件接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "文件id", paramType = "query") })
	@ResponseBody
	public BaseMsg downBigFile(
			@RequestParam  long id,
			HttpServletRequest request, HttpServletResponse response) {
		Attachment attachment = attachmentService.selectById(id);
		if (attachment == null) {
			return BaseMsg.errorMsg("文件不存在!");
		}

		String savePath=AttachmentUtils.getBigFilePath(attachment.getName())+File.separator+attachment.getMdFive()+AttachmentUtils.getFileDotSuffix(attachment.getName());
		File serverFile = new File(savePath);
		if (!serverFile.exists()) {
			return BaseMsg.errorMsg("服务器文件不存在!");
		}

		String contentType = AttachmentConstant.mediaTypeMapper.get(AttachmentUtils.getFileDotSuffix(attachment.getName()).toLowerCase());
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		int skip = 0;
		try {
			String Range = request.getHeader("Range").replaceAll("byte=", "").replaceAll("-", "");
			skip = StringUtils.isNumeric(Range) ? Integer.valueOf(Range) : 0;
		} catch (Exception e) {
			logger.warn("断点下载异常,原因:{}", e.getMessage());
		}

		long fileSize = attachment.getSize();
		response.setContentLengthLong(fileSize);
		response.setContentType(contentType);

		try {
			response.setHeader("Content-disposition",
					"attachment;filename=" + URLEncoder.encode(attachment.getName(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(serverFile));
				BufferedOutputStream os = new BufferedOutputStream(response.getOutputStream())) {
			if (skip > 0) {
				is.skip(skip);
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				String contentRange = new StringBuffer("bytes ").append(skip).append("-").append(fileSize - 1)
						.append("/").append(fileSize).toString();
				response.setHeader("Content-Range", contentRange);
			}
			byte b[] = new byte[1024 * 10];
			while (is.read(b) != -1) {
				os.write(b);
			}
			os.flush();
		} catch (IOException e) {
			logger.warn("下载文件出错，原因:{}", e.getMessage());
		}
		return BaseMsg.success();
	}

	

}
