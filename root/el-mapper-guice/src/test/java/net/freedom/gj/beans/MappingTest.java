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

import com.google.inject.Inject;
import com.googlecode.junice.JUniceRunner;
import com.googlecode.junice.annotation.GuiceModules;
import java.util.Date;
import net.freedom.gj.beans.factory.FactoryModule;
import net.freedom.gj.beans.mapper.BeanMapper;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Martin Jamszolik
 */

@RunWith(JUniceRunner.class)
@GuiceModules(modules={FactoryModule.class,MapperConfigurationModule.class})
public class MappingTest{

    
    
    @Inject
    BeanMapper mapper;
    
    

    @Test
    public void mappingOnMapObject(){

        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setName("Testing Name Property");
        EntityBeanB b2 = new EntityBeanB();
        
        mapper.map(b1, b2);

        assertEquals(b1.getMyDate().toString(), (String)b2.getExtension().get("myDate") );
        assertEquals(b1.getName(), b2.getExtension().get("myName") );
        
    }


//    private BeanMapper configureBeanMapper(){
//        SimpleFileMapperConfiguration fileConfig = new SimpleFileMapperConfiguration();
//        fileConfig.setConfigurationFile("/net/freedom/gj/beans/beanA-to-beanB.txt");
//        fileConfig.setSourceType("net.freedom.gj.beans.EntityBeanA");
//        fileConfig.setTargetType("net.freedom.gj.beans.EntityBeanB");
//
//        MapperConfigurationBeanFactory factory = new MapperConfigurationBeanFactory();
//        factory.add(fileConfig);
//
//        BeanMapperImpl beanMapper = new BeanMapperImpl();
//        beanMapper.setBeanFactory(factory);
//
//        return beanMapper;
//    }

}