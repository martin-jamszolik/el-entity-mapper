package net.freedom.gj.mapper.config


import scala.collection.mutable.HashMap

trait MapperConfigTrait {
  val converters = new HashMap[String, Converter]
  def getMappingInformation: MappingInformation
}

trait ConfigContext {
  def source: Any
  def target: Any
}

trait Converter {
   def convert(subject: AnyRef): AnyRef
}
