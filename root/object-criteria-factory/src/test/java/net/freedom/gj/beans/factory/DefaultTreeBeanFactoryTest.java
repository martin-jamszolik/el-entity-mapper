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
package net.freedom.gj.beans.factory;

import net.freedom.gj.beans.criteria.MapContext;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 *
 * @author martin
 */
public class DefaultTreeBeanFactoryTest {
    
    BeanFactory<MyService,Object> factory;
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerBean method, of class DefaultTreeBeanFactory.
     */
    @Before
    public void registerBeans() throws Exception{
       
        // create implementations
        MyServiceA a = new MyServiceA();
        MyServiceB b = new MyServiceB();
        MyServiceC c = new MyServiceC();
        
        
        System.out.println("registerBean");
        DefaultTreeBeanFactory<MyService,Object> instance = new DefaultTreeBeanFactory<MyService,Object>();
        instance.setObjectType("net.freedom.gj.beans.factory.MyService");
        instance.register(a,b,c);
        
        instance.init();
        
        factory = instance;
    }
    
    @Test
    public void testFactoryUsingMapContext() throws Exception{
        
         MyService service = factory.getObject(new MapContext("payload","I am a string value") );
         
         service.execute();
         
         service = factory.getObject(new MapContext("payload",new Integer(0),"switch","ON" ) );
        
         service.execute();
         
         DataEntity pojo = new DataEntity("ABC","furniture");
         
         service = factory.getObject(pojo);
         
         service.execute();
         
    }
    
   
       
    
}
