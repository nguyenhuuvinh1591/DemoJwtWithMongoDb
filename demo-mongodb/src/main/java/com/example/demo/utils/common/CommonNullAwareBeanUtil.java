package com.example.demo.utils.common;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CommonNullAwareBeanUtil {

    private static Logger logger = LoggerFactory.getLogger(CommonNullAwareBeanUtil.class);

    public static void copyPropertiesWONull(Object src, Object target) throws Exception {
        try {
            BeanUtils.copyProperties(src, target, getArrNullPropertyName(src));
        } catch (Exception e) {
            logger.error("##copyPropertiesWONull##", e);
            throw e;
        }
    }

    private static String[] getArrNullPropertyName(Object source) throws Exception {
        Set<String> emptyNames = new HashSet<String>();
        String[] result;
        try {
            final BeanWrapper src = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

            for (java.beans.PropertyDescriptor pd : pds) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue == null)
                    emptyNames.add(pd.getName());
            }
            result = new String[emptyNames.size()];
        } catch (Exception e) {
            logger.error("##getNullPropertyNames##", e);
            throw e;
        }
        return emptyNames.toArray(result);
    }
}
