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

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.viablespark.mapper.util.GuiceBeanMapperModule;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Martin Jamszolik
 */
public class PoweredByGuiceMappingTest {

    private BeanMapper getMapper() {
        Injector injector = Guice.createInjector(
                new GuiceBeanMapperModule(),
                new GuiceMapperConfigurationModule());
        return injector.getInstance(BeanMapper.class);
    }

    @Test
    public void mappingOnMapObject() {

        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setValue(BigDecimal.ONE);
        b1.setName("Testing Name Property");
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("EMPLOYEE", 21));
        groups.add(new Group("EMPADMIN", 1));
        b1.setGroups(groups);

        EntityBeanB b2 = new EntityBeanB();

        getMapper().map(b1, b2);

        assertEquals(b1.getMyDate().toString(), (String) b2.getExtension().get("myDate"));
        assertEquals(b1.getName(), b2.getExtension().get("myName"));
        assertEquals(b1.getValue(), b2.getExtension().get("myMoney"));
        assertTrue(b1.getGroups().size() == b2.getEntityGroups().size());
        assertEquals( b1.getGroups().get(0).getName(), b2.getEntityGroups().get(0).getGroupName() );
        assertEquals( b1.getGroups().get(0).getRank(), b2.getEntityGroups().get(0).getRank());
    }

}
