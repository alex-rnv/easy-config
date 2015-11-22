package com.crossover.trial.properties.source;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Source implementation for local file system.
 * @author Alex
 */
class FileSource extends AbstractSource {

    FileSource(String path, StreamReader reader) {
        super(path, reader);
    }

    @Override
    protected InputStream getStream(String path) throws IOException {
        return Files.newInputStream(Paths.get(path));
    }
}
