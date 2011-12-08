package org.coderj.beanmap.test;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import junit.framework.Assert;

import org.coderj.beanmap.BeanMapUtils;
import org.coderj.beanmap.test.pojo.Address;
import org.coderj.beanmap.test.pojo.Person;
import org.junit.Test;

public class BeanMapUtilsTest {

    @Test
    public void test() {
        Person p = new Person();
        p.setAge(20);
        p.setName("Jack");
        Address address = new Address();
        address.setCity("Beijing");
        address.setStreet("Ditan");
        p.setAddr(address);
        Map<String, Object> map = BeanMapUtils.convertFromBean(p);

        Assert.assertEquals(20, map.get("Person.age"));
        Assert.assertEquals("Jack", map.get("Person.name"));
        Assert.assertEquals("Beijing", map.get("Person.addr.city"));
        Assert.assertEquals("Ditan", map.get("Person.addr.street"));

        map = BeanMapUtils.convertFromBean(p);

        Assert.assertEquals(20, map.get("Person.age"));
        Assert.assertEquals("Jack", map.get("Person.name"));
        Assert.assertEquals("Beijing", map.get("Person.addr.city"));
        Assert.assertEquals("Ditan", map.get("Person.addr.street"));
    }

    @Test
    public void test_basicType() throws ParseException {
        long l = 100l;
        Map<String, Object> map = BeanMapUtils.convertFromBean(l);
        Assert.assertEquals(100l, map.get("Long"));

        int i = 10;
        map = BeanMapUtils.convertFromBean(i);
        Assert.assertEquals(10, map.get("Integer"));

        double d = 3.1415926;
        map = BeanMapUtils.convertFromBean(d);
        Assert.assertEquals(3.1415926, map.get("Double"));

        Date date = new Date();
        map = BeanMapUtils.convertFromBean(date);
        Assert.assertEquals(date, map.get("Date"));

        Float f = Float.valueOf(1.64f);
        map = BeanMapUtils.convertFromBean(f);
        Assert.assertEquals(1.64f, map.get("Float"));

    }

    @Test
    public void test_reference() {
        Person p = new Person();
        p.setAge(20);
        p.setName("Jack");
        Address address = new Address();
        address.setCity("Beijing");
        address.setStreet("Ditan");
        p.setAddr(address);
        Person father = new Person();
        father.setName("Big Jack");
        father.setAge(50);
        father.setAddr(address);
        p.setFather(father);

        Map<String, Object> map = BeanMapUtils.convertFromBean(p);

        Assert.assertEquals(20, map.get("Person.age"));
        Assert.assertEquals("Jack", map.get("Person.name"));
        Assert.assertEquals("Beijing", map.get("Person.addr.city"));
        Assert.assertEquals("Ditan", map.get("Person.addr.street"));
        Assert.assertEquals("Big Jack", map.get("Person.father.name"));
        Assert.assertEquals(50, map.get("Person.father.age"));
        Assert.assertEquals("Beijing", map.get("Person.father.addr.city"));
        Assert.assertEquals("Ditan", map.get("Person.father.addr.street"));
    }

    @Test
    public void test_cycle_ref() {
        Person p = new Person();
        p.setFather(p);
        try {
            BeanMapUtils.convertFromBean(p);
        } catch (Exception e) {
            Assert.assertEquals("Cycling Reference...", e.getMessage());
        }
    }
}
