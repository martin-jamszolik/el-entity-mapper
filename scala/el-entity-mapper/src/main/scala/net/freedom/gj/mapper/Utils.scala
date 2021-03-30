package net.freedom.gj.mapper

import java.lang.reflect.Method
import javax.el._
import scala.collection.mutable.HashMap

class BeanMapperELContext extends ELContext {
  val READ_WRITE = new CompositeELResolver() {
    add(new ArrayELResolver(false))
    add(new ListELResolver(false))
    add(new MapELResolver(false))
    add(new ResourceBundleELResolver())
    add(new BeanELResolver(false))
  }
  val variableMapper = new VariableMapper() {
    val map = new HashMap[String, ValueExpression]

    def resolveVariable(variable: String): ValueExpression = map(variable)

    def setVariable(variable: String, expr: ValueExpression) = {
      map.put(variable, expr) match {
        case None => null
        case Some(x) => x
      }
    }
  }
  val functionMapper = new FunctionMapper() {
    def resolveFunction(prefix: String, localName: String): Method = {
      throw new UnsupportedOperationException("Function Not supported yet.")
    }
  }

  override def getELResolver() = READ_WRITE

  override def getVariableMapper() = variableMapper

  override def getFunctionMapper() = functionMapper

  def setSource(expr: ValueExpression) = variableMapper setVariable("source", expr)

  def setTarget(expr: ValueExpression) = variableMapper setVariable("target", expr)


}