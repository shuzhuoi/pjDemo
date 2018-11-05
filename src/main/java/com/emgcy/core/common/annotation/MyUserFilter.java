package com.emgcy.core.common.annotation;

import java.lang.annotation.*;

/**
 * 用户数据过滤
 *
 * @author bgy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyUserFilter {
    /**  表的别名 */
    String tableAlias() default  "";

    /**  true：没有本部门数据权限，也能查询本人数据 */
    boolean user() default true;
}
