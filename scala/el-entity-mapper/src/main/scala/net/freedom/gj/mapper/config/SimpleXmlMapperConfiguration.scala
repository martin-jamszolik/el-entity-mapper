

package net.freedom.gj.mapper.config

class SimpleXmlMapperConfiguration extends MapperConfigurationTrait {
  var configurationFile: String =_
  var mappingInformation: MappingInformation =_
  var sourceType: String= _
  var targetType: String= _

  def getMappingInformation(): MappingInformation = {

    if( mappingInformation != null ){
      return mappingInformation
    }

    mappingInformation = new MappingInformation

    mappingInformation
  }

}
