package com.crossover.trial.properties.entity;

/**
 * Interface describes data type transformation from simple object representation to data object.
 * Basic implementations should not throw exceptions, but fallback to return initial value. Custom
 * implementations though, may throw exceptions and act like data validators on initialization step.
 * @author Alex
 */
public interface ValueResolver {
    Object resolveValue(Object val);
}
