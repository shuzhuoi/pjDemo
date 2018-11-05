package com.shuzhuo.core.system.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shuzhuo.core.common.util.StringUtil;
import com.shuzhuo.core.common.xss.SQLFilter;
import com.shuzhuo.core.system.sys.entity.User;
import com.shuzhuo.core.system.sys.mapper.RoleMapper;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;


/**
 * Controller公共组件
 * 
 * @author bgy
 * @date 2017年11月17日
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private RoleMapper roleMapper;
	
	protected User getUser() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(user!=null){
			user.setRoleList(roleMapper.selectUserRole(user.getId()));
		}
		return user;
	}

	protected Long getUserId() {
		return getUser().getId();
	}
	//查询用户类型
	protected Integer getUserType() {
		return getUser().getUserType();
	}

	protected Long getHospitalId() {
		return getUser().getOrgHospitalId();
	}
	
	//查询
    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void buildSearch(HttpServletRequest request,Wrapper wrapper) {
        String jsonStr = request.getParameter("search");
        if(StringUtils.isNotBlank(jsonStr)){
        	JSONArray array = JSONArray.fromObject(jsonStr);        	
        	List<SearchEntity> lists = JSONArray.toList(array, new SearchEntity(), new JsonConfig());
        	if(lists!=null&&lists.size()>0){
        		for(SearchEntity search:lists){
        			if(StringUtils.isBlank(search.getFieldKey())){
        				continue;
        			}
        			String value = search.getFieldVal();
        			if(StringUtils.isBlank(value)){
        				continue;
					}
        			//1普通查询，2模糊查询，3范围查询
        			if ("1".equals(search.getFlag())) {
        				wrapper.eq(search.getFieldKey(), value);
					}else if ("2".equals(search.getFlag())) {
        				wrapper.like(search.getFieldKey(), value);
					}else if ("3".equals(search.getFlag())) {
						if(value.startsWith("&&")){
							wrapper.le(search.getFieldKey(), value.substring(2));
						} else if (value.endsWith("&&")){
							wrapper.ge(search.getFieldKey(), value.substring(0,value.length()-2));
						} else{
							String[] split = value.split("&&");
							wrapper.ge(search.getFieldKey(),split[0]);
							wrapper.le(search.getFieldKey(),split[1]);
						}
					}
        		}
        	}
        }
    }
	
	//分页
    @SuppressWarnings("rawtypes")
	protected Page buildPage(HttpServletRequest request) {
        String isPage = request.getParameter("isPage");
        int current = 1;
        int size = 10;
        if (StringUtils.isNotBlank(isPage) && "false".equals(isPage)) {
            current = RowBounds.NO_ROW_OFFSET;
            size = RowBounds.NO_ROW_LIMIT;
        } else {
            String page = request.getParameter("page");
            String limit = request.getParameter("limit");

            current = StringUtils.isNotBlank(page) ? Integer.valueOf(page) : 1;
            size = StringUtils.isNotBlank(limit) ? Integer.valueOf(limit) : 10;
        }
        Page page = new Page(current, size);
        return page;

    }
    
	//排序
    protected void buildOrder(HttpServletRequest request,@SuppressWarnings("rawtypes") Wrapper wrapper) {
        String orderField = request.getParameter("orderField");
        String order = request.getParameter("order");
        if (StringUtils.isNotBlank(orderField)) {
        	
        	String field = StringUtil.camelToUnderline(orderField);
        	
        	wrapper.orderBy(SQLFilter.sqlInject(field), StringUtils.isNotBlank(order) && "desc".equals(order) ? false : true);
        } else {
        	wrapper.orderBy("create_time",false);
        }

    }
	
}
