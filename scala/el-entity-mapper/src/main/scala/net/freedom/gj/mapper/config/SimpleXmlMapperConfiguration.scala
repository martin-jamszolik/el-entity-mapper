package net.freedom.gj.mapper.config

class SimpleXmlMapperConfiguration(val file:String, val source:String,val target:String) 
                                  extends MapperConfigurationTrait {
  //var configurationFile: String =_
  var mappingInformation: MappingInformation =_
  
  def getMappingInformation(): MappingInformation = {

    if( mappingInformation != null ){
      return mappingInformation
    }

    mappingInformation = new MappingInformation

    mappingInformation
  }

}
