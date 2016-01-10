--------------------------------------------------------
Crossover Properties Trial. Design Decisions & Issues
--------------------------------------------------------

Here I want to explain my view on the task, especially type safety requirement.
1. First, in real life scenario, I would insist on the rule: it is clients code responsibility to specify the type of
   the property at runtime. It can be implemented as following code sample:

   public interface Configuration {
        ...
        String getString(String propertyName);

        Boolean getBoolean(String propertyName);
        ...
   }

   All values are stored as raw strings, methods should throw runtime exception if string could not be parsed. This is
   the way Apache Commons Configuration works. This approach seems natural, as client seeking for particular property,
   should be aware of its value type. The shortcoming of this approach is data validation - you will know about broken
   configuration only at runtime. On the other hand, this solution is simpler to implement, especially considering
   embedded properties and arrays (p.3). With this solution we won't get required output (propertyName, propertyType,
   propertyValue), as we have no type related information in the model.

2. According to required task output (propertyName, propertyType, propertyValue), I assume that in our solution we want
   to know property value type after initialization, so property data structure should contain not only name and value,
   but data type too. Our solution should include some initialization step to retrieve property type from its raw value.
   I see two possible implementations here:

   2.1. First implementation stems from the fact that this is configuration we are dealing with, so the meaning and
    type of each property is predefined. We can talk about some metadata which describes at least known properties
    (analogous to XSD). It means that we can define some schema ("configuration of configuration"), or even DSL to
    configure our properties engines behaviour. The simplest approach is to have properties file with entries like

        aws_account_id=java.lang.Integer
        aws_region_id=com.amazonaws.regions.Regions
        jpa.showSql=java.lang.Boolean

    We do not need to specify String type here, as all unlisted properties are cast to it.

   2.1. Expose some API to allow clients implement some custom rules to parse expected values. This means that clients
    could implement some interface and bind this implementation to specific property name programmatically.
    Core implementation provides resolvers for common types and primitives. Clients could also provide their custom
    resolvers, like resolve to com.amazonaws.regions.Regions class, if property value matches "aws_region_id". In future
    versions, it is possible to create more sophisticated rules, like regex matching: "aws.*region.*". As a plus of this
    solution, it is flexible. Though, it is error prone and requires conflicts resolution (what if several
    ClassResolvers match).

    I've decided to go with this solution, after some thinking.

3. We do not consider support of embedded properties and lists of properties (while json file provides such opportunity)
   in this version.

4. Correctness. I think my solution will not pass all the tests, because of this output value:
        aws_region_id, com.amazonaws.regions.Regions, US_EAST_1
   which do not satisfy example:
        aws_region_id, com.amazonaws.regions.Regions, us-east-1.
   Anyway, I think my output is correct. And here are my thoughts. us-east-1 is just a simple string representation of
   the property, we could have gotten it any time after parsing the file. Instead, we've implemented whole engine to
   retrieve its real type and resolve it. So, the engine returns object, instead of string. And the output contains
   already resolved properties, not string representations. I was tempted no create new level of representation (like,
   new field *showValue* in Property class). But it doesn't look good, and doesn't solve any real life scenario problem,
   which is more important. So, we've gone long way to retrieve property real type, and should use it further.

---------------------------------





