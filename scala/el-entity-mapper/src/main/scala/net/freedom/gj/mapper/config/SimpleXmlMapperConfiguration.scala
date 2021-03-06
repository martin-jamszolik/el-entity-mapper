package net.freedom.gj.mapper.config

import scala.collection.mutable._
import scala.io.Source
import scala.xml.NodeSeq
import scala.xml.parsing.ConstructingParser

class SimpleXmlMapperConfig
(val file: String, val sourceType: String, val targetType: String) extends MapperConfigTrait {

  lazy val mi: MappingInformation = parse()

  override def getMappingInformation: MappingInformation = mi

  private def parse(): MappingInformation = {
    val resource = classOf[SimpleXmlMapperConfig].getResourceAsStream(file);
    val parser = ConstructingParser.fromSource(Source.fromInputStream(resource), false);
    val doc = parser.document()

    var list = new ListBuffer[MappingData]();
    for (element <- doc \ "bind") {
      list += getBindData(element)
    }
    for (element <- doc \ "collection") {
      var b = new ListBuffer[MappingData]()
      element.child.foreach(bind => b += getBindData(bind))
      list += new MappingData((element \ "@source").text,
        (element \ "@target").text, null, b, (element \ "@type").text)
    }
    val processorList= new ListBuffer[PostProcessor]()
    for (processors <- doc \ "post-processors") {
      processors.child.foreach(p => processorList += newInstance((p \ "@value").text, classOf[PostProcessor]))
    }
    new MappingInformation(list, processorList)
  }

  private def getBindData(el: NodeSeq): MappingData = {
    new MappingData((el \ "@source").text,
      (el \ "@target").text,
      getConverter((el \ "@converter").text))
  }
}
