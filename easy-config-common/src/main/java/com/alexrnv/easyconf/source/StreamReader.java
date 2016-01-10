package com.alexrnv.easyconf.source;

import com.alexrnv.easyconf.entity.Property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Reads properties from file in specified format.
 * @author Alex
 */
interface StreamReader {
    Map<String, Property> read(InputStream stream) throws IOException;
}
