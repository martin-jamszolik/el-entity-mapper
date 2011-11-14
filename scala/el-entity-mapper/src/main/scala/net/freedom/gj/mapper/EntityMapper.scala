package net.freedom.gj.mapper

import javax.el._
import config._

trait EntityMapper {
  def map[T](source:AnyRef,target:T):T
}


class EntityMapperImpl(val configFactory: ConfigFactoryTrait[MapperConfigTrait,ConfigContext]) extends EntityMapper {

  private val expr = ExpressionFactory.newInstance();  

  def map[T](source:AnyRef, target:T ):T = {

    val configurations = configFactory.getConfigurations(
      new ConfigContext {def source = source; def target = target}   )
            
    // Get EL context with source and target objects configured
    val elContext = getElContext(source, target)
    
    for(config <- configurations) {
      val info = config.getMappingInformation
      info.getData.foreach(item => mapData(item,elContext,source.getClass) )
      
      if( info.getProcessors != null )
        info.getProcessors.foreach(processor => processor.process(source, target))
    }
    
    return target;
  }
  
  
  private def mapData(item:MappingData,elContext:ELContext,srcClass:Class[_]):Unit={
    try{
      var value = expr.createValueExpression(elContext, "${source."+item.source+"}",
                                             java.lang.Class.forName("java.lang.Object")).getValue(elContext)
    
      // Create target if it is null
      createTargetObject(item, elContext, value);
    
      // If the target is Array or Collection, loop through the collection
      if (item.collection != null && !item.collection.isEmpty ) {
        mapCollection(item, elContext, value)
        return
      }
    
      // Convert source value if a converter is specified 
      if( item.converter != null){
        value  = item.converter.convert(value)
      }
    
      //Copy source value to target
      expr.createValueExpression(elContext, "${target."+item.target+"}",value.getClass).setValue(elContext,value)     
    } catch {     
      case e: Exception => println("Map data Error:"+e.getMessage); throw e;
    }
    
  }
  
  private def mapCollection(item:MappingData,elCtx:ELContext,value:AnyRef)={
    
  }
  
  private def createTargetObject(item:MappingData,elCtx:ELContext,value:AnyRef)={
    
  }


  /**
   * Gets ELContext instance. It uses SimpleContext.
   * It also configures source and target objects with names source and target.
   * @param source Source object
   * @param target Target object
   * @return Returns ELContext object.
   */
  private def getElContext[T](source:Any,target:T):ELContext ={
    val context = new util.BeanMapperELContext()
    context.setSource(expr.createValueExpression(source, source.getClass ) )
    context.setTarget( expr.createValueExpression(target, target.getClass ) )
    context
  }

}


