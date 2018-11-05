package com.shuzhuo.core.common.shiro;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * 继承ShiroFilterFactoryBean处理拦截资源文件问题
 */
public class CrShiroFilterFactoryBean extends ShiroFilterFactoryBean {

//    private static ShiroKickoutSessionControlFilter kickoutFilter = SpringContextHolder.getBean("kickoutFilter");

    // ShiroFilter将直接忽略的请求
    private Set<String> ignoreExt;

    public CrShiroFilterFactoryBean() {
        super();
        ignoreExt = new HashSet<String>();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
        ignoreExt.add(".svg");
        ignoreExt.add(".map");
        ignoreExt.add(".ico");
    }


    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) getSecurityManager();
        if (securityManager == null) {
            throw new BeanInitializationException("SecurityManager property must be set.");
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            throw new BeanInitializationException("The shiro manager does not implement the WebSecurityManager interface.");
        }

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();

        FilterChainManager chainManager = createFilterChainManager();
//		chainManager.addFilter("authcs", filter);
        chainResolver.setFilterChainManager(chainManager);
        return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private class MySpringShiroFilter extends AbstractShiroFilter {
        public MySpringShiroFilter(
                WebSecurityManager securityManager, PathMatchingFilterChainResolver chainResolver) {
            super();
            if (securityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(securityManager);
            if (chainResolver != null) {
                setFilterChainResolver(chainResolver);
            }
        }

        @Override
        protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
                                        FilterChain chain)
                throws ServletException, IOException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            String str = request.getRequestURI().toLowerCase();
            boolean flag = true;
            String requestURL = request.getRequestURL().toString();
            /**
             * swagger-ui以及api-doc请求放行
             */
            if (requestURL.contains("swagger") || requestURL.contains("v2")) {
                flag = false;
            } else {
                int idx = 0;
                if ((idx = str.lastIndexOf(".")) > 0) {
                    str = str.substring(idx);
                    if (ignoreExt.contains(str.toLowerCase())) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                super.doFilterInternal(servletRequest, servletResponse, chain);
            } else {
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
