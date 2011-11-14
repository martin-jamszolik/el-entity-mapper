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

import java.util.Date;
import junit.framework.Assert;
import net.freedom.gj.beans.criteria.MapContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author martin
 */
public class AnnotationDefaultTreeBeanFactoryTest {
    
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
        MyAnnotatedServiceA d = new MyAnnotatedServiceA();
        MyAnnotatedServiceB e = new MyAnnotatedServiceB();
        MyAnnotatedServiceC c = new MyAnnotatedServiceC();
        
        
        System.out.println("Beans Registered");
        DefaultTreeBeanFactory<MyService,Object> instance = new DefaultTreeBeanFactory<MyService,Object>();
        instance.setObjectType("net.freedom.gj.beans.factory.MyService");
        instance.register(d,e,c);
        
        instance.init();
        
        factory = instance;
    }
    
    @Test
    public void testFactoryUsingMapContext() throws Exception{
        
         // Service A
         MyService service = factory.getObject(new MapContext("payload","I am a string value") );
         Assert.assertEquals(MyAnnotatedServiceA.class, service.getClass());
         service.execute();
         
         // Service A again due to OR annotation.
         service = factory.getObject(new MapContext("payload",new Date(),"other","extra") );
         Assert.assertEquals(MyAnnotatedServiceA.class, service.getClass());         
         service.execute();
         
         service = factory.getObject(new MapContext("payload",new Integer(0),"switch","ON" ) );
         Assert.assertEquals(MyAnnotatedServiceB.class, service.getClass());
         service.execute();
         
         
         DataEntity pojo = new DataEntity("ABC","furniture");         
         service = factory.getObject(pojo);
         Assert.assertEquals(MyAnnotatedServiceC.class, service.getClass());
         service.execute();
         
    }
    
   
       
    
}
