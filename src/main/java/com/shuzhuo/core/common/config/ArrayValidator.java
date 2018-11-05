package com.shuzhuo.core.common.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shuzhuo.core.common.validator.ArrayValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-12-01
 */
public class ArrayValidator implements ConstraintValidator<ArrayValid, String> {
	
	private static final Logger logger = LoggerFactory.getLogger(ArrayValidator.class);

	private String message;
	
	private String[] value;
	
	@Override
	public void initialize(ArrayValid constraintAnnotation) {
		this.message=constraintAnnotation.message();
		this.value=constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(logger.isInfoEnabled()){
			logger.info("Array valid...{}",value);
		}
		if(value==null||"".equals(value))
			return true;
		
		if(this.value.length>0){
			for (int i = 0; i < this.value.length; i++) {
				String thisValue = this.value[i];
				String valueParam = String.valueOf(value);
				if (thisValue.equals(valueParam)) {
					return true;
				}
			}
			message="参数格式不正确!";
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		
		return true;
	}

}
