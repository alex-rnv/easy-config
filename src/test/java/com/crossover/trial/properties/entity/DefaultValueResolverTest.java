package com.crossover.trial.properties.entity;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Date: 11/22/2015
 * Time: 4:50 PM
 *
 * @author Alex
 */
public class DefaultValueResolverTest {

    private DefaultValueResolver resolver = new DefaultValueResolver();

    @Test
    public void testNull() {
        Assert.assertNull("Null resolves to null", resolver.resolveValue(null));
    }

    @Test
    public void testEmptyString() {
        Assert.assertEquals("Empty string resolved to empty string", "", resolver.resolveValue(""));
    }

    @Test
    public void testBoolean() {
        Assert.assertEquals("Boolean ignore case", true, resolver.resolveValue("true"));
        Assert.assertEquals("Boolean ignore case", false, resolver.resolveValue("false"));
        Assert.assertEquals("Boolean ignore case", true, resolver.resolveValue("TRUE"));
        Assert.assertEquals("Boolean ignore case", false, resolver.resolveValue("FALSE"));
    }

    @Test
    public void testInteger() {
        Assert.assertEquals("Integer string", 1, resolver.resolveValue("1"));
        Assert.assertEquals("Integer string", 0, resolver.resolveValue("0"));
        Assert.assertEquals("Integer string", -1, resolver.resolveValue("-1"));
        Assert.assertEquals("Hex Integer string", 10, resolver.resolveValue("0xa"));
    }

    @Test
    public void testLong() {
        Assert.assertEquals("Long string", 1L, resolver.resolveValue("1L"));
        Assert.assertEquals("Long string", 0L, resolver.resolveValue("0L"));
        Assert.assertEquals("Long string", -1L, resolver.resolveValue("-1L"));
        Assert.assertEquals("Too big for Integer", 21474836470L, resolver.resolveValue("21474836470"));
    }

    @Test
    public void testBigInteger() {
        Assert.assertEquals("Too big for Long", new BigInteger("92233720368547758070"),
                resolver.resolveValue("92233720368547758070"));
    }

    @Test
    public void testFloat() {
        Assert.assertEquals("Float string", 1.f, resolver.resolveValue("1."));
        Assert.assertEquals("Float string", 1.0f, resolver.resolveValue("1.0"));
        Assert.assertEquals("Float string", -1.0f, resolver.resolveValue("-1.0"));
        Assert.assertEquals("Float scientific", 1e-3f, resolver.resolveValue("1e-3"));
        Assert.assertEquals("Float scientific", 1e4f, resolver.resolveValue("1e4"));
    }

    @Test
    public void testDouble() {
        Assert.assertEquals("Double string", 1., resolver.resolveValue("1.d"));
        Assert.assertEquals("Double string", 1.0, resolver.resolveValue("1.0d"));
        Assert.assertEquals("Double string", -1.0, resolver.resolveValue("-1.0d"));
        Assert.assertEquals("Double scientific", 1e-3, resolver.resolveValue("1e-3d"));
        Assert.assertEquals("Double scientific", 1e4, resolver.resolveValue("1e4d"));
        Assert.assertEquals("Too big for Float", 3.4028235e+39, resolver.resolveValue("3.4028235e+39"));
    }

    @Test
    public void testBigDecimal() {
        Assert.assertEquals("Too big for Double", new BigDecimal("1.7976931348623157e+309"),
                resolver.resolveValue("1.7976931348623157e+309"));
    }

    @Test
    public void testInstant() {
        Assert.assertEquals("Parsed DateTime value with offset",
                OffsetDateTime.of(2011, 12, 3, 9, 15, 30, 0, ZoneOffset.UTC).toInstant(),//ensure offset 1
                resolver.resolveValue("2011-12-03T10:15:30+01:00"));
    }

}