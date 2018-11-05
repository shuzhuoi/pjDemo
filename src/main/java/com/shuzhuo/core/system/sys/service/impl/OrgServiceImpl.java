package com.shuzhuo.core.system.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.shuzhuo.core.common.excel.dto.DeptExcelDTO;
import com.shuzhuo.core.common.excel.dto.ImportExcelLogDTO;
import com.shuzhuo.core.system.sys.dto.SysDeptDTO;
import com.shuzhuo.core.system.sys.entity.Org;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.service.IOrgService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.excel.dto.ExcelLogDetailDTO;
import com.shuzhuo.core.common.util.UUIDGenerator;
import com.shuzhuo.core.system.sys.mapper.OrgMapper;
import com.shuzhuo.core.system.sys.mapper.UserMapper;
import com.google.common.collect.Lists;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
@Service
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgServiceImpl.class);
	
	@Autowired
	private OrgMapper orgDao;
	@Autowired
	private UserMapper userDao;

	@Override
	public Page<Org> queryList(Page<Org> page, Wrapper<Org> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		page.setRecords(orgDao.queryList(page, wrapper));
		return page;
	}

	@Override
	@Transactional
	public boolean insertHospital(Org org) {
		//新增医院
		org.setId(null);
		org.setUuid(UUIDGenerator.generate());
		org.setParentId(0L);
		org.setType(Constant.ORG_HTYPE);
		Integer count = orgDao.insert(org);
		if(count!=null && count>0){
			//创建医院管理员用户
			User user = new User();
			user.setUuid(UUIDGenerator.generate());
			user.setOrgHospitalId(org.getId());
			user.setEmployeeId(org.getAccount());
			user.setName("医院管理员");
			//sha256加密
			String salt = RandomStringUtils.randomAlphanumeric(20);
			user.setPassword(new Sha256Hash(org.getPassword(), salt).toHex());
			user.setSalt(salt);
			user.setSex(1);
			user.setUserType(2);
			user.setStatus(1);
			user.setStaffStatus(1);
			user.setExpert(0);
			Integer userCount = userDao.insert(user);
			if(userCount!=null && userCount>0){
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateHospital(Org org) {
		Org bean = orgDao.selectById(org.getId());
		if (org.getName()!=null) {//医院名称
			bean.setName(org.getName());
		}
		if (org.getLevel()!=null) {//医院等级
			bean.setLevel(org.getLevel());
		}
		if (org.getAddress()!=null) {
			bean.setAddress(org.getAddress());
		}
		if (org.getTelephone()!=null) {
			bean.setTelephone(org.getTelephone());
		}
		Integer count = orgDao.updateAllColumnById(bean);
		if (count!=null && count>0) {
			if (StringUtils.isNotBlank(org.getPassword())) {
				//查询医院管理员
				User user = new User();
				user.setOrgHospitalId(org.getId());
				user.setUserType(2);
				User user2 = userDao.selectOne(user);
				//更新密码
				user2.setPassword(new Sha256Hash(org.getPassword(), user2.getSalt()).toHex());
				Integer userCount = userDao.updateById(user2);
				if (userCount==null||userCount==0) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}

	@Override
	public Page<SysDeptDTO> selectDept(Page<SysDeptDTO> page, Wrapper<SysDeptDTO> wrapper) {
		SqlHelper.fillWrapper(page, wrapper);
		List<Org> lists = orgDao.selectDept(page, wrapper);
		//返回结果
		List<SysDeptDTO> deptLists = Lists.newArrayList();
		if (lists!=null&&lists.size()>0) {
			//遍历组装SysDeptDTO
			for (Org org:lists) {
				SysDeptDTO dto = new SysDeptDTO();
				dto.setId(org.getId());
				dto.setCode(org.getCode());
				dto.setName(org.getName());
				//医院ID和名称
				if (org.getType()==2) {
					dto.setHospitalId(org.getParentId());
					dto.setHospitalName(org.getParentName());
				}else if(org.getType()==3){
					dto.setParentId(org.getParentId());
					dto.setParentName(org.getParentName());
					//获取上级科室所在医院
					Org bean = orgDao.selectOrgById(org.getParentId());
					if (bean!=null) {
						dto.setHospitalId(bean.getParentId());
						dto.setHospitalName(bean.getParentName());
					}
				}
				deptLists.add(dto);
			}
		}
		page.setRecords(deptLists);
		return page;
	}
	
	@Override
	public List<Org> selectCurrentUserDept(Wrapper<SysDeptDTO> wrapper) {
		Page<SysDeptDTO> page = new Page<SysDeptDTO>(RowBounds.NO_ROW_OFFSET,RowBounds.NO_ROW_LIMIT);
		List<Org> lists = orgDao.selectDept(page, wrapper);
		if (lists!=null&&lists.size()>0) {
			for(Org list:lists){
				//去除空值
				if (StringUtils.isBlank(list.getName())) {
					lists.remove(list);					
				}
				if (list.getType()==2) {
					list.setName(list.getName()+"(一级)");
				} else if (list.getType()==3) {
					list.setName(list.getName()+"(二级)");
				}
			}
		}
		return lists;
	}
	
	@Override
	public List<Long> selectDeptByName(Map<String, Object> map) {
		return orgDao.selectDeptByName(map);
	}

	@Override
	@Transactional
	public boolean delDeptBatch(Long[] ids) {
		int count = orgDao.delDeptBatch(ids);
		if (count>0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean insertDept(SysDeptDTO dept) {
		Org org = new Org();
		org.setUuid(UUIDGenerator.generate());
		org.setCode(dept.getCode());
		org.setName(dept.getName());
		if (dept.getParentId()==null) {
			org.setParentId(dept.getHospitalId());
			org.setType(2);
		} else {
			org.setParentId(dept.getParentId());
			org.setType(3);
		}
		Integer count = orgDao.insert(org);
		if (count>0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateDept(SysDeptDTO dept) {
		Org org = orgDao.selectById(dept.getId());
		if (dept.getCode()!=null) {
			org.setCode(dept.getCode());
		}
		if (dept.getName()!=null) {
			org.setName(dept.getName());
		}
		if (dept.getParentId()==null) {
			org.setParentId(dept.getHospitalId());
			org.setType(2);
		} else {
			org.setParentId(dept.getParentId());
			org.setType(3);
		}
		Integer count = orgDao.updateAllColumnById(org);
		if (count>0) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public ImportExcelLogDTO insertDeptExcel(List<DeptExcelDTO> list) {
		ImportExcelLogDTO result = new ImportExcelLogDTO();
		Integer successCount = 0;
		if (list!=null&&list.size()>0) {
			for(DeptExcelDTO dto:list){
				ExcelLogDetailDTO excelLog = new ExcelLogDetailDTO();
				excelLog.setNum(Integer.valueOf(dto.getNum()));
				
				Org dept = new Org();
				dept.setUuid(UUIDGenerator.generate());
				dept.setCode(dto.getCode());
				dept.setName(dto.getName());
				
				EntityWrapper<Org> deptWrapper = new EntityWrapper<>(new Org());
				//一级科室
				if (StringUtils.isBlank(dto.getParentDeptName())||"无".equals(dto.getParentDeptName().trim())) {
					deptWrapper.eq("parent_id", dto.getHospitalId());
					deptWrapper.eq("name", dto.getName());
					deptWrapper.eq("type", 2);
					int count = orgDao.selectCount(deptWrapper);
					if (count>0) {
						excelLog.setSuccess(false);
						excelLog.setMessage("科室名称已经存在");
						result.getList().add(excelLog);
						continue;
					}
					//上级id和type
					dept.setParentId(dto.getHospitalId());
					dept.setType(2);
					//一级科室要先写入数据库
					Integer deptCount = orgDao.insert(dept);
					if (deptCount!=null && deptCount>0) {
						excelLog.setSuccess(true);
						excelLog.setMessage("导入成功");
	                    successCount++;
					} else {
						excelLog.setSuccess(false);
						excelLog.setMessage("导入失败");
					}
					result.getList().add(excelLog);
					
				} else {//二级科室
					Org org2 = new Org();
					org2.setParentId(dto.getHospitalId());
					org2.setName(dto.getParentDeptName());
					org2.setType(2);
					
					Org oneDept = orgDao.selectOne(org2);
					if (oneDept==null) {
						excelLog.setSuccess(false);
						excelLog.setMessage("上级科室名称不存在");
						result.getList().add(excelLog);
						continue;
					}
					
					EntityWrapper<Org> deptWrapper2 = new EntityWrapper<>(new Org());
					deptWrapper2.eq("parent_id", oneDept.getId());
					deptWrapper2.eq("name", dto.getName());
					deptWrapper2.eq("type", 3);
					int count = orgDao.selectCount(deptWrapper2);
					if (count>0) {
						excelLog.setSuccess(false);
						excelLog.setMessage("科室名称已经存在");
						result.getList().add(excelLog);
						continue;
					}
					
					dept.setParentId(oneDept.getId());
					dept.setType(3);
					
					Integer deptCount = orgDao.insert(dept);
					if (deptCount!=null && deptCount>0) {
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
		}
		result.setSuccessCount(successCount);
		return result;
	}
	
}
