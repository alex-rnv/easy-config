package com.crossover.trial.properties;

import com.crossover.trial.properties.entity.Property;
import com.crossover.trial.properties.entity.PropertyFactory;
import com.crossover.trial.properties.source.SourceFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * <div>
 * Encapsulates whole engine lifecycle and holds all the data. This is main configuration engine entry.
 * Lifecycle consists of two stages, to avoid different collisions: initialization and exploit.
 * On initialization stage, it is only allowed to add new modules to enhance basic type resolution rules:<br/>
 * <pre>
 * {@code
 * Configuration configuration = new Configuration();
 * configuration.addModule(myModule);
 * configuration.addModule(new ExternalModule());
 * configuration.init();}
 * </pre>
 * After initialization, new modules are not allowed. New data sources could be submitted to configuration at any time
 * by calling
 * <pre>
 * {@code configuration.addSource(url);}
 * </pre>
 * where url is raw source url (should satisfy rules described in {@link SourceFactory}). Properties from this source
 * will appear in configuration, according to ConcurrentHashMap notation (not all at the same time are guaranteed,
 * thread safe though).<br/>
 * Configuration provides some helper methods to retrieve properties with known required type, like getInteger,
 * getBoolean. Method getObject will return raw object, method getString will return objects string representation
 * (according to toString()). Method getValue allows to return values for externally configurable types.
 * </div>
 * @author Alex
 */
public class Configuration {
    private static final Logger LOG = getLogger(Configuration.class);

    private volatile PropertyFactory propertyFactory = new PropertyFactory();
    private volatile SourceFactory sourceFactory;
    private volatile boolean isInitialized;
    private final Map<String, Property> propertyMap = new ConcurrentHashMap<>();

    public synchronized void init() {
        this.sourceFactory = new SourceFactory(propertyFactory);
        this.isInitialized  = true;
    }

    public synchronized void addSource(String src) throws IOException {
        if(!isInitialized) {
            throw new IllegalStateException("Configuration is not initialized");
        }
        sourceFactory.newSource(src).read().forEach((k,v) -> {
            Property p = propertyMap.put(k,v);
            if(p != null) {
                LOG.warn("Previous value was overridden: " + p);
            }
        });
    }

    public synchronized void addModule(Module module) {
        if(isInitialized) {
            throw new IllegalStateException("Not allowed to add modules after initialization");
        }
        LOG.debug("Module added " + module.getName());
        module.resolversMap.forEach((k,v) -> propertyFactory.addResolver(k,v));
    }

    public String getString(String name) {
        Object o = getObject(name);
        return o != null ? o.toString() : null;
    }

    public Integer getInteger(String name) {
        return getValue(name, Integer.class);
    }

    public Long getLong(String name) {
        return getValue(name, Long.class);
    }

    public BigInteger getBigInteger(String name) {
        return getValue(name, BigInteger.class);
    }

    public Float getFloat(String name) {
        return getValue(name, Float.class);
    }

    public Double getDouble(String name) {
        return getValue(name, Double.class);
    }

    public BigDecimal getBigDecimal(String name) {
        return getValue(name, BigDecimal.class);
    }

    public Instant getInstant(String name) {
        return getValue(name, Instant.class);
    }

    public <T> T getValue(String name, Class<T> tClass) {
        Property p = propertyMap.get(name);
        if(p == null) return null;
        return tClass.cast(p.getValue());
    }

    public Object getObject(String name) {
        Property p = propertyMap.get(name);
        if(p == null) return null;
        return p.getValue();
    }

    public Collection<Property> getAllProperties() {
        return Collections.unmodifiableCollection(propertyMap.values());
    }

}
