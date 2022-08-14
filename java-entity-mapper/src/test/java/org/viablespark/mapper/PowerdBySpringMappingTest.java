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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Martin Jamszolik
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringMapperConfiguration.class)
public class PowerdBySpringMappingTest{

    @Autowired
    BeanMapper mapper;

    @Test
    public void mappingOnMapObject(){

        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setName("Testing property");
        b1.setAddress(new Address("123 Main st","","","Sarasota","FL","0000"));
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("EMPLOYEE-SP", 30));
        groups.add(new Group("EMPADMIN-SP", 2));
        b1.setGroups(groups);

        EntityBeanB b2 = new EntityBeanB();

        b2 = mapper.map(b1, b2);

        assertEquals(b1.getMyDate().toString(), (String)b2.getExtension().get("myDate") );
        assertEquals(b1.getName(), b2.getExtension().get("myName") );
        assertNotNull(b2.getMyAddress());
        assertEquals(b2.getMyAddress().getAddress1(), b1.getAddress().getLine1() );
        assertTrue(b1.getGroups().size() == b2.getEntityGroups().size());
        assertEquals( b1.getGroups().get(0).getName(), b2.getEntityGroups().get(0).getGroupName() );
        assertEquals( b1.getGroups().get(0).getRank(), b2.getEntityGroups().get(0).getRank());
    }

}
