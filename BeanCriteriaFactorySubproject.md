# Bean Criteria Factory Subproject #

A library used by el-entity-mapper framework. Bean Criteria Factory is unique in a sense that you get a similar experience from frameworks such as OSGI or other modular systems but without the containers. You have an ability to register multiple services under the same interface along with their specific criteria (context). Then ask the factory to pick the implementation.  The criteria object can be any arbitrary object with getters, or you can use the convenient **MapContext** class to specify the criteria. At the end, the properties just have to match with the criteria that the service had been assigned with.


# Details #
<table><tr><td><a href='https://picasaweb.google.com/lh/photo/7cANxtP8MGW_j97ERIZnvNMTjNZETYmyPJy0liipFm0?feat=embedwebsite'><img src='https://lh5.googleusercontent.com/-p60bpzfObus/TydjLx8mU5I/AAAAAAAAAXE/IN4v1gIHwNU/s800/object-criteria-factory.png' height='527' width='800' /></a></td></tr><tr><td>From <a href='https://picasaweb.google.com/111183671876530949377/TechnologyScreenshots?authuser=0&feat=embedwebsite'>Technology screenshots</a></td></tr></table>

Most CDI frameworks solve this approach with compile time annotations (@Qualifiers) ex. Spring. But this library expands on that, and allows to register and then match on qualifiers during a runtime call, so the implementation is not known until a request is made. Furthermore, you can get a collection of valid implementation if applicable or desired. You have two ways of specifying the criteria, by annotation, or by implementing BeanCriteria interface. There are use cases for both approaches. Specially when you need the criteria to be dynamic also.

In case of el-entity-mapper we use this library to register MapperConfiguration class along with it's qualifiers(criteria) source type and target type. When time comes to map one bean to another, this library will return all valid `MapperConfiguration` classes applicable for mapping. each implementation will drive EL expression language mapping and apply it to the beans.

This is why we can have more then one MapperConfiguration type. XML,plain,spring.