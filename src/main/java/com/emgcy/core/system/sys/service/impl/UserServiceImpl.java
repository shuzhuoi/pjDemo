package com.emgcy.core.system.sys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.mapper.BeanMapper;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.common.config.ApplicationYmlConfig;
import com.emgcy.core.common.excel.dto.ExcelLogDetailDTO;
import com.emgcy.core.common.excel.dto.ImportExcelLogDTO;
import com.emgcy.core.common.excel.dto.UserExcelDTO;
import com.emgcy.core.common.excel.util.MyMockMultipartFile;
import com.emgcy.core.common.shiro.ShiroUserUtil;
import com.emgcy.core.common.util.ReflectionKit;
import com.emgcy.core.common.util.TokenGenerator;
import com.emgcy.core.common.util.UUIDGenerator;
import com.emgcy.core.system.attachment.entity.Attachment;
import com.emgcy.core.system.attachment.service.IAttachmentService;
import com.emgcy.core.system.attachment.util.AttachmentUtils;
import com.emgcy.core.system.sys.entity.User;
import com.emgcy.core.system.sys.entity.UserAttachment;
import com.emgcy.core.system.sys.entity.UserRole;
import com.emgcy.core.system.sys.entity.UserTrain;
import com.emgcy.core.system.sys.mapper.UserAttachmentMapper;
import com.emgcy.core.system.sys.mapper.UserMapper;
import com.emgcy.core.system.sys.mapper.UserRoleMapper;
import com.emgcy.core.system.sys.mapper.UserTrainMapper;
import com.emgcy.core.system.sys.service.IUserService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userDao;
	@Autowired
	private UserRoleMapper userRoleDao;
	@Autowired
	private UserAttachmentMapper userAttachmentDao;
	@Autowired
	private UserTrainMapper userTrainDao;
	@Autowired
	private IAttachmentService attachmentService;
	@Autowired
	private ApplicationYmlConfig config;
	
	@Override
//	@MyUserFilter(tableAlias = "u", user = true)
	public Page<User> queryList(Page<User> page, Wrapper<User> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		page.setRecords(userDao.queryList(page, wrapper));
		return page;
	}

	/**
	 * 查询用户列表(不分页)
	 */
	@Override
	public List<User> queryList(Wrapper<User> wrapper) {
		wrapper.in("user_type", new Integer[]{1, 3});
		return userDao.queryList(wrapper);
	}

	@Override
	public boolean isUserExist(User user) {
		//医院ID和员工号判断用户是否存在
		if (user.getOrgHospitalId()!=null&&StringUtils.isNotBlank(user.getEmployeeId())) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("org_hospital_id", user.getOrgHospitalId());
			map.put("employee_id", user.getEmployeeId());
			List<User> lists = userDao.selectByMap(map);
			if (lists!=null&&lists.size()>0) {
				return true;
			}
		}
		//手机号判断用户是否存在
		if (StringUtils.isNotBlank(user.getPhone())) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("phone", user.getPhone());
			List<User> lists = userDao.selectByMap(map);
			if (lists!=null&&lists.size()>0) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean insertUser(User user, Integer userType) {
		user.setUuid(UUIDGenerator.generate());
		user.setOrgHospitalId(ShiroUserUtil.getHospitalId());
		//sha256加密(初始密码暂时设为123456)
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(TokenGenerator.entryptPassword("123456", salt));
		user.setSignPassword(TokenGenerator.entryptPassword("123456", salt));
		user.setSalt(salt);
		user.setUserType(userType);
		//新增用户的状态暂时设为可用
		user.setStatus(1);
		user.setStaffStatus(1);
		Integer count = userDao.insert(user);
		if(count!=null && count>0){
			//医师资格证
			if (StringUtils.isNotBlank(user.getQualification())) {
				UserAttachment attach = new UserAttachment();
				attach.setUserId(user.getId());
				attach.setAttachMd5(user.getQualification());
				attach.setAttachType(2);
				userAttachmentDao.insert(attach);
			}
			//医师执业证
			if (StringUtils.isNotBlank(user.getPractice())) {
				UserAttachment attach = new UserAttachment();
				attach.setUserId(user.getId());
				attach.setAttachMd5(user.getPractice());
				attach.setAttachType(3);
				userAttachmentDao.insert(attach);
			}
			//培训教育
			if (user.getTrainList()!=null&&user.getTrainList().size()>0) {
				for(UserTrain list:user.getTrainList()){
					list.setUserId(user.getId());
					list.setStatus(4);
					userTrainDao.insert(list);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean updateUser(User user) {
		User bean = userDao.selectById(user.getId());
		
		String[] notNullPropertyNames = ReflectionKit.getNotNullPropertyNames(user);
        BeanUtils.copyProperties(bean, user, notNullPropertyNames);
        
        Integer count = userDao.updateAllColumnById(user);
        if(count!=null && count>0){
        	//医师资格证
        	if (user.getQualification()!=null) {
        		//先删除用户的医师资格证
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("user_id", user.getId());
    			map.put("attach_type", 2);
        		userAttachmentDao.deleteByMap(map);
        		//不是空字符串则保存医师资格证
        		if (!"".equals(user.getQualification())) {
    				UserAttachment attach = new UserAttachment();
    				attach.setUserId(user.getId());
    				attach.setAttachMd5(user.getQualification());
    				attach.setAttachType(2);
    				userAttachmentDao.insert(attach);
    			}
			}
        	//医师执业证
        	if (user.getPractice()!=null) {
        		//先删除用户的医师执业证
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("user_id", user.getId());
    			map.put("attach_type", 3);
        		userAttachmentDao.deleteByMap(map);
        		//不是空字符串则保存医师执业证
        		if (!"".equals(user.getPractice())) {
    				UserAttachment attach = new UserAttachment();
    				attach.setUserId(user.getId());
    				attach.setAttachMd5(user.getPractice());
    				attach.setAttachType(3);
    				userAttachmentDao.insert(attach);
    			}
			}
        	//培训教育
        	//先删除用户的培训教育列表
        	Map<String,Object> map = new HashMap<String,Object>();
			map.put("user_id", user.getId());
			userTrainDao.deleteByMap(map);
			
			if (user.getTrainList()!=null&&user.getTrainList().size()>0) {
				for(UserTrain list:user.getTrainList()){
					list.setUserId(user.getId());
					list.setStatus(4);
					userTrainDao.insert(list);
				}
			}
			return true;
        }
		return false;
	}
	
	@Override
	@Transactional
	public boolean updateUserNoTrain(User user) {
		User bean = userDao.selectById(user.getId());
		
		String[] notNullPropertyNames = ReflectionKit.getNotNullPropertyNames(user);
        BeanUtils.copyProperties(bean, user, notNullPropertyNames);
        
        Integer count = userDao.updateAllColumnById(user);
        if(count!=null && count>0){
        	//医师资格证
        	if (user.getQualification()!=null) {
        		//先删除用户的医师资格证
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("user_id", user.getId());
    			map.put("attach_type", 2);
        		userAttachmentDao.deleteByMap(map);
        		//不是空字符串则保存医师资格证
        		if (!"".equals(user.getQualification())) {
    				UserAttachment attach = new UserAttachment();
    				attach.setUserId(user.getId());
    				attach.setAttachMd5(user.getQualification());
    				attach.setAttachType(2);
    				userAttachmentDao.insert(attach);
    			}
			}
        	//医师执业证
        	if (user.getPractice()!=null) {
        		//先删除用户的医师执业证
    			Map<String,Object> map = new HashMap<String,Object>();
    			map.put("user_id", user.getId());
    			map.put("attach_type", 3);
        		userAttachmentDao.deleteByMap(map);
        		//不是空字符串则保存医师执业证
        		if (!"".equals(user.getPractice())) {
    				UserAttachment attach = new UserAttachment();
    				attach.setUserId(user.getId());
    				attach.setAttachMd5(user.getPractice());
    				attach.setAttachType(3);
    				userAttachmentDao.insert(attach);
    			}
			}
			return true;
        }
		return false;
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public BaseMsg importUserSign(Long userId, MultipartFile multipartFile) throws Exception {
		String  fileName = multipartFile.getOriginalFilename();
		String suffix = AttachmentUtils.getFileSuffix(fileName);
		//签名仅支持png格式
		if (!"png".equals(suffix)) {
			return BaseMsg.errorMsg("签名图片仅支持png格式");
		}
		BaseMsg msg = attachmentService.saveFile(multipartFile);
		if ("1".equals(msg.get("code").toString())) {
			//附件
			Attachment attachment = (Attachment)msg.get("data");
			
			UserAttachment attach = new UserAttachment();
			attach.setUserId(userId);
			attach.setAttachType(1);
			UserAttachment entity = userAttachmentDao.selectOne(attach);
			if (entity==null) {
				attach.setAttachMd5(attachment.getMdFive());
				userAttachmentDao.insert(attach);
			} else {
				entity.setAttachMd5(attachment.getMdFive());
				userAttachmentDao.updateAllColumnById(entity);
			}
		}
		return msg;
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public BaseMsg importUserSignBatch(MultipartFile multipartFile) throws Exception {
		String  fileName = multipartFile.getOriginalFilename();
		String suffix = AttachmentUtils.getFileSuffix(fileName);
		//批量导入签名仅支持rar格式
//		if (!"rar".equals(suffix)) {
//			return BaseMsg.errorMsg("仅支持rar压缩格式");
//		}
		if (!"zip".equals(suffix)) {
			return BaseMsg.errorMsg("仅支持zip压缩格式");
		}
		Integer count = 0;
		BaseMsg msg = attachmentService.saveFile(multipartFile);
		if ("1".equals(msg.get("code").toString())) {
			//附件
			Attachment attachment = (Attachment)msg.get("data");
			//完整路径
			String filePath = config.getResourceRootPath()+ attachment.getPhotoPath();
			//目录
			String fileDir = AttachmentUtils.getFilePrefix(filePath);
			//解压
//			RarUtil.unRar(rarPath, rarDir);
			ZipUtil.unzip(filePath, fileDir);
			//获取文件夹下的所有文件
			List<File> files = FileUtil.loopFiles(fileDir);
			if (files!=null&&files.size()>0) {
				for(File file:files){
					String subFileName = file.getName();
					//判断后缀是否正确
					String subSuffix = AttachmentUtils.getFileSuffix(subFileName);
					if (!"png".equals(subSuffix)){
						continue;
					}
					//判断用户是否存在
					String subPrefix = AttachmentUtils.getFilePrefix(subFileName);
					User user = new User();
					user.setOrgHospitalId(ShiroUserUtil.getHospitalId());
					user.setEmployeeId(subPrefix);
					User entity = userDao.selectOne(user);
					if (entity==null) {
						continue;
					}
					
					FileInputStream inFile = new FileInputStream(file);
					MultipartFile multi = new MyMockMultipartFile(subFileName,subFileName,null, inFile);
					
					BaseMsg subMsg = attachmentService.saveFile(multi);
					if ("1".equals(subMsg.get("code").toString())){
						//附件
						Attachment subAttachment = (Attachment)subMsg.get("data");
						
						UserAttachment attach = new UserAttachment();
						attach.setUserId(entity.getId());
						attach.setAttachType(1);
						UserAttachment attachEntity = userAttachmentDao.selectOne(attach);
						if (attachEntity==null) {
							attach.setAttachMd5(subAttachment.getMdFive());
							userAttachmentDao.insert(attach);
						} else {
							attachEntity.setAttachMd5(subAttachment.getMdFive());
							userAttachmentDao.updateAllColumnById(attachEntity);
						}
						count++;
					}
				}
			}
			return BaseMsg.successMsg("成功导入"+count+"张电子签名图片");
		}
		return BaseMsg.errorMsg("导入失败");
	}
	
	@Override
	public Page<User> selectRoleUserList(Long roleId, Page<User> page, Wrapper<User> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		wrapper.andNew("ur.role_id = {0}",roleId);
		page.setRecords(userDao.selectRoleUserList(page, wrapper));
		return page;
	}
	
	@Override
	public Page<User> selectUnbindingRoleUserList(Long roleId, Page<User> page, Wrapper<User> wrapper) {
		//急救人员或后台人员
		SqlHelper.fillWrapper(page, wrapper);
		wrapper.andNew("u.user_type = 3 or u.user_type = 4");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("role_id", roleId);
		
		List<UserRole> lists = userRoleDao.selectByMap(map);
		if (lists!=null&&lists.size()>0) {
			StringBuffer buffer = new StringBuffer();
			for(UserRole list:lists){
				buffer.append(list.getUserId().toString()).append(",");
			}
			wrapper.andNew().notIn("u.id", buffer.toString());
		}
		
		page.setRecords(userDao.selectUnbindingRoleUserList(page, wrapper));
		return page;
	}

	/**
	 * 根据角色获取用户列表
	 * @param wrapper
	 * @return
	 */
	@Override
	public List<User> selectUserListByRole(Wrapper<User> wrapper){
		return userDao.selectUserListByRole(wrapper);
	}

	@Override
	public boolean updatePassword(Long userId, String password) {
		User user = new User();
		user.setId(userId);
		user.setPassword(password);
		Integer count = userDao.updateById(user);
		if(count!=null && count>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateSignPassword(Long userId, String password) {
		User user = new User();
		user.setId(userId);
		user.setSignPassword(password);
		Integer count = userDao.updateById(user);
		if(count!=null && count>0){
			return true;
		}
		return false;
	}

	@Override
	public ImportExcelLogDTO insertUserExcel(List<UserExcelDTO> list, int userType) {
		ImportExcelLogDTO result = new ImportExcelLogDTO();
		Integer successCount = 0;
		if (list!=null&&list.size()>0){
			for(UserExcelDTO dto:list){
				ExcelLogDetailDTO excelLog = new ExcelLogDetailDTO();
				excelLog.setNum(Integer.valueOf(dto.getNum()));
				
				User user = BeanMapper.map(dto, User.class);
				user.setUuid(UUIDGenerator.generate());
				
				//sha256加密(初始密码暂时设为123456)
				String salt = RandomStringUtils.randomAlphanumeric(20);
				user.setPassword(TokenGenerator.entryptPassword("123456", salt));
				user.setSignPassword(TokenGenerator.entryptPassword("123456", salt));
				user.setSalt(salt);
				user.setUserType(userType);
				//状态暂时设为可用
				user.setStatus(1);
				user.setStaffStatus(1);
				
				Integer count = userDao.insert(user);
				if (count!=null && count>0) {
					excelLog.setSuccess(true);
					excelLog.setMessage("导入成功");
                    successCount++;
				} else {
					excelLog.setSuccess(false);
					excelLog.setMessage("导入失败");
				}
				
				result.getList().add(excelLog);
			}
		}
		result.setSuccessCount(successCount);
		return result;
	}
	
	@Override
	public List<User> selectMenuUserId(String menuCode) {
		return userDao.selectMenuUserId(menuCode);
	}
	
}
