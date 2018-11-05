//package com.emgcy.core.common.aspect;
//
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import com.baomidou.mybatisplus.mapper.Wrapper;
//import com.emgcy.core.common.annotation.MyUserFilter;
//import com.emgcy.core.common.constants.Constant;
//import com.emgcy.core.common.exception.CustomException;
//import com.emgcy.core.common.shiro.ShiroUserUtil;
//import com.emgcy.core.system.sys.entity.Role;
//import com.emgcy.core.system.sys.entity.User;
//
///**
// * 数据过滤，切面处理类
// * @author chenshun
// * @email sunlightcs@gmail.com
// * @date 2017/9/17 15:02
// */
//@Aspect
//@Component
//public class MyUserFilterAspect {
//
//	@Pointcut("@annotation(com.emgcy.core.common.annotation.MyUserFilter)")
//	public void myUserFilterCut() {
//
//	}
//
//	@Before("myUserFilterCut()")
//	public void myUserFilter(JoinPoint point) throws Throwable {
//		Object params = point.getArgs()[1];
//		if(params != null && params instanceof Wrapper){
//			
//			
//			User user = ShiroUserUtil.getCurrentUser();
//
//			//如果不是超级管理员，则只能查询本部门及子部门数据
//			if(user.getId() != Constant.SUPER_ADMIN){
//				Wrapper ew = (Wrapper)params;
//				String sqlFilter = getFilterSQL(user, point);
//				if(!"".equals(sqlFilter)){
//					ew.andNew(sqlFilter);					
//				}
//			}
//			return ;
//		}
//		throw new CustomException("数据权限接口的参数，只能是Wrapper类型，且不能为NULL");
//	}
//
//	/**
//	 * 获取数据过滤的SQL
//	 */
//	private String getFilterSQL(User user, JoinPoint point){
//		MethodSignature signature = (MethodSignature) point.getSignature();
//		MyUserFilter myUserFilter = signature.getMethod().getAnnotation(MyUserFilter.class);
//		//获取表的别名
//		String tableAlias = myUserFilter.tableAlias();
//		if(StringUtils.isNotBlank(tableAlias)){
//			tableAlias +=  ".";
//		}
//		
//		StringBuilder filterSql = new StringBuilder();
//		List<Role> roleList = user.getRoleList();
//		if(roleList!=null&&roleList.size()>0){
//			int min = Constant.SELF_DATA_SCOPE;
//			for(Role role:roleList){
//				if(role.getDataScope()<min){
//					min=role.getDataScope();
//				}
//			}
//			if(min==Constant.ALL_DATA_SCOPE){
//				return filterSql.toString();
//			}else if(min==Constant.HOSPITAL_DATA_SCOPE){
//				if(user.getOrgHospitalId()!=null){
//					filterSql.append(tableAlias).append("org_hospital_id = '").append(user.getOrgHospitalId()).append("'");					
//				}
//			}else if(min==Constant.DEPT_DATA_SCOPE){
//				if(user.getOrgDeptId()!=null){
//					filterSql.append(tableAlias).append("org_dept_id = '").append(user.getOrgDeptId()).append("'");					
//				}
//			}else if(min==Constant.SELF_DATA_SCOPE){
//				filterSql.append(tableAlias).append("create_by = '").append(user.getId()).append("'");
//			}
//		}
//		
//		//没有数据权限，也能查询本人创建的数据
//		if(myUserFilter.user()){
//			filterSql.append(tableAlias).append("create_by = '").append(user.getId()).append("'");
//		}
//		return filterSql.toString();
//	}
//}
