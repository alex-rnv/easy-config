package com.crossover.trial.properties.entity;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory to create properties from raw values.
 * @author Alex
 */
public class PropertyFactory {

    private final Map<String, ValueResolver> resolversMap = new ConcurrentHashMap<>();
    private final ValueResolver defaultResolver = new DefaultValueResolver();

    public void addResolver(String propName, ValueResolver resolver) {
        Objects.requireNonNull(propName);
        Objects.requireNonNull(resolver);
        resolversMap.put(propName, resolver);
    }

    public Property newProperty(String name, Object rawValue) {
        ValueResolver resolver = resolversMap.getOrDefault(name, defaultResolver);
        return new Property(name, resolver.resolveValue(rawValue));
    }
}
