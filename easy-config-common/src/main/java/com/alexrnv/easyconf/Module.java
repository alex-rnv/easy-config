package com.alexrnv.easyconf;

import com.alexrnv.easyconf.entity.ValueResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is an entry point to external configuration enhancements. One can implement separate modules and provide them
 * at initialization part. Modules should specify resolving behavior for specific properties.
 * @author Alex
 */
public class Module {
    private final String name;
    protected final Map<String, ValueResolver> resolversMap = new ConcurrentHashMap<>();

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addResolver(String propName, ValueResolver resolver) {
        resolversMap.put(propName, resolver);
    }

}