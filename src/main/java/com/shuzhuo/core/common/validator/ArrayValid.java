package com.shuzhuo.core.common.validator;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.shuzhuo.core.common.config.ArrayValidator;


/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-01
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD,ElementType.PARAMETER})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy=ArrayValidator.class)
public @interface ArrayValid {
	
	String message() default "\u53c2\u6570\u683c\u5f0f\u4e0d\u6b63\u786e\u0021";

	Class<?>[] groups() default {};
	
	String[] value() default{};

	Class<? extends Payload>[] payload() default {};
	
	@Target({ METHOD,PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		ArrayValid[] value();
	}
}
