package com.alexrnv.easyconf.source;

import com.alexrnv.easyconf.entity.Property;

import java.io.IOException;
import java.util.Map;

/**
 * @author Alex
 */
public interface Source {
    Map<String, Property> read() throws IOException;
}
