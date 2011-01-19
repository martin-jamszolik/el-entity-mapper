package net.freedom.gj.mapper

import javax.el._
import config._
import net.freedom.gj.mapper.config.ConfigurationContext

trait EntityMapper {
  def map[T](source:Any,target:T):T
}


class EntityMapperImpl extends EntityMapper {

  val factory = ExpressionFactory.newInstance();
  var configurationFactory: ConfigurationFactoryTrait[MapperConfigurationTrait,ConfigurationContext] =_

  def map[T](source:Any, target:T ):T = {

    val configuration = configurationFactory.getConfigurations(new ConfigurationContext {def source = source; def target = target} )
    // Get EL context with source and target objects configured
    val elContext = getElContext(source, target)
    
    return target;
  }


  /**
     * Gets ELContext instance. It uses SimpleContext.
     * It also configures source and target objects with names source and target.
     * @param source Source object
     * @param target Target object
     * @return Returns ELContext object.
     */
//    private ELContext getElContext(Object source, Object target) {
//        BeanMapperELContext context = new BeanMapperELContext();
//
//        context.setSource(factory.createValueExpression(source, source.getClass()));
//        context.setTarget(factory.createValueExpression(target, target.getClass()));
//
//        return context;
//    }
//
      private def getElContext[T](source:Any,target:T):ELContext ={
        val context = new util.BeanMapperELContext()
        context.setSource(factory.createValueExpression(source, source.asInstanceOf[AnyRef].getClass ) )
        context.setTarget( factory.createValueExpression(target, target.asInstanceOf[AnyRef].getClass ) )
        context
      }

}


