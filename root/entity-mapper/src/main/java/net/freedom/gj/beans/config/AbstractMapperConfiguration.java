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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.freedom.gj.beans.converter.Converter;
import net.freedom.gj.beans.factory.BeanCriteria;
import net.freedom.gj.beans.factory.CriteriaBuilder;
import net.freedom.gj.beans.factory.InstanceOfMatcher;
import net.freedom.gj.beans.factory.PropertyBuilder;
import net.freedom.gj.beans.factory.PropertyCriteria;
import net.freedom.gj.beans.mapper.MappingInformation;

/**
 *
 * @author Martin Jamszolik
 */
public abstract class AbstractMapperConfiguration implements MapperConfiguration,BeanCriteria {
    protected String configurationFile;
    protected MappingInformation mappingInformation;
    protected String sourceType;
    protected String targetType;
    protected Map<String,Converter> converters = new HashMap<String,Converter>(4);

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

    
    protected Converter getConverter(String className) {
        if ( className == null )
            return null;

        if (converters.containsKey(className)) {
            return converters.get(className);
        }
        try {
            converters.put(className, (Converter) (Class.forName(className)).newInstance());
            return converters.get(className);
        } catch (Exception ex) {
            return null;
        }
    }


     public List<PropertyCriteria> getCriteria() {
        try {
            return new CriteriaBuilder().build(new PropertyBuilder()
                    .build("source", new InstanceOfMatcher(Class.forName(sourceType)))
                    .build("target", new InstanceOfMatcher(Class.forName(targetType)))).getCriteria();

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(),e.getCause());
        }
    }

}
