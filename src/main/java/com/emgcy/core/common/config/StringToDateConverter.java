package com.emgcy.core.common.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author chenshuzhuo
 * @date 2017-11-23
 *
 */
public class StringToDateConverter implements Converter<String, Date>{

	private static final String YMD_DATE_FORMAT = "yyyy-MM-dd";
	private static final String YMDHM_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	private static final String YMDHMS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	    
	@Override
	public Date convert(String source) {
		if(StringUtils.isBlank(source)){
			return null;
		}
		source = source.trim();
		try {
            if (source.contains("-")) {
                SimpleDateFormat formatter;
                if (source.contains("ss")) {
                    formatter = new SimpleDateFormat(YMDHMS_DATE_FORMAT);
                } else if (source.contains(":")) {
                    formatter = new SimpleDateFormat(YMDHM_DATE_FORMAT);
                } else {
                    formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
                }
                Date dtDate = formatter.parse(source);
                return dtDate;
            } else if (source.matches("^\\d+$")) {
                Long lDate = new Long(source);
                return new Date(lDate);
            }else if (source.contains("GMT+0800")) {
            	source = source.replace("GMT", "").replaceAll("\\(.*\\)", "");
                SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
                return format.parse(source);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", source));
        }
		return null;
	}

}
