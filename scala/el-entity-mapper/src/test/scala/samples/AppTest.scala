package samples

import org.junit._
import Assert._

import net.freedom.gj.mapper._
import net.freedom.gj.mapper.config._
import org.junit._
import Assert._
import scala.collection.mutable.ArrayBuffer
import scala.beans.BeanProperty

@Test
class AppTest {

  @Test
  def testMapper() = {
    
    val entityMapper = getEntityMapper
    
    val a = BeanA("martin","some descr",28)
    val b = BeanB("","",0)

    var result = entityMapper.map(a,b)
    
    println("Test Mapper result is :"+result)
    
    assertNotNull(result)
    assertEquals("martin",result.getTitle)
    assertEquals(28,result.getYears)
    
    a.setDesc("second time desc")
    a.setAge(31)
    
    result = entityMapper.map(a,b)
    println("Mapper second run result is :"+result)
    assertEquals("martin",result.getTitle)
    assertEquals(31,result.getYears)
    assertEquals("second time desc",result.getDescription)
    
  }


  private def getEntityMapper():EntityMapper = {     
   SimpleConfigFactory.add( 
      new SimpleXmlMapperConfig("/net/freedom/gj/beans/xml/beanA-to-beanB.xml"
                                ,"net.freedom.gj.mapper.BeanA","net.freedom.gj.mapper.BeanB") );     
   new EntityMapperImpl(SimpleConfigFactory) 
  }


}

case class BeanA(@BeanProperty var name:String,
                 @BeanProperty var desc:String, 
                 @BeanProperty var age:Int){
  @BeanProperty
  val groups:Seq[Group] = new ArrayBuffer();
}

case class BeanB(@BeanProperty var title:String,
                 @BeanProperty var description:String, 
                 @BeanProperty var years:Int){
  @BeanProperty
  val entityGroups:Seq[Group] = new ArrayBuffer();

}
case class Group(@BeanProperty var name:String,
                 @BeanProperty var rank:String)    


class ReportResultPostProcessor extends PostProcessor{    
  override def process(source:Any,target:Any):Unit = println("Post Process Invoked. Processing source:"+source
                                                        +", target:"+target)  
}


