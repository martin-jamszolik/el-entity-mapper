/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.freedom.gj.beans.config;

import net.freedom.gj.beans.mapper.PostProcessor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.freedom.gj.beans.converter.Converter;
import net.freedom.gj.beans.mapper.MappingData;
import net.freedom.gj.beans.mapper.MappingInformation;
import net.freedom.gj.beans.util.Lg;
import static net.freedom.gj.beans.util.BasicHelper.resolveKey;

/**
 *
 * @author Martin Jamszolik
 */
public class SimpleFileMapperConfiguration extends AbstractMapperConfiguration {

    

    public MappingInformation getMappingInformation() {
        try {
            if (mappingInformation != null) {
                return mappingInformation;
            }

            mappingInformation = new MappingInformation();
            InputStream stream =this.getClass().getResourceAsStream(configurationFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader( stream ));

            String line = "";            
            List<MappingData> list = new ArrayList<MappingData>();
            while( (line = reader.readLine()) != null  ) {
                if( line.length() == 0 )
                    continue;
                
                if( line.contains("<collection") ){
                    handleCollection(reader,line,list);
                }else if( line.contains("<post-processors")){
                    handlePostProcessors(reader, line, mappingInformation);
                }else
                    list.add(getMappingData(line));
            }
            mappingInformation.setMappingData(list);
            reader.close();

        } catch (Exception ex) {
            Lg.log(this,Lg.ERROR, "Error", ex);
        }

        return mappingInformation;
    }

    private void handleCollection(BufferedReader reader,String line, List<MappingData>list ) throws Exception{
        MappingData data = new MappingData();
        data.setCollection(true);
        data.setCollectionObjectType(resolveKey("type=\"","\"",line));
        data.setSourceExpression(resolveKey("source=\"", "\"", line));
        data.setTargetExpression(resolveKey("target=\"", "\"", line));
         List<MappingData> coll = new ArrayList<MappingData>();
         while( (line = reader.readLine()) != null && !line.contains("</collection>") ) {
            coll.add(getMappingData(line));
         }
         data.setCollectionMappingData(coll);
         list.add(data);
    }

    private void handlePostProcessors(BufferedReader reader,String line,MappingInformation info ) throws Exception{        
         List<PostProcessor> coll = new ArrayList<PostProcessor>();
         while( (line = reader.readLine()) != null && !line.contains("</post-processors>") ) {
            String processClass = resolveKey("process=\"","\"",line);
            coll.add((PostProcessor)(Class.forName(processClass)).newInstance() );
         }
         info.setPostProcessors(coll);
    }


    private MappingData getMappingData(String conf) throws Exception {
        MappingData data = new MappingData();
        data.setSourceExpression(resolveKey("source=\"", "\"", conf));
        data.setTargetExpression(resolveKey("target=\"", "\"", conf));
        String converter = resolveKey("converter=\"", "\"", conf);
        if (converter != null) {            
            Converter instance = getConverter(converter);
            data.setConverter(instance);
        }
        return data;
    }

}
