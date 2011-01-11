package net.freedom.gj.mapper.config


import scala.collection.mutable.HashMap

trait MapperConfigurationTrait {

  val converters = new HashMap[String, Converter]

  def getMappingInformation: MappingInformation


}



trait Converter {

   def convert(subject: Any): Any
}
