package net.freedom.gj.mapper


import net.freedom.gj.mapper._
import net.freedom.gj.mapper.config._
import org.junit._
import Assert._
import scala.reflect.BeanProperty

@Test
class MapperTest {

  @Test
  def testMapper() = {

    val result = getEntityMapper.map(new BeanA("martin","test",28),new BeanB("","",0))

    println("Test Mapper result is :"+result)
    
    assertNotNull(result)
    assertEquals("martin",result.getTitle)
    assertEquals(28,result.getYears)

  }


  private def getEntityMapper():EntityMapper = {     
   SimpleConfigFactory.add( 
      new SimpleXmlMapperConfig("net/test/a-to-b.xml","net.text.A","net.test.B") );   
   new EntityMapperImpl(SimpleConfigFactory) 
  }


}

case class BeanA(@BeanProperty var name:String,@BeanProperty var desc:String, @BeanProperty var age:Int)
case class BeanB(@BeanProperty var title:String,@BeanProperty var description:String, @BeanProperty var years:Int)
