package com.emgcy.core.common.mybatis;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.emgcy.core.common.shiro.ShiroUserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import java.util.Date;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-01
 * version 0.2
 * 必须实现元对象处理器接口
 *
 */
public class MyMetaObjectHandler extends MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
    	Object obj=null;
    	try {
            obj = getFieldValByName("delFlag", metaObject);
            if (null == obj) {
                setFieldValByName("delFlag",0, metaObject);
            }
        } catch (Exception e) {
        }

        try {
            obj = getFieldValByName("createBy", metaObject);
            if (null == obj) {
                setFieldValByName("createBy",ShiroUserUtil.getCurrentUser().getId(), metaObject);
            }
        } catch (Exception e) {
        }
        
        try {
        	obj = getFieldValByName("creator_name", metaObject);
        	if (null == obj) {
        		setFieldValByName("creator_name",ShiroUserUtil.getCurrentUser().getName(), metaObject);
        	}
        } catch (Exception e) {
        }
        try {
            obj = getFieldValByName("createTime", metaObject);
            if (null == obj) {
                setFieldValByName("createTime", new Date(), metaObject);
            }
        } catch (Exception e) {
        	obj=null;
        }
    }

	@SuppressWarnings("unused")
	@Override
    public void updateFill(MetaObject metaObject) {
    	Object obj=null;
    	try {
    		obj = getFieldValByName("updateBy", metaObject);
            setFieldValByName("updateBy",ShiroUserUtil.getCurrentUser().getId(), metaObject);
        } catch (Exception e) {
        }

        try {
        	obj = getFieldValByName("updateTime", metaObject);
            setFieldValByName("updateTime", new Date(), metaObject);
        } catch (ReflectionException e) {
        	obj=null;
        }
        
    }
}
