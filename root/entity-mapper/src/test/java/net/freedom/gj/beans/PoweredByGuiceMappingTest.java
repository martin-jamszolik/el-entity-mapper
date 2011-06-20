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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.freedom.gj.beans.mapper.BeanMapper;
import net.freedom.gj.beans.util.GuiceBeanMapperModule;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Martin Jamszolik
 */

@RunWith(JUniceRunner.class)
@GuiceModules(modules={GuiceBeanMapperModule.class,GuiceMapperConfigurationModule.class})
public class PoweredByGuiceMappingTest{
   
    
    @Inject
    BeanMapper mapper;  

    @Test
    public void mappingOnMapObject(){

        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setName("Testing Name Property");
        List<Group> groups = new ArrayList<Group>();
        groups.add(new Group("EMPLOYEE",21));
        groups.add(new Group("EMPADMIN",1));
        b1.setGroups(groups);

        EntityBeanB b2 = new EntityBeanB();
        
        mapper.map(b1, b2);

        assertEquals(b1.getMyDate().toString(), (String)b2.getExtension().get("myDate") );
        assertEquals(b1.getName(), b2.getExtension().get("myName") );
        
    }

}
