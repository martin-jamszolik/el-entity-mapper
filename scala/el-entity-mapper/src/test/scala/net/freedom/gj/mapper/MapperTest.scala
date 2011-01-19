

package net.freedom.gj.mapper.test

import net.freedom.gj.mapper.config.SimpleConfigurationFactory
import net.freedom.gj.mapper._
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
   mapper.configurationFactory = SimpleConfigurationFactory
   
   mapper
  }


}
