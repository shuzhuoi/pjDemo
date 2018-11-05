//package com.emgcy.core.common.config;
//
//import org.glassfish.jersey.servlet.ServletContainer;
//import org.glassfish.jersey.servlet.ServletProperties;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 
// * @author chenshuzhuo
// * @date 2018-01-02
// */
//@Configuration
//public class BeanConfig {
//
//	@Bean
//	public ServletRegistrationBean servletRegistrationBean() {
//		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
//		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
//		return registration;
//	}
//}
