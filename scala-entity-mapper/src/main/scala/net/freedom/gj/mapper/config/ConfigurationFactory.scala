
package net.freedom.gj.mapper.config

import scala.collection.mutable.ListBuffer

trait ConfigFactoryTrait[ConfigType, ContextType] {
  def getConfigurations(ctx: ContextType): List[ConfigType]
}


object SimpleConfigFactory extends ConfigFactoryTrait[MapperConfigTrait, ConfigContext] {
  private val list = new ListBuffer[MapperConfigTrait]

  def add(conf: MapperConfigTrait): Unit = {
    list += conf
  }

  def getConfigurations(ctx: ConfigContext): List[MapperConfigTrait] = {
    list.toList
  }
}