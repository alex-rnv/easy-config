package com.alexrnv.easyconf.entity;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

/**
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
        assertEquals("Empty string resolved to empty string", "", resolver.resolveValue(""));
    }

    @Test
    public void testBoolean() {
        assertEquals("Boolean ignore case", true, resolver.resolveValue("true"));
        assertEquals("Boolean ignore case", false, resolver.resolveValue("false"));
        assertEquals("Boolean ignore case", true, resolver.resolveValue("TRUE"));
        assertEquals("Boolean ignore case", false, resolver.resolveValue("FALSE"));
    }

    @Test
    public void testInteger() {
        assertEquals("Integer string", 1, resolver.resolveValue("1"));
        assertEquals("Integer string", 0, resolver.resolveValue("0"));
        assertEquals("Integer string", -1, resolver.resolveValue("-1"));
        assertEquals("Hex Integer string", 10, resolver.resolveValue("0xa"));
    }

    @Test
    public void testLong() {
        assertEquals("Long string", 1L, resolver.resolveValue("1L"));
        assertEquals("Long string", 0L, resolver.resolveValue("0L"));
        assertEquals("Long string", -1L, resolver.resolveValue("-1L"));
        assertEquals("Too big for Integer", 21474836470L, resolver.resolveValue("21474836470"));
    }

    @Test
    public void testBigInteger() {
        assertEquals("Too big for Long", new BigInteger("92233720368547758070"),
                resolver.resolveValue("92233720368547758070"));
    }

    @Test
    public void testFloat() {
        assertEquals("Float string", 1.f, resolver.resolveValue("1."));
        assertEquals("Float string", 1.0f, resolver.resolveValue("1.0"));
        assertEquals("Float string", -1.0f, resolver.resolveValue("-1.0"));
        assertEquals("Float scientific", 1e-3f, resolver.resolveValue("1e-3"));
        assertEquals("Float scientific", 1e4f, resolver.resolveValue("1e4"));
    }

    @Test
    public void testDouble() {
        assertEquals("Double string", 1., resolver.resolveValue("1.d"));
        assertEquals("Double string", 1.0, resolver.resolveValue("1.0d"));
        assertEquals("Double string", -1.0, resolver.resolveValue("-1.0d"));
        assertEquals("Double scientific", 1e-3, resolver.resolveValue("1e-3d"));
        assertEquals("Double scientific", 1e4, resolver.resolveValue("1e4d"));
        assertEquals("Too big for Float", 3.4028235e+39, resolver.resolveValue("3.4028235e+39"));
    }

    @Test
    public void testBigDecimal() {
        assertEquals("Too big for Double", new BigDecimal("1.7976931348623157e+309"),
                resolver.resolveValue("1.7976931348623157e+309"));
    }

    @Test
    public void testInstant() {
        assertEquals("Parsed DateTime value with offset",
                OffsetDateTime.of(2011, 12, 3, 9, 15, 30, 0, ZoneOffset.UTC).toInstant(),//ensure offset 1
                resolver.resolveValue("2011-12-03T10:15:30+01:00"));
    }

}