package net.freedom.gj.mapper.config


import scala.collection.mutable.HashMap

trait MapperConfigTrait {
  val converters = new HashMap[String, Converter]
  def getMappingInformation: MappingInformation
  
  protected def getConverter( converter:String ):Converter= {
    //TODO: implement this
    return null
  }
  protected def newInstance[T](impl:String,cl: Class[T]):T = {
    val clazz = Class.forName(impl)
    clazz.newInstance.asInstanceOf[T]
  }  
}

trait ConfigContext {
  def source: Any
  def target: Any
}

trait Converter {
   def convert(subject: AnyRef): AnyRef
}
