/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package org.coderj.beanmap.mapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.coderj.beanmap.BeanMapProfile;

/**
 * 类PojoBeanMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiangbo Nov 29, 2011 4:56:56 PM
 */
public class PojoBeanMapper extends BeanMapper {

    private static Map<String, BeanMapper> fieldMapperCache = new HashMap<String, BeanMapper>();

    private List<BeanMapper>               fieldBeanMappers = new ArrayList<BeanMapper>();
    private Method                         method;
    private String                         name;
    private Class<?>                       clazz;
    private boolean                        isTraveled       = false;

    public PojoBeanMapper(Class<?> clazz){
        this(clazz, null, null);
    }

    public PojoBeanMapper(Class<?> clazz, String name, Method method){
        this.clazz = clazz;
        this.method = method;
        this.name = name;

    }

    public void travel() {
        if (hasTravled()) {
            return;
        }
        isTraveled = true;

        for (Method m : clazz.getMethods()) {

            String methodName = m.getName();

            if (Modifier.isStatic(m.getModifiers())) {
                continue;
            }

            if (m.getReturnType().equals(Void.TYPE)) {
                continue;
            }

            if (m.getParameterTypes().length != 0) {
                continue;
            }

            if (methodName.startsWith("get")) {
                if (methodName.length() < 4) {
                    continue;
                }

                if (methodName.equals("getClass")) {
                    continue;
                }

                if (!Character.isUpperCase(methodName.charAt(3))) {
                    continue;
                }

                String propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                String clazzPropertyName = clazz.getName() + propertyName;
                // 先查找是否有同名的属性解析器
                BeanMapper mapper = fieldMapperCache.get(clazzPropertyName);
                if (mapper == null) {
                    Class<?> propertyClass = m.getReturnType();
                    if (BeanMapProfile.isBasicType(propertyClass)) {
                        mapper = new BasicBeanMapper(m, propertyName);
                    } else {
                        mapper = new PojoBeanMapper(propertyClass, propertyName, m);
                    }

                    fieldMapperCache.put(clazzPropertyName, mapper);

                }
                fieldBeanMappers.add(mapper);

            }
        }
        for (BeanMapper mapper : fieldBeanMappers) {
            mapper.travel();
        }
    }

    private synchronized boolean hasTravled() {
        if (isTraveled) {
            return true;
        }
        isTraveled = true;
        return false;
    }

    @Override
    public void describe(Map<String, Object> map, Object object, String prefix, Stack<Object> valueStack)
                                                                                                         throws Exception {
        if (object == null) {
            return;
        }

        Object propertyValue = getPropertyValue(object);
        if (valueStack.contains(propertyValue)) {
            throw new RuntimeException("Cycling Reference...");
        }
        valueStack.push(propertyValue);
        String propertyPrefix = buildKey(prefix);
        for (BeanMapper mapper : fieldBeanMappers) {
            mapper.describe(map, propertyValue, propertyPrefix, valueStack);
        }
        valueStack.pop();

    }

    private Object getPropertyValue(Object object) throws Exception {
        if (object == null) {
            return null;
        }
        if (method == null) {
            return object;
        }
        return method.invoke(object);
    }

    private String buildKey(String prefix) {
        if (name != null && prefix != null) {
            return prefix + "." + name;
        } else {
            return prefix;
        }

    }

}
