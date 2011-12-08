/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package org.coderj.beanmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.coderj.beanmap.mapper.BeanMapper;
import org.coderj.beanmap.mapper.PojoBeanMapper;

/**
 * ��BeanMapUtils.java��ʵ��������TODO ��ʵ������
 * 
 * @author jiangbo Nov 29, 2011 3:50:32 PM
 */
public class BeanMapUtils {

    static Map<String, BeanMapper> mapperCache = new HashMap<String, BeanMapper>();

    public static Map<String, Object> convertFromBean(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (object == null) {
            return null;
        }

        // �����JDK�������ͣ�ֱ�ӷ��ظö���ֵ
        Class<?> clazz = object.getClass();
        if (BeanMapProfile.isBasicType(clazz)) {
            map.put(clazz.getSimpleName(), object);
            return map;
        }

        // ������Զ���POJO�����������
        BeanMapper mapper = buildBeanMapper(clazz);
        Stack<Object> valueStack = new Stack<Object>();
        try {
            mapper.describe(map, object, clazz.getSimpleName(), valueStack);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    private static BeanMapper buildBeanMapper(Class<?> clazz) {
        BeanMapper mapper = mapperCache.get(clazz.getName());
        if (mapper != null) {
            return mapper;
        }
        mapper = new PojoBeanMapper(clazz);
        mapper.travel();
        mapperCache.put(clazz.getName(), mapper);
        return mapper;

    }
}
