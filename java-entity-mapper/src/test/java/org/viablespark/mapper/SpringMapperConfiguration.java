
package org.viablespark.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.viablespark.mapper.config.SimpleFileMapperConfiguration;
import org.viablespark.mapper.config.SpringXmlMapperConfiguration;

/**
 *
 * @author mjamsz
 */
@Configuration
@ImportResource("classpath:/org/viablespark/spring/bean-mapper-config.xml")
public class SpringMapperConfiguration {
    
    
    @Bean("springXml")
    public SpringXmlMapperConfiguration springMapperXml(){
        SpringXmlMapperConfiguration conf = new SpringXmlMapperConfiguration();
        conf.setSourceType(org.viablespark.mapper.EntityBeanA.class);
        conf.setTargetType(org.viablespark.mapper.EntityBeanB.class);
        conf.setConfigurationFile("/org/viablespark/beanA-to-beanB-spring.xml");
        return conf;
    }
    
    @Bean("simpleAdditionalFile1")
    public SimpleFileMapperConfiguration simpleMapperAdditionalFile1(){
        SimpleFileMapperConfiguration conf = new SimpleFileMapperConfiguration();
        conf.setSourceType(org.viablespark.mapper.EntityBeanA.class);
        conf.setTargetType(org.viablespark.mapper.EntityBeanB.class);
        conf.setConfigurationFile("/org/viablespark/beanA-to-beanB-additional-mapping.txt");
        return conf;        
    }
    
    @Bean("simpleFile2")
    public SimpleFileMapperConfiguration simpleMapperFile2(){
        SimpleFileMapperConfiguration conf = new SimpleFileMapperConfiguration();
        conf.setSourceType(org.viablespark.mapper.EntityBeanA.class);
        conf.setTargetType(org.viablespark.mapper.EntityBeanB.class);
        conf.setConfigurationFile("/org/viablespark/beanA-to-beanB-mapping1.txt");
        return conf;        
    }
}
