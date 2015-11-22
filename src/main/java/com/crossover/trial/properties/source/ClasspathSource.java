package com.crossover.trial.properties.source;

import java.io.InputStream;

/**
 * Source implementation for classpath available files.
 * @author Alex
 */
class ClasspathSource extends AbstractSource {

    ClasspathSource(String path, StreamReader reader) {
        super(path, reader);
    }

    @Override
    protected InputStream getStream(String path) {
        return this.getClass().getClassLoader().getResourceAsStream(path);
    }

}
