package com.crossover.trial.properties.source;

import com.crossover.trial.properties.entity.Property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * Abstract interface with template methods
 * @author Alex
 */
abstract class AbstractSource implements Source {

    protected final String path;
    protected final StreamReader reader;

    protected AbstractSource(String path, StreamReader reader) {
        this.path = requireNonNull(path);
        this.reader = requireNonNull(reader);
    }

    @Override
    public Map<String, Property> read() throws IOException {
        try(InputStream stream = getStream(path)) {
            return reader.read(stream);
        }
    }

    abstract protected InputStream getStream(String path) throws IOException;
}
