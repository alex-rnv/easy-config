Easy-Config
-------------------------------

Initially it is a sample demo project, started as light-weight and easily extensible alternative to [apache commons configuration](https://commons.apache.org/proper/commons-configuration/). Main idea is to provide API to enhance configurable data types with user-defined types, not only String and primitives.   
1. It is clients code responsibility to specify the type of the property at runtime (same as apache configuration):   
```java
   public interface Configuration {
        ...
        String getString(String propertyName);
        Boolean getBoolean(String propertyName);
        ...
   }
```
All values are stored as raw strings, methods should throw runtime exception if string could not be parsed. This approach seems natural, as client seeking for particular property, should be aware of its value type. The only shortcoming of this approach is data validation - you will know about broken configuration only at runtime.   

2. Library exposes API to allow clients implement some custom rules to parse expected values.
TBD





