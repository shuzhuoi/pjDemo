package com.emgcy.core.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.emgcy.core.common.config.StringSplitValidator;

/**
 * 
 * @author chenshuzhuo
 * @date 2018-01-05
 * 
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy=StringSplitValidator.class)
public @interface StringSplitValid {
	
	String message() default "\\u6821\\u9a8c\\u5931\\u8d25\\u0021";

	String value();
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
