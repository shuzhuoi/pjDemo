package com.shuzhuo.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口APi 文档")
                .description("")
                .termsOfServiceUrl("https://swagger.io/docs/specification/what-is-swagger/")
                .version("1.0")
                .build();
    }

	private List<Parameter> setHeaderToken() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<>();
		tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build();
		pars.add(tokenPar.build());
		return pars;
	}

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("sys")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.sys"))
                .paths(PathSelectors.any())
                .build();
    }

	@Bean
	public Docket excelApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("excel")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.common.excel"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
    public Docket patientApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("patient")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.patient"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi5() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("工作台")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.workbench"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi6() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("审核中心")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.auditcenter"))
                .paths(PathSelectors.any())
                .build();
    }

	@Bean
	public Docket attachmentApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("attachment")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.attachment"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}

	@Bean
	public Docket dictApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("dict")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.dict"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}

	@Bean
	public Docket articleApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("article")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.article"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}

	@Bean
	public Docket resourceApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("resource")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pjDemo.core.system.resource"))
				.paths(PathSelectors.any())
				.build();
	}





}
