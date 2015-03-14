Comprehensive solution to complex object mapping challenges. This project builds on top of Expression Language. Includes EL implementation by [JUEL](http://juel.sourceforge.net/). You are welcome to use your favorite EL library, but we recommend JUEL.

**Entity mapper strengths:**

  1. Ability to map deeply structured objects.
  1. Ability to apply mapping definitions from multiple sources to map complex object.
  1. Ability to load mapping configuration based on context sensitive criteria at runtime.
  1. Ability to apply converter from source to target object.
  1. Ability to instantiate a target complex property if resolves to NULL.
  1. Works on objects externally. No need to modify your domain specific objects with yet more annotations.

**Entity mapper weaknesses:**
  1. All properties are mapped explicitly, as defined by the configuration. In return, gaining performance as no extraneous reflection and processing is done on the objects.
  1. Single mapping configuration corresponds to one way direction mapping. If you need to reverse the mapping, you will provide another configuration with reversed source to target. So one configuration file does not represent bi-directional mapping.

## See more detail on wiki ##
[bean-criteria-factory](BeanCriteriaFactorySubproject.md)- _Explain the ability to dynamically determine and match right set of configuration instruction while mapping._


Standalone swing gui example project is available for demonstration.<br>
All of our projects are maven based and tested to work out of the box when building from the parent pom.<br>
<br>
<br>
<h2>let's see some code:</h2>

<table><thead><th><b>Code Link</b></th><th><b>Demonstrates</b></th></thead><tbody>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/java/net/freedom/gj/beans/PowerdBySpringMappingTest.java'>PowerdBySpringMappingTest.java</a></td><td> <i>Using Spring CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/java/net/freedom/gj/beans/PoweredByGuiceMappingTest.java'>PoweredByGuiceMappingTest.java</a></td><td><i>Using Guice CDI for auto registering mapping configurations</i> </td></tr>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/java/net/freedom/gj/beans/SimpleMappingTest.java'>SimpleMappingTest.java</a></td><td><i>Using no CDI environment</i></td></tr>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/resources/net/freedom/gj/beans/txt/beanA-to-beanB-mapping1.txt'>beanA-to-beanB-mapping1.txt</a></td><td><i>Example of simple custom text file</i></td></tr>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/resources/net/freedom/gj/beans/xml/beanA-to-beanB.xml'>beanA-to-beanB.xml</a></td><td><i>Example of basic xml file for mapping configuration</i></td></tr>
<tr><td><a href='http://code.google.com/p/el-entity-mapper/source/browse/root/entity-mapper/src/test/resources/net/freedom/gj/beans/spring/beanA-to-beanB.xml'>beanA-to-beanB.xml</a></td><td><i>Example of spring xml file for mapping configuration</i></td></tr></tbody></table>

<h2>Common use cases</h2>

<ol><li>You are migrating from legacy to next generation application. Not all features have been re-implemented, forcing you to use legacy services, remotely communicating between the two applications.  Passing around information that has to be translated in between.<br>
</li><li>You have a JPA persistence layer with JPA entity annotations. However you don't want to expose JPA at your business and UI layer. Map JPA entities against more suited business object models.<br>
</li><li>You have web services,tailored for specific client requirements. On a Web service side, you have JAXB annotated entities that do not resemble business entities.  Using this mapper you can synchronize JAXB input against business data model for you business service to process the request. Results would be mapped back to JAXB entities and returned to the caller.