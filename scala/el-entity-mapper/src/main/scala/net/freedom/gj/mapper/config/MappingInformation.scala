
package net.freedom.gj.mapper.config

class MappingInformation(mappingData:Seq[MappingData],
                         postProcessors:Seq[PostProcessor]=Nil) {     
  def getData = mappingData;
  def getProcessors = postProcessors;
}



case class MappingData(source:String,
                       target:String,
                       converter:Converter = null,
                       collection:Seq[MappingData] = Nil,
                       objType:String= null) {  
}


trait PostProcessor{
  def process(source:Any,target:Any)
}