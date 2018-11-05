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
                .title("crEmgcy 接口APi 文档")
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
                .apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.sys"))
                .paths(PathSelectors.any())
                .build();
    }
	
	@Bean
	public Docket excelApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("excel")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.common.excel"))
				.paths(PathSelectors.any())
				.build();
	}
	
	@Bean
    public Docket patientApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("patient")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.patient"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi5() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("工作台")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.workbench"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi6() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("审核中心")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.auditcenter"))
                .paths(PathSelectors.any())
                .build();
    }
	
	@Bean
	public Docket attachmentApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("attachment")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.attachment"))
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
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.dict"))
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
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.article"))
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
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.resource"))
				.paths(PathSelectors.any())
				.build();
	}
	
	@Bean
	public Docket videoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("video")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.video"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}
	
	@Bean
	public Docket trainApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("train")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.train"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}
	
	@Bean
	public Docket classifyApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.groupName("classify")
		.apiInfo(apiInfo())
		.select()
		.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.classify"))
		.paths(PathSelectors.any())
		.build()
		.globalOperationParameters(setHeaderToken());
	}

	@Bean
	public Docket restfulWebApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("restfulWeb")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.interfaces.restful.web"))
				.paths(PathSelectors.any())
				.build();
	}
	
	@Bean
	public Docket restfulAppApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Ipad端")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.interfaces.restful.app"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket trackApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("track")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.track"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public Docket dispatchApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("dispatch")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.dispatch"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(setHeaderToken());
	}

	@Bean
	public Docket emergencyApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("emergency")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.emgcy.core.system.emergency"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(setHeaderToken());
	}
	

}
