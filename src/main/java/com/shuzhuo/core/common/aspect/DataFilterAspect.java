package com.shuzhuo.core.common.aspect;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shuzhuo.core.common.annotation.DataFilter;
import com.shuzhuo.core.common.constants.Constant;
import com.shuzhuo.core.common.exception.CustomException;
import com.shuzhuo.core.common.shiro.ShiroUserUtil;
import com.shuzhuo.core.system.sys.entity.User;

/**
 * 数据过滤，切面处理类
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/9/17 15:02
 */
@Aspect
@Component
public class DataFilterAspect {
//    @Autowired
//    private SysDeptService sysDeptService;

	@Pointcut("@annotation(com.shuzhuo.core.common.annotation.DataFilter)")
	public void dataFilterCut() {

	}

	@Before("dataFilterCut()")
	public void dataFilter(JoinPoint point) throws Throwable {
		Object params = point.getArgs()[1];
		if(params != null && params instanceof Wrapper){
			
			
			User user = ShiroUserUtil.getCurrentUser();

			//如果不是超级管理员，则只能查询本部门及子部门数据
			if(user.getId() != Constant.SUPER_ADMIN){
				Wrapper ew = (Wrapper)params;
				ew.andNew(getFilterSQL(user, point));
				
			}

			return ;
		}

		throw new CustomException("数据权限接口的参数，只能是Wrapper类型，且不能为NULL");
	}

	/**
	 * 获取数据过滤的SQL
	 */
	private String getFilterSQL(User user, JoinPoint point){
		MethodSignature signature = (MethodSignature) point.getSignature();
		DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
		//获取表的别名
		String tableAlias = dataFilter.tableAlias();
		if(StringUtils.isNotBlank(tableAlias)){
			tableAlias +=  ".";
		}

		//获取子部门ID
//		String subDeptIds = sysDeptService.getSubDeptIdList(user.getDeptId());

		StringBuilder filterSql = new StringBuilder();
		filterSql.append("and (");
//		filterSql.append(tableAlias).append("dept_id in(").append(subDeptIds).append(")");

		//没有本部门数据权限，也能查询本人数据
		if(dataFilter.user()){
//			filterSql.append(" or ").append(tableAlias).append("user_id=").append(user.getUserId());
		}
		filterSql.append(")");

		return filterSql.toString();
	}
}
