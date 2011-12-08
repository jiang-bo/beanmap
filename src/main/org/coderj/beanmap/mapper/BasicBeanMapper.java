package org.coderj.beanmap.mapper;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;

public class BasicBeanMapper extends BeanMapper {

    private Method method;
    private String name;

    public BasicBeanMapper(Method method, String name){
        // TODO Auto-generated constructor stub
        this.method = method;
        this.name = name;
    }

    @Override
    public void describe(Map<String, Object> map, Object object, String prefix, Stack<Object> valueObject)
                                                                                                          throws Exception {
        map.put(buildKey(prefix), getPropertyValue(object));

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

        if (prefix != null) {
            return prefix + "." + name;
        } else {
            return name;
        }
    }

    @Override
    public void travel() {

    }
}
