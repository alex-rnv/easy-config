package com.alexrnv.easyconf.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Date: 11/22/2015
 * Time: 5:31 PM
 *
 * @author Alex
 */
public class PropertyFactoryTest {

    private PropertyFactory factory = new PropertyFactory();

    @Test(expected = NullPointerException.class)
    public void testNullKey() {
        factory.addResolver(null, val -> null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullValue() {
        factory.addResolver("key", null);
    }

    @Test
    public void testCustomResolver() {
        factory.addResolver("my.key.hash_map", val -> new HashMap<>());
        Property p = factory.newProperty("my.key.hash_map", "any");
        Assert.assertEquals("Same name", "my.key.hash_map", p.getName());
        Assert.assertEquals("Custom HashMap resolver", HashMap.class, p.getValue().getClass());
    }

}