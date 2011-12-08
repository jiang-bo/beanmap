package org.coderj.beanmap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BeanMapProfile {

    public static Set<Class<?>> basic_type = new HashSet<Class<?>>();
    static {
        basic_type.add(long.class);
        basic_type.add(int.class);
        basic_type.add(short.class);
        basic_type.add(double.class);
        basic_type.add(byte.class);
        basic_type.add(char.class);
        basic_type.add(float.class);
        basic_type.add(boolean.class);

        basic_type.add(Long.class);
        basic_type.add(Integer.class);
        basic_type.add(Double.class);
        basic_type.add(Float.class);
        basic_type.add(Byte.class);
        basic_type.add(Short.class);
        basic_type.add(BigDecimal.class);
        basic_type.add(BigInteger.class);
        basic_type.add(Boolean.class);
        basic_type.add(String.class);
        basic_type.add(Date.class);
    }

    public static boolean isBasicType(Class<?> clazz) {
        return basic_type.contains(clazz);
    }
}
