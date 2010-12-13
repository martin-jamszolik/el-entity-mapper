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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.freedom.gj.beans.converter.Converter;
import net.freedom.gj.beans.factory.BeanCriteria;
import net.freedom.gj.beans.factory.CriteriaBuilder;
import net.freedom.gj.beans.factory.InstanceOfMatcher;
import net.freedom.gj.beans.factory.PropertyBuilder;
import net.freedom.gj.beans.factory.PropertyCriteria;
import net.freedom.gj.beans.mapper.MappingData;
import net.freedom.gj.beans.mapper.MappingInformation;
import net.freedom.gj.beans.util.Lg;

/**
 *
 * @author Martin Jamszolik
 */
public class SimpleFileMapperConfiguration implements MapperConfiguration,BeanCriteria {

    private String configurationFile;
    private MappingInformation mappingInformation;
    private String sourceType;
    private String targetType;

    public MappingInformation getMappingInformation() {
        try {
            if (mappingInformation != null) {
                return mappingInformation;
            }

            mappingInformation = new MappingInformation();
            InputStream stream =this.getClass().getResourceAsStream(configurationFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader( stream ));

            String line = "";
            // start of mapping
            List<MappingData> list = new ArrayList<MappingData>();
            while( (line = reader.readLine()) != null &&  !line.contains("section>") ) {
                list.add(getMappingData(line));                
            }
            mappingInformation.setMappingData(list);

            reader.close();

        } catch (Exception ex) {
            Lg.log(this,Lg.ERROR, "Error", ex);
        }

        return mappingInformation;
    }

    private MappingData getMappingData(String conf) throws Exception {
        MappingData data = new MappingData();
        data.setSourceExpression(resolveKey("source=\"", "\"", conf));
        data.setTargetExpression(resolveKey("target=\"", "\"", conf));
        String converter = resolveKey("converter=\"", "\"", conf);
        if (converter != null) {
            //TODO: These can be singletons, stop creating instances per line item.
            Converter instance = (Converter) (Class.forName(converter)).newInstance();
            data.setConverter(instance);
        }

        return data;
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     *
     * @param key - indicator to where your value is stored
     * @param delimiter - indicator to where you stop extracting the value
     * @param ctx - body that contains the value you like to extract.
     * @return
     */
    public static String resolveKey(String key, String delimiter, String ctx) {

        if (ctx.contains(key)) {
            int ind = ctx.indexOf(key);
            String ans = ctx.substring(ind + key.length());
            ans = ans.substring(0, (ans.indexOf(delimiter) == -1 ? ans.length() : ans.indexOf(delimiter)));
            return ans;
        }
        return null;
    }

    public List<PropertyCriteria> getCriteria() {
        try {
            PropertyBuilder pb = new PropertyBuilder()
                    .build("source", new InstanceOfMatcher(Class.forName(sourceType)))
                    .build("target", new InstanceOfMatcher(Class.forName(targetType)));
            
            return new CriteriaBuilder().build(pb).getCriteria();
            
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(),e.getCause());
        }
    }
}
