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
package org.viablespark.mapper.config;

import org.viablespark.mapper.PostProcessor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.viablespark.mapper.converter.Converter;
import org.viablespark.mapper.MappingData;
import org.viablespark.mapper.MappingInformation;
import org.viablespark.mapper.util.Lg;
import static org.viablespark.mapper.util.BasicHelper.resolveKey;

/**
 *
 * @author Martin Jamszolik
 */
public class SimpleFileMapperConfiguration extends AbstractMapperConfiguration {

    

    public MappingInformation getMappingInformation() {
        if (mappingInformation != null) {
            return mappingInformation;
        }
        mappingInformation = new MappingInformation();

        try(InputStream resourceAsStream = this.getClass().getResourceAsStream(this.configurationFile)) {
            assert resourceAsStream != null;
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream)) ) {

                String line;
                List<MappingData> list = new ArrayList<>();
                while( (line = reader.readLine()) != null  ) {
                    if( line.length() == 0 )
                        continue;

                    if( line.contains("[") ){
                        handleCollection(reader,line,list);
                    }else if( line.contains("post-process")){
                        handlePostProcessors(reader, line, mappingInformation);
                    }else
                        list.add(getMappingData(line));
                }
                mappingInformation.setMappingData(list);
            }
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
         List<MappingData> coll = new ArrayList<>();
         while( (line = reader.readLine()) != null && !line.contains("]") ) {
            coll.add(getMappingData(line));
         }
         data.setCollectionMappingData(coll);
         list.add(data);
    }

    private void handlePostProcessors(BufferedReader reader,String line,MappingInformation info ) {
         String processClass = resolveKey("process=\"","\"",line);
         PostProcessor postProcess = (PostProcessor)(getInstance(processClass));
         info.addPostProcess(postProcess);
    }


    private MappingData getMappingData(String conf) {
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
