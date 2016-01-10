package com.alexrnv.easyconf.source;

import com.alexrnv.easyconf.entity.Property;
import com.alexrnv.easyconf.entity.PropertyFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Date: 11/22/2015
 * Time: 5:30 PM
 *
 * @author Alex
 */
public class PropFileStreamReaderTest {

    private PropFileStreamReader reader = new PropFileStreamReader(new PropertyFactory());

    @Test
    public void testReader() throws IOException {
        ClasspathSource source = new ClasspathSource("aws.properties", reader);
        Map<String, Property> map = reader.read(source.getStream(source.path));
        assertEquals(4, map.size());
        assertEquals("AKIAJSF6XRIJNJTTTL3Q", map.get("aws_access_key").getValue());
        assertEquals("pmqnweEYvdiw7cvCdTOES48sOUvK1rGvvctBsgsa", map.get("aws_secret_key").getValue());
        assertEquals(12345678, map.get("aws_account_id").getValue());
        assertEquals("us-east-1", map.get("aws_region_id").getValue());
    }

}