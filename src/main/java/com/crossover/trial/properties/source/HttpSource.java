package com.crossover.trial.properties.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Source implementation for http endpoints.
 * @author Alex
 */
class HttpSource extends AbstractSource {

    HttpSource(String path, StreamReader reader) {
        super(path, reader);
    }

    @Override
    protected InputStream getStream(String path) throws IOException {
        URL url = new URL(path);
        return url.openStream();
    }
}
