
package net.freedom.gj.mapper.config

class MappingInformation(mappingData:List[MappingData],
                         postProcessors:List[PostProcessor]=Nil) {     
  def getData = mappingData;
  def getProcessors = postProcessors;
}



case class MappingData(source:String,
                       target:String,
                       converter:Converter = null,
                       collection:List[MappingData] = Nil,
                       objType:String= null) {  
}


trait PostProcessor{
  def process(source:Any,target:Any)
}