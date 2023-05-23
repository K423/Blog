package com.lzh.blog.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class NullUtils {

    /**
     * 获取所有属性值为空的数组
     */
    public static String[] getNullPropertyNames(Object obj){
        BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullProperty = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors){
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null){
                nullProperty.add(propertyName);
            }
        }
        return nullProperty.toArray(new String[nullProperty.size()]);
    }
}
