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

import net.freedom.gj.beans.criteria.BeanCriteria;
import net.freedom.gj.beans.criteria.CriteriaBuilder;
import net.freedom.gj.beans.criteria.PropertyBuilder;
import net.freedom.gj.beans.criteria.PropertyCriteria;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import net.freedom.gj.beans.mapper.MappingData;
import net.freedom.gj.beans.mapper.MappingInformation;
import net.freedom.gj.beans.mapper.PostProcessor;
import net.freedom.gj.beans.matcher.InstanceOfMatcher;
import net.freedom.gj.beans.matcher.PropertyMatcher;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Spring framework required to use this class.
 * Spring implementation of MapperConfiguration.
 * The mappings are defined in configurationFile.
 * The sourceType and targetType information is used to
 * determine when this configuration is applicable. This Implementation is useful
 * when you want to use live object and wire other concerns within Spring container
 * as part of Mapper Configuration.  Otherwise You might want to look at simpler 
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

public class SpringXmlMapperConfiguration implements MapperConfiguration, BeanCriteria, BeanFactoryAware {

    /**
     * Mappings are defined in this configuration file.
     */
    private String configurationFile;
    /**
     * The source object type for which this configuration is applicable.
     */
    private String sourceType;
    /**
     * the target object type for which this configuration is applicable.
     */
    private String targetType;
    /**
     * Parent spring application context.
     */
    private BeanFactory parentSpringContext;
    /**
     * Additional Criteria to select this bean.
     */
    MappingInformation mappingInformation;
    private Map<String, PropertyMatcher> additionalCriteria;
    private Map<String, BeanFactory> cachedContext = new HashMap<String, BeanFactory>();

    /**
     * This loads mapping information from configuration file and returns them.
     * @return Returns mapping information.
     */
    public MappingInformation getMappingInformation() {

        if (mappingInformation != null) {
            return mappingInformation;
        }

        BeanFactory context = getContext(configurationFile);
        mappingInformation = new MappingInformation();
        mappingInformation.setMappingData((List<MappingData>) context.getBean("__mappingData"));
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
            cachedContext.put(file, new XmlBeanFactory(res, parentSpringContext));
            return cachedContext.get(file);
        }

    }

    /**
     * The criteria is the source object type has to be of sourceType
     * and target object type has to be of type targetType.
     * @return
     */
    public List<PropertyCriteria> getCriteria() {
        try {
            PropertyBuilder pb = new PropertyBuilder()
                    .build("source", new InstanceOfMatcher(Class.forName(sourceType)))
                    .build("target", new InstanceOfMatcher(Class.forName(targetType)));
            if (additionalCriteria != null && !additionalCriteria.isEmpty()) {
                for (String property : additionalCriteria.keySet()) {
                    pb.build(property, additionalCriteria.get(property));
                }
            }
            return new CriteriaBuilder().build(pb).getCriteria();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.parentSpringContext = beanFactory;
    }

    public void setAdditionalCriteria(
            Map<String, PropertyMatcher> additionalCriteria) {
        this.additionalCriteria = additionalCriteria;
    }
}
