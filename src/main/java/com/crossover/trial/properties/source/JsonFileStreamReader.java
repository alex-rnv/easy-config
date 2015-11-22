package com.crossover.trial.properties.source;

import com.crossover.trial.properties.entity.Property;
import com.crossover.trial.properties.entity.PropertyFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Reader for ".json" files.
 * @author Alex
 */
class JsonFileStreamReader implements StreamReader {
    private static final Logger LOG = getLogger(JsonFileStreamReader.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PropertyFactory propertyFactory;

    public JsonFileStreamReader(PropertyFactory propertyFactory) {
        this.propertyFactory = requireNonNull(propertyFactory);
    }

    @Override
    public Map<String, Property> read(InputStream stream) throws IOException {
        HashMap<String, Object> raw = objectMapper.readValue(stream, HashMap.class);
        Map<String, Property> map = new HashMap<>();
        raw.forEach((k,val) -> {
            if(val instanceof List || val instanceof Map) {
                LOG.warn("Embedded objects and arrays are not supported in this version, skipping " + val);
            } else {
                Property p = propertyFactory.newProperty(k, val);
                LOG.debug("Read " + p);
                map.put(k, p);
            }
        });
        return map;
    }
}
