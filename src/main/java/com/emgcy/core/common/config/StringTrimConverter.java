package com.emgcy.core.common.config;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-23
 */
public class StringTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		if(source != null){
            source = source.trim();
            if("".equals(source)){ 
                source = null;
            }
        }
        return source;
	}

}
