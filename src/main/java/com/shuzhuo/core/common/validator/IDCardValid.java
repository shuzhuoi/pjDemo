package com.shuzhuo.core.common.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.shuzhuo.core.common.config.IDCardValidator;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-23
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})  
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy=IDCardValidator.class)
public @interface IDCardValid {
	
	String message() default "\u8eab\u4efd\u8bc1\u6821\u9a8c\u5931\u8d25\u0021";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
