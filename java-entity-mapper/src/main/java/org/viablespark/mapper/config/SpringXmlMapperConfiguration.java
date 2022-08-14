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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import org.viablespark.mapper.MappingData;
import org.viablespark.mapper.MappingInformation;
import org.viablespark.mapper.PostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.viablespark.criteria.CriteriaBuilder;
import org.viablespark.criteria.PropertyBuilder;
import org.viablespark.criteria.PropertyCriteria;
import org.viablespark.criteria.matcher.PropertyMatcher;

/**
 * Spring framework required to use this class. Spring implementation of
 * MapperConfiguration. The mappings are defined in configurationFile. The
 * sourceType and targetType information is used to determine when this
 * configuration is applicable. This Implementation is useful when you want to
 * use live object and wire other concerns within Spring container as part of
 * Mapper Configuration. Otherwise You might want to look at simpler
 * alternatives.
 *
 * @see SimpleXmlMapperConfiguration
 * @see SimpleFileMapperConfiguration
 *
 *
 * @author Goutham Gogineni
 * @author Martin Jamszolik
 *
 *
 */
public class SpringXmlMapperConfiguration extends AbstractMapperConfiguration implements BeanFactoryAware {

    /**
     * Parent spring application context.
     */
    private BeanFactory parentSpringContext;

    private Map<String, PropertyMatcher> additionalCriteria;
    private final Map<String, BeanFactory> cachedContext = new HashMap<>();

    /**
     * This loads mapping information from configuration file and returns them.
     *
     * @return Returns mapping information.
     */
    @Override
    public MappingInformation getMappingInformation() {

        if (mappingInformation != null) {
            return mappingInformation;
        }

        BeanFactory context = getContext(configurationFile);
        mappingInformation = new MappingInformation();
        List<MappingData> data = context.getBean("__mappingData",List.class);
        mappingInformation.setMappingData(data);
        try {
            mappingInformation.setPostProcessors((List<PostProcessor>) context.getBean("__postProcessors"));
        } catch (NoSuchBeanDefinitionException e) {
            // ignore
        }

        return mappingInformation;
    }

    private BeanFactory getContext(String file) {

        if (cachedContext.containsKey(file)) {
            return cachedContext.get(file);
        } else {
            Resource res = new ClassPathResource(file);
            //XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(parentSpringContext.);
            cachedContext.put(file, new XmlBeanFactory(res, parentSpringContext));
            return cachedContext.get(file);
        }

    }

    /**
     * The criteria is the source object type has to be of sourceType and target
     * object type has to be of type targetType.
     *
     * @return
     */
    @Override
    public List<PropertyCriteria> getCriteria() {
        PropertyBuilder pb = new PropertyBuilder();
        if (additionalCriteria != null && !additionalCriteria.isEmpty()) {
            for (String property : additionalCriteria.keySet()) {
                pb.build(property, additionalCriteria.get(property));
            }
        }
        return new CriteriaBuilder(super.getCriteria()).build(pb).getCriteria();
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.parentSpringContext = beanFactory;
    }

    public void setAdditionalCriteria(
            Map<String, PropertyMatcher> additionalCriteria) {
        this.additionalCriteria = additionalCriteria;
    }
}
