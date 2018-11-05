package com.shuzhuo.core.system.sys.mapper;

import java.util.List;
import java.util.Map;

import com.shuzhuo.core.system.sys.dto.SysDeptDTO;
import com.shuzhuo.core.system.sys.entity.Org;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;


/**
 * <p>
  * 机构表 Mapper 接口
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-23
 */
public interface OrgMapper extends BaseMapper<Org> {
	
	/**
	 * 查询医院列表
	 */
	List<Org> queryList(RowBounds rb, @Param("ew") Wrapper<Org> wrapper);
	
	/**
	 * 查询科室列表
	 */
	List<Org> selectDept(RowBounds rb, @Param("ew") Wrapper<SysDeptDTO> wrapper);
	
	/**
	 * 根据名称查询科室
	 */
	List<Long> selectDeptByName(Map<String, Object> map);
	
	/**
     * 根据id查询机构
     */
	Org selectOrgById(Long id);
	
	/**
     * 批量删除科室
     */
	int delDeptBatch(Long[] id);
	
}