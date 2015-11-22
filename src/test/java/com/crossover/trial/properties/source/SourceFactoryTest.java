package com.crossover.trial.properties.source;

import com.crossover.trial.properties.entity.PropertyFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Date: 11/22/2015
 * Time: 5:18 PM
 *
 * @author Alex
 */
public class SourceFactoryTest {

    private SourceFactory factory = new SourceFactory(new PropertyFactory());

    @Test(expected = NullPointerException.class)
    public void testNull() {
        factory.newSource(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        factory.newSource("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongPrefix() {
        factory.newSource("jdbc:mysql://localhost/test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongSuffix() {
        factory.newSource("file:///tmp/data.xml");
    }

    @Test
    public void testExpectPropFileReader() {
        assertTrue(factory.getReader("file:///config.properties") instanceof PropFileStreamReader);
    }

    @Test
    public void testExpectJsonReader() {
        assertTrue(factory.getReader("file:///config.json") instanceof JsonFileStreamReader);
    }

    @Test
    public void testExpectClasspathSource() {
        assertTrue(factory.newSource("classpath:resources/config.json") instanceof ClasspathSource);
    }

    @Test
    public void testExpectFileSource() {
        assertTrue(factory.newSource("file:///config.json") instanceof FileSource);
    }

    @Test
    public void testExpectHttpSource() {
        assertTrue(factory.newSource("http://config.json") instanceof HttpSource);
    }
}