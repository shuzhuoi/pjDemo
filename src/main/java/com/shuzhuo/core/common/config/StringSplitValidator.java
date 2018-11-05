package com.shuzhuo.core.common.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shuzhuo.core.common.validator.StringSplitValid;

public class StringSplitValidator implements ConstraintValidator<StringSplitValid, String> {

	private String message;
	
	private String value;
	
	@Override
	public void initialize(StringSplitValid constraintAnnotation) {
		message=constraintAnnotation.message();
		value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value==null||"".equals(value))
			return true;
		if(value.indexOf(this.value)==-1){
			message="\\u6821\\u9a8c\\u4e0d\\u901a\\u8fc7\\u002c\\u8be5\\u5b57\\u6bb5\\u5fc5\\u987b\\u8981\\u4ee5\\u003a\\u0026\\u0023\\u0033\\u0039\\u003b"+value+"\\u0026\\u0023\\u0033\\u0039\\u003b\\u5206\\u5272";
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		if (value.startsWith(this.value)||value.endsWith(this.value)) {
			message="\\u5f00\\u5934\\u548c\\u672b\\u5c3e\\u4e0d\\u80fd\\u5305\\u542b\\uff1a"+this.value;
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		for (String string : value.split(this.value)) {
			if ("".equals(string)) {
				message="\\u5206\\u5272\\u4e4b\\u95f4\\u5fc5\\u987b\\u5305\\u542b\\u5185\\u5bb9";
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
				return false;
			}
		}
		return false;
	}
	

}
