/*
 * Copyright 1999-2011 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package org.coderj.beanmap.mapper;

import java.util.Map;
import java.util.Stack;

/**
 * 类BeanMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiangbo Nov 29, 2011 4:52:47 PM
 */
public abstract class BeanMapper {

    /**
     * 将object对象中的属性值转换为map
     * 
     * @param map
     * @param object
     * @throws Exception
     */
    public abstract void describe(Map<String, Object> map, Object object, String prefix, Stack<Object> valueObject)
                                                                                                                   throws Exception;

    public abstract void travel();

}
