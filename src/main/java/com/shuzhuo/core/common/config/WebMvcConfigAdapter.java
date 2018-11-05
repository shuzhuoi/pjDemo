package com.shuzhuo.core.common.config;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.shuzhuo.core.common.interceptor.RepeatDataInterceptor;

/**
 * 
 * @author chenshuzhuo
 * @data 2017-11-15
 *
 */
@Configuration
public class WebMvcConfigAdapter extends WebMvcConfigurerAdapter {

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	@Autowired
	private ApplicationYmlConfig appConfig;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RepeatDataInterceptor()).addPathPatterns("/**");
	}

	@PostConstruct
	public void initEditableValidation() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
				.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer
					.getConversionService();
			genericConversionService.addConverter(new StringTrimConverter());
			genericConversionService.addConverter(new StringToDateConverter());
		}

	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(appConfig.getResourceRootPath());
		return factory.createMultipartConfig();
	}

}
