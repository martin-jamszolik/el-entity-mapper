
package net.freedom.gj.mapper.config

trait ConfigurationFactoryTrait[ConfigurationType,ContextType] {
  def getConfigurations(ctx: ContextType): Set[ConfigurationType]
}


object SimpleConfigurationFactory extends ConfigurationFactoryTrait[MapperConfigurationTrait,ConfigurationContext] {

  private var list: List[MapperConfigurationTrait] =_

  def add( conf: MapperConfigurationTrait):Unit = list = conf :: list

  def getConfigurations(ctx: ConfigurationContext) : Set[MapperConfigurationTrait] = {
    
    null
  }

}