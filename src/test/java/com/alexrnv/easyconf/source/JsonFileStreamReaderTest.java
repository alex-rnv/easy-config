package com.alexrnv.easyconf.source;

import com.alexrnv.easyconf.entity.PropertyFactory;
import com.alexrnv.easyconf.entity.Property;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Date: 11/22/2015
 * Time: 6:18 PM
 *
 * @author Alex
 */
public class JsonFileStreamReaderTest {

    private JsonFileStreamReader reader = new JsonFileStreamReader(new PropertyFactory());

    @Test
    public void testReader() throws IOException {
        ClasspathSource source = new ClasspathSource("config.json", reader);
        Map<String, Property> map = reader.read(source.getStream(source.path));
        assertEquals(7, map.size());
        assertEquals("https://authserver/v1/auth", map.get("auth.endpoint.uri").getValue());
        assertEquals(3600, map.get("job.timeout").getValue());
        assertEquals(4, map.get("job.maxretry").getValue());
        assertEquals("broadcast", map.get("sns.broadcast.topic_name").getValue());
        assertEquals(30, map.get("sns.broadcast.visibility_timeout").getValue());
        assertEquals(2.4, map.get("score.factor").getValue());
        assertEquals(false, map.get("jpa.showSql").getValue());
    }

}