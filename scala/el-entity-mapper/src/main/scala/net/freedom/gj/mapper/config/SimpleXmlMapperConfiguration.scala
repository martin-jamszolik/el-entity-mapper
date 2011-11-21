package net.freedom.gj.mapper.config

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.xml.NodeSeq
import scala.xml.parsing.ConstructingParser

class SimpleXmlMapperConfig
(val file:String, val sourceType:String,val targetType:String) extends MapperConfigTrait {
  
  lazy val mappingInformation: MappingInformation = parse 
  
  override def getMappingInformation(): MappingInformation =  mappingInformation  
    
  private def parse():MappingInformation = {   
    val resource = classOf[SimpleXmlMapperConfig].getResourceAsStream(file);
    val parser = ConstructingParser.fromSource(Source.fromInputStream(resource), false); 
    val doc = parser.document()
    
    var list:ArrayBuffer[MappingData] = new ArrayBuffer();
    for(val element <- doc \ "bind") {      
      list += getBindData(element)
    }    
    for( element <- doc \ "collection"){
      var b:ArrayBuffer[MappingData] = new ArrayBuffer()  
      element.child.foreach(bind => b += getBindData(bind))      
      list += new MappingData((element \ "@source").text,
                              (element \ "@target").text,null,b,(element \ "@type").text)
    }
    var procs:ArrayBuffer[PostProcessor] = new ArrayBuffer()
    for( processors <-doc \ "post-processors"){      
      processors.child.foreach( p => procs += newInstance((p \ "@value").text,classOf[PostProcessor]) )
    }               
    new MappingInformation( list,procs)
  }
    
  private def getBindData( el:NodeSeq):MappingData ={
    new MappingData( (el \ "@source").text,
                    (el \ "@target").text,
                    getConverter((el \ "@converter").text) )
  }
}
