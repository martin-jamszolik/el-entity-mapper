package net.freedom.gj.mapper


import net.freedom.gj.mapper._
import net.freedom.gj.mapper.config.{SimpleConfigurationFactory,SimpleXmlMapperConfiguration}
import org.junit._
import Assert._

@Test
class MapperTest {

  @Test
  def testMapper() = {

    val result = getEntityMapper.map("test","test")

    println(result)
    
    assertNotNull(result)

  }


  private def getEntityMapper():EntityMapper = { 
   val mapper =  new EntityMapperImpl   
   SimpleConfigurationFactory.add( 
      new SimpleXmlMapperConfiguration("net/test/a-to-b.xml","net.text.A","net.test.B") );
   mapper.configurationFactory = SimpleConfigurationFactory
   mapper
  }


}
