package com.alexrnv.easyconf.entity;

/**
 * Resolved property data object
 * @author Alex
 */
public class Property {
    private final String name;
    private final Object value;

    public Property(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
