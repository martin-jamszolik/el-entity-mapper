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

package org.viablespark.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.viablespark.criteria.factory.DefaultTreeBeanFactory;
import org.viablespark.mapper.config.MapperConfiguration;
import org.viablespark.mapper.config.SimpleFileMapperConfiguration;
import org.viablespark.mapper.config.SimpleXmlMapperConfiguration;


/**
 *
 * @author Martin Jamszolik
 */


public class MappingTest{

    

    @Test
    public void mappingOnMapObject(){

        // Populate object with some data
        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setName("Testing Name Property");
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("EMPLOYEE",21));
        groups.add(new Group("EMPADMIN",1));
        b1.setGroups(groups);
        b1.setValue(new BigDecimal("1000"));
        b1.setAddress(new Address("123 Main st","","","Sarasota","FL","0000"));


        // get an instance of bean mapper
        BeanMapper mapper = configureBeanMapper();

        // map object A to another object B
        EntityBeanB b2 = mapper.map(b1, new EntityBeanB());

        // Assert for correctness
        assertEquals(b1.getMyDate().toString(),b2.getExtension().get("myDate") );
        assertEquals(b1.getName(), b2.getExtension().get("myName") );
        assertNotNull(b2.getEntityGroups());
        assertEquals(b2.getEntityGroups().size(), b1.getGroups().size() );
        assertEquals( b2.getExtension().get("myMoney"), b1.getValue());
        assertNotNull(b2.getMyAddress());
        assertEquals(b2.getMyAddress().getAddress1(), b1.getAddress().getLine1() );
        
    }


    private BeanMapper configureBeanMapper(){
        // Optionally, use xml structured file.
        SimpleXmlMapperConfiguration fileConfig = new SimpleXmlMapperConfiguration();
        fileConfig.setConfigurationFile("/org/viablespark/beanA-to-beanB.xml");
        fileConfig.setSourceType(EntityBeanA.class);
        fileConfig.setTargetType(EntityBeanB.class);       

        //You can use multiple configuration files to map single complex object.
        //Optionally,using text custom format file.
        SimpleFileMapperConfiguration additionalConfig = new SimpleFileMapperConfiguration();
        additionalConfig.setConfigurationFile("/org/viablespark/beanA-to-beanB-mapping2.txt");
        additionalConfig.setSourceType(EntityBeanA.class);
        additionalConfig.setTargetType(EntityBeanB.class);  

        DefaultTreeBeanFactory<MapperConfiguration,MapperConfigurationContext> factory = new DefaultTreeBeanFactory<>();
        factory.setObjectClass(MapperConfiguration.class);
        factory.register(fileConfig,additionalConfig);

        BeanMapperImpl beanMapper = new BeanMapperImpl();
        beanMapper.setBeanFactory(factory);

        return beanMapper;
    }

}
