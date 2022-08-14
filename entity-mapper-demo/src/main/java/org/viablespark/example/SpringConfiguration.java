
package org.viablespark.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.viablespark.mapper.config.SimpleXmlMapperConfiguration;
import org.viablespark.mapper.config.SpringXmlMapperConfiguration;

/**
 *
 * @author mjamsz
 */
@Configuration
@ImportResource("classpath:/org/viablespark/spring/bean-mapper-config.xml")
@ComponentScan(basePackages = "org.viablespark")
public class SpringConfiguration {
    
    
    @Bean("springXml")
    public SimpleXmlMapperConfiguration springMapperXml(){
    
        SimpleXmlMapperConfiguration conf = new SimpleXmlMapperConfiguration();
        conf.setSourceType(org.viablespark.example.pojo.Employee.class);
        conf.setTargetType(org.viablespark.example.jpa.EmployeeEntity.class);
        conf.setConfigurationFile("/example/simple-pojo-to-jpa.xml");
        return conf;
    }
    
}
