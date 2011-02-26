
package net.freedom.gj.mapper.config

import scala.collection.mutable.ListBuffer

trait ConfigurationFactoryTrait[ConfigurationType,ContextType] {
  def getConfigurations(ctx: ContextType): List[ConfigurationType]
}


object SimpleConfigurationFactory extends ConfigurationFactoryTrait[MapperConfigurationTrait,ConfigurationContext] {
  private val list = new ListBuffer[MapperConfigurationTrait]

  def add( conf: MapperConfigurationTrait):Unit = {
    list += conf
  }
  
  def getConfigurations(ctx: ConfigurationContext) : List[MapperConfigurationTrait] = {
    
    list.toList
  }

}