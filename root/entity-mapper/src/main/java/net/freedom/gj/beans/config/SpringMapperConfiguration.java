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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.freedom.gj.beans.factory.BeanCriteria;
import net.freedom.gj.beans.factory.CriteriaBuilder;
import net.freedom.gj.beans.factory.InstanceOfMatcher;
import net.freedom.gj.beans.factory.PropertyBuilder;
import net.freedom.gj.beans.factory.PropertyCriteria;
import net.freedom.gj.beans.factory.PropertyMatcher;
import net.freedom.gj.beans.mapper.MappingData;
import net.freedom.gj.beans.mapper.MappingInformation;
import net.freedom.gj.beans.mapper.PostProcessor;

/**
 * This is Spring implementation of MapperConfiguration. The mappings are defined in configurationFile. 
 * The sourceType and targetType information is used to determine when this configuration is applicable. 
 * @author Goutham Gogineni & Martin Jamszolik
 *
 *
 */
@SuppressWarnings("unchecked")
public class SpringMapperConfiguration implements MapperConfiguration, BeanCriteria, ApplicationContextAware {

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
    private ApplicationContext parentSpringContext;
    /**
     * Additional Criteria to select this bean.
     */
    MappingInformation mappingInformation;
    private Map<String, PropertyMatcher> additionalCriteria;
    private Map<String, ApplicationContext> cachedContext = new HashMap<String, ApplicationContext>();

    /**
     * This loads mapping information from configuration file and returns them.
     * @return Returns mapping information.
     */
    public MappingInformation getMappingInformation() {

        if (mappingInformation != null) {
            return mappingInformation;
        }

        ApplicationContext context = getContext(configurationFile);
        mappingInformation = new MappingInformation();
        mappingInformation.setMappingData((List<MappingData>) context.getBean("__mappingData"));
        try {
            mappingInformation.setPostProcessors((List<PostProcessor>) context.getBean("__postProcessors"));
        } catch (NoSuchBeanDefinitionException e) {
            // ignore
        }

        return mappingInformation;
    }

    public ApplicationContext getContext(String file) {

        if (cachedContext.containsKey(file)) {
            return cachedContext.get(file);
        } else {
            cachedContext.put(file, new ClassPathXmlApplicationContext(new String[]{file}, parentSpringContext));
            return cachedContext.get(file);
        }

    }

    /**
     * The criteria is the source object type has to be of sourceType and target object type has to be of type targetType.
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

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        parentSpringContext = context;
    }

    public void setAdditionalCriteria(
            Map<String, PropertyMatcher> additionalCriteria) {
        this.additionalCriteria = additionalCriteria;
    }
}
