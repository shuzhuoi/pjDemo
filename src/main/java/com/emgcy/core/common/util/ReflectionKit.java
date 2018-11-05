package com.emgcy.core.common.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.InvalidPropertyException;

public class ReflectionKit {
	
    public static String[] getNotNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> noEmptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            try {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue != null)
                    noEmptyNames.add(pd.getName());
            }catch (InvalidPropertyException e){

            }

        }
        String[] result = new String[noEmptyNames.size()];
        return noEmptyNames.toArray(result);
    }

}
