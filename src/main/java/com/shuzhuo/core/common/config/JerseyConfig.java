//package com.emgcy.core.common.config;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.ServerProperties;
//import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
//import org.springframework.stereotype.Component;
//
//import com.emgcy.core.common.exception.ExceptionHandler;
//
///**
// * 
// * @author chenshuzhuo
// * @date 2018-01-02
// */
//@Component
//public class JerseyConfig extends ResourceConfig {
//	public JerseyConfig() {
//		packages("com.emgcy.core.interfaces.restful");
//		register(ExceptionHandler.class);
//		register(RequestContextFilter.class);
////		register(ValidationConfigurationContextResolver.class);
//		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
//		property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
//	}
//
//}
