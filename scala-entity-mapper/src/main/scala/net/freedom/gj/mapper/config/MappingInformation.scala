
package net.freedom.gj.mapper.config

import scala.collection.mutable._

class MappingInformation(mappingData: Seq[MappingData],
                         postProcessors: Seq[PostProcessor]) {
  def getData = mappingData;

  def getProcessors = postProcessors;
}


case class MappingData(source: String,
                       target: String,
                       converter: Option[Converter] = None,
                       collection: Seq[MappingData] = ListBuffer.empty[MappingData],
                       objType: String = null) {
}


trait PostProcessor {
  def process(source: Any, target: Any): Unit
}