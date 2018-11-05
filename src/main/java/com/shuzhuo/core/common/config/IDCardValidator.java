package com.shuzhuo.core.common.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shuzhuo.core.common.util.IDCardUtil;
import com.shuzhuo.core.common.validator.IDCardValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-23
 */
public class IDCardValidator implements ConstraintValidator<IDCardValid, String> {
	
	private static final Logger logger = LoggerFactory.getLogger(IDCardValidator.class);

	private String message;
	
	@Override
	public void initialize(IDCardValid constraintAnnotation) {
		message=constraintAnnotation.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(logger.isInfoEnabled()){
			logger.info("IDCard valid...{}",value);
		}
		if(value==null||"".equals(value)){
			message="身份证不能为空!";
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		if(value.length()!=15 && value.length()!=18){
			message="身份证只能15位或18位!";
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		
		return IDCardUtil.validateIdCard(value);
	}

}
