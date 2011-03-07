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

package net.freedom.gj.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.freedom.gj.beans.config.SimpleFileMapperConfiguration;
import net.freedom.gj.beans.config.SimpleXmlMapperConfiguration;
import net.freedom.gj.beans.factory.MapperConfigurationBeanFactory;
import net.freedom.gj.beans.mapper.BeanMapper;
import net.freedom.gj.beans.mapper.BeanMapperImpl;
import static org.junit.Assert.*;
import org.junit.Test;

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
        List<Group> groups = new ArrayList<Group>();
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
        assertEquals(b1.getMyDate().toString(), (String)b2.getExtension().get("myDate") );
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
        fileConfig.setConfigurationFile("/net/freedom/gj/beans/xml/beanA-to-beanB.xml");
        fileConfig.setSourceType("net.freedom.gj.beans.EntityBeanA");
        fileConfig.setTargetType("net.freedom.gj.beans.EntityBeanB");       

        //You can use multiple configuration files to map single complex object.
        //Optionally,using text custom format file.
        SimpleFileMapperConfiguration additionalConfig = new SimpleFileMapperConfiguration();
        additionalConfig.setConfigurationFile("/net/freedom/gj/beans/txt/beanA-to-beanB-mapping2.txt");
        additionalConfig.setSourceType("net.freedom.gj.beans.EntityBeanA");
        additionalConfig.setTargetType("net.freedom.gj.beans.EntityBeanB");

        //Simple factory, suitable where no CDI capability available.
        MapperConfigurationBeanFactory factory = new MapperConfigurationBeanFactory();
        factory.add(fileConfig);
        factory.add(additionalConfig);

        BeanMapperImpl beanMapper = new BeanMapperImpl();
        beanMapper.setBeanFactory(factory);

        return beanMapper;
    }

}
