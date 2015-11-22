package com.crossover.trial.properties.source;

import com.crossover.trial.properties.entity.Property;
import com.crossover.trial.properties.entity.PropertyFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Reader for ".properties" files.
 * @author Alex
 */
class PropFileStreamReader implements StreamReader {
    private static final Logger LOG = getLogger(PropFileStreamReader.class);

    private final PropertyFactory propertyFactory;

    PropFileStreamReader(PropertyFactory propertyFactory) {
        this.propertyFactory = requireNonNull(propertyFactory);
    }

    @Override
    public Map<String, Property> read(InputStream stream) throws IOException {
        Properties props = new Properties();
        props.load(stream);
        Map<String, Property> map = new ConcurrentHashMap<>();
        props.stringPropertyNames().forEach(propName -> {
            String propValue = props.getProperty(propName);
            Property p = propertyFactory.newProperty(propName, propValue);
            LOG.debug("Read " + p);
            map.put(propName, p);
        });
        return map;
    }
}
