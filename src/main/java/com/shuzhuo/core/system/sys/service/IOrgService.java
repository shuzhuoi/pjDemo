package com.shuzhuo.core.system.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shuzhuo.core.common.excel.dto.DeptExcelDTO;
import com.shuzhuo.core.common.excel.dto.ImportExcelLogDTO;
import com.shuzhuo.core.system.sys.dto.SysDeptDTO;
import com.shuzhuo.core.system.sys.entity.Org;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
public interface IOrgService extends IService<Org> {
	
	/**
	 * 查询医院列表
	 */
	Page<Org> queryList(Page<Org> page, Wrapper<Org> wrapper);
	
	/**
	 * 新增医院
	 */
	boolean insertHospital(Org org);
	
	/**
	 * 修改医院
	 */
	boolean updateHospital(Org org);
	
	/**
	 * 查询科室列表
	 */
	Page<SysDeptDTO> selectDept(Page<SysDeptDTO> page, Wrapper<SysDeptDTO> wrapper);
	
	/**
	 * 查询科室列表(当前用户)
	 */
	List<Org> selectCurrentUserDept(Wrapper<SysDeptDTO> wrapper);
	
	/**
	 * 根据名称查询科室
	 */
	List<Long> selectDeptByName(Map<String, Object> map);
	
	/**
	 * 批量删除科室
	 */
	boolean delDeptBatch(Long[] ids);
	
	/**
	 * 新增科室
	 */
	boolean insertDept(SysDeptDTO dept);
	
	/**
	 * 修改科室
	 */
	boolean updateDept(SysDeptDTO dept);
	
	/**
	 * excel导入科室
	 */
	ImportExcelLogDTO insertDeptExcel(List<DeptExcelDTO> list);
	
}
