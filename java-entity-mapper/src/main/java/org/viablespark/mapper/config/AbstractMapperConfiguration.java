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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.viablespark.mapper.converter.Converter;
import org.viablespark.criteria.BeanCriteria;
import org.viablespark.criteria.CriteriaBuilder;
import org.viablespark.criteria.PropertyBuilder;
import org.viablespark.criteria.PropertyCriteria;
import org.viablespark.mapper.MappingInformation;
import org.viablespark.criteria.matcher.InstanceOfMatcher;

/**
 *
 * @author Martin Jamszolik
 */
public abstract class AbstractMapperConfiguration implements MapperConfiguration, BeanCriteria {

    protected String configurationFile;
    protected MappingInformation mappingInformation;
    protected Class<?> sourceType;
    protected Class<?> targetType;
    protected Map<String, Converter> converters = new HashMap<>(4);


    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

    public void setSourceType(Class<?> sourceType) {
        this.sourceType = sourceType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }

    public Class<?> getSourceType() {
        return sourceType;
    }

    public Class<?> getTargetType() {
        return targetType;
    }
    
    

    protected Converter getConverter(String className) {
        if (className == null) {
            return null;
        }

        if (converters.containsKey(className)) {
            return converters.get(className);
        }
        try {
            var converter = Class.forName(className).getConstructor().newInstance();
            converters.put(className, (Converter)converter);
            return converters.get(className);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<PropertyCriteria> getCriteria() {
        return new CriteriaBuilder().build(new PropertyBuilder()
                .build("source", new InstanceOfMatcher(sourceType))
                .build("target", new InstanceOfMatcher(targetType))).getCriteria();
    }

}
