package net.freedom.gj.mapper.config

class SimpleXmlMapperConfig(val file:String, val source:String,val target:String) 
                                  extends MapperConfigTrait {
  
  var mappingInformation: MappingInformation =_
  
  def getMappingInformation(): MappingInformation = {

    if( mappingInformation != null ){
      return mappingInformation
    }
    
    mappingInformation = new MappingInformation(List(new MappingData("name","title"),new MappingData("age","years") ) );
    mappingInformation
  }

}
