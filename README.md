Another solution to a complex object mapping challange. This project uses Expression Language Library to help with mapping.

**Entity mapper strengths:**

  1. Ability to map deeply structured objects.
  1. Ability to apply mapping from multiple definitions to map complex object.
  1. Ability to load mapping configuration based on context sensitive criteria at runtime.
  1. Ability to apply converter from source to target object.
  1. Ability to instantiate a target complex property if missing.
  1. Works on domain objects externally. No need to add any annotations.
  1. Preconfigured for Spring Framework or Guice IOC.

**Entity mapper weaknesses:**
  1. All properties are mapped explicitly, as defined by the configuration. 
  1. Mapping configuration corresponds to one way direction mapping. If you need to reverse the mapping, you will need to provide another configuration in reverse.

## Runtime Object Discovery 
This project includes another useful library object-criteria-factory. A runtime object injection or discovery based on dynamic criteria.

Standalone swing gui example project is available for an interactive demo.<br>
All of our projects are maven based and tested to work out of the box when building from the parent pom.<br>
<br>
<br>
<h2>let's see some code:</h2>

<table><thead><th><b>Code Link</b></th><th><b>Demonstrates</b></th></thead><tbody>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/java/net/freedom/gj/beans/PowerdBySpringMappingTest.java'>PowerdBySpringMappingTest.java</a></td><td> <i>Using Spring CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/java/net/freedom/gj/beans/PoweredByGuiceMappingTest.java'>PoweredByGuiceMappingTest.java</a></td><td><i>Using Guice CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/java/net/freedom/gj/beans/SimpleMappingTest.java'>SimpleMappingTest.java</a></td><td><i>Using no CDI environment</i></td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/resources/net/freedom/gj/beans/txt/beanA-to-beanB-mapping1.txt'>beanA-to-beanB-mapping1.txt</a></td><td><i>Example of simple custom text file</i></td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/resources/net/freedom/gj/beans/xml/beanA-to-beanB.xml'>beanA-to-beanB.xml</a></td><td><i>Example of basic xml file for mapping configuration</i></td></tr>
<tr><td><a href='https://github.com/martin-jamszolik/el-entity-mapper/tree/master/root/entity-mapper/src/test/resources/net/freedom/gj/beans/spring/beanA-to-beanB.xml'>beanA-to-beanB.xml</a></td><td><i>Example of spring xml file for mapping configuration</i></td></tr></tbody></table>

<h2>Common use cases</h2>

<ol><li>Migrating between legacy to new application models. Not all features have been re-implemented, forcing you to use legacy services, remotely communicating between the two applications.  Passing around information that has to be mapped.<br>
</li><li>You have a JPA persistence layer with JPA entity annotations. However you don't want to expose JPA at your business and UI layer. Map JPA entities against more suited business object models.<br>
</li><li>You have web services,tailored for specific client requirements. On a Web service side, you have JAXB annotated entities that as decorators for business entities.  Using this mapper you can synchronize JAXB input against business data model for you business service to process the request. Results would be mapped back to JAXB entities and returned to the caller.
