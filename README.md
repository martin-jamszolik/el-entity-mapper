## Java Entity Mapper

Another solution to a complex object mapping. This project uses an Expression Language Library to help with mapping.

**Entity mapper strengths:**

  1. Map deeply structured objects.
  1. Apply mapping from multiple definitions to map complex object.
  1. Load mapping configuration based on context sensitive criteria.
  1. Apply converters from source to target object.
  1. Instantiate a target complex property when missing.
  1. Works on domain objects externally. No need to add any annotations.
  1. Pre-configured for Spring Framework or Guice IOC integration.

**Entity mapper weaknesses:**
  1. All properties are mapped explicitly, as per configuration only. 
  1. Mapping configuration is one way only. If you need to reverse the mapping, you will need to provide another configuration.

## Dynamic Object Discovery 
This repo includes another useful library called object-criteria-factory. A runtime object locator or discovery based on dynamic or lazy binding criteria.

Imagine asking for a reference of a service under exotic criteria conditions, Here is a sample:

```java
@Criteria({
    @Matcher(property = "payload",matcher = InstanceOfMatcher.class,classValue = Integer.class),
    @Matcher(property = "switch",matcher = ToStringValueMatcher.class,stringValue = "ON") 
})
public class MyServiceSwitchimpl implements MyService {

    public void execute() {
        System.out.println("You got me to do your bidding");
    }

}

```
How do you use it once objects are configured with criteria?

```java
service = locator.getObject(new MapContext("payload",0,"switch","ON" ) );
assertEquals(MyServiceSwitchimpl.class, service.getClass());
service.execute();
```

Pretty cool right? 
Properties like `payload` or `switch` are evaluated on the visiting data object. It can by any object as long as it contains the expected properties.
Perhaps, annotations are not enough, you want the criteria to change dynamically at the time of evaluation:
* Per request or per thread context?
* Per database query?
* Per External Web service availability?
Implement `getCriteria()` as you see fit.

```java
    public List<PropertyCriteria> getCriteria() {
        return new CriteriaBuilder()
                .build(new PropertyBuilder()
                        .build("payload", new InstanceOfMatcher(Integer.class))
                        .build("switch", new PropertyValueEqualsMatcher("ON")))
                .getCriteria();
    }
```

We are using this library for the entity mapper configurations. It' gets interesting with more complex scenarios.

## Desktop GUI for Quick Demo

Standalone swing gui example project is available for an interactive demo. Just run `entity-mapper-demo` main class.<br>
All of our projects are maven based and tested to work out of the box when building from the parent pom.<br>
<br>
<br>
<h2>Let's see some code:</h2>

<table><thead><th><b>Code Link</b></th><th><b>Demonstrates</b></th></thead><tbody>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/blob/master/java-entity-mapper/src/test/java/org/viablespark/mapper/PowerdBySpringMappingTest.java'>PowerdBySpringMappingTest.java</a></td><td> <i>Using Spring CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/blob/master/java-entity-mapper/src/test/java/org/viablespark/mapper/PoweredByGuiceMappingTest.java'>PoweredByGuiceMappingTest.java</a></td><td><i>Using Guice CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/blob/master/java-entity-mapper/src/test/java/org/viablespark/mapper/SimpleMappingTest.java'>SimpleMappingTest.java</a></td><td><i>Using no CDI environment</i></td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/blob/master/java-entity-mapper/src/test/resources/org/viablespark/beanA-to-beanB-mapping1.txt'>beanA-to-beanB-mapping1.txt</a></td><td><i>Example of simple custom text file</i></td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/blob/master/java-entity-mapper/src/test/resources/org/viablespark/beanA-to-beanB.xml'>beanA-to-beanB.xml</a></td><td><i>Example of basic xml file for mapping configuration</i></td></tr>
</tbody>
</table>

<h2>Common use cases</h2>

<ol><li>Migrating between legacy to new application models. Not all features have been re-implemented, forcing you to use legacy services, remotely communicating between the two applications.  Passing around information that has to be mapped.<br>
</li><li>You have a JPA persistence layer with JPA entity annotations. However you don't want to expose JPA at your business and UI layer. Map JPA entities against more suited business object models.<br>
</li><li>You have web services, tailored for specific client requirements. On a Web service side, you have JAXB annotated entities that as decorators for business entities.  Using this mapper you can synchronize JAXB input against business data model for you business service to process the request. Results would be mapped back to JAXB entities and returned to the caller.
</ol>

## Would Scala implementation be more elegant?

Have been working on a scala implementation of this feature as well [scala-entity-mapper](https://github.com/martin-jamszolik/scala-entity-mapper).