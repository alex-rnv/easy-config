package com.crossover.trial.properties.source;

import com.crossover.trial.properties.entity.Property;

import java.io.IOException;
import java.util.Map;

/**
 * @author Alex
 */
public interface Source {
    Map<String, Property> read() throws IOException;
}
