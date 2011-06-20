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

import java.util.Date;
import javax.annotation.Resource;
import net.freedom.gj.beans.mapper.BeanMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Martin Jamszolik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
     "classpath:/net/freedom/gj/beans/spring/bean-mapper-config.xml",
     "classpath:/net/freedom/gj/beans/spring/test-mapping-configuration.xml"
})

public class PowerdBySpringMappingTest{

    @Resource
    BeanMapper mapper;

    @Test
    public void mappingOnMapObject(){

        EntityBeanA b1 = new EntityBeanA();
        b1.setMyDate(new Date());
        b1.setName("Testing property");
        b1.setAddress(new Address("123 Main st","","","Sarasota","FL","0000"));

        EntityBeanB b2 = new EntityBeanB();

        mapper.map(b1, b2);

        Assert.assertEquals(b1.getMyDate().toString(), (String)b2.getExtension().get("myDate") );
        Assert.assertEquals(b1.getName(), b2.getExtension().get("myName") );
        Assert.assertNotNull(b2.getMyAddress());
        Assert.assertEquals(b2.getMyAddress().getAddress1(), b1.getAddress().getLine1() );
    }

}
