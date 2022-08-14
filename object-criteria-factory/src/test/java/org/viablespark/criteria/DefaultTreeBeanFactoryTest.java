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
package org.viablespark.criteria;

import org.viablespark.criteria.factory.DefaultTreeBeanFactory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author martin
 */
public class DefaultTreeBeanFactoryTest {
    
    private DefaultTreeBeanFactory<MyService,Object> registerBeans() throws Exception{
       
        // create implementations
        MyServiceA a = new MyServiceA();
        MyServiceB b = new MyServiceB();
        MyServiceC c = new MyServiceC();
        
        
        System.out.println("registerBean");
        DefaultTreeBeanFactory<MyService,Object> instance = new DefaultTreeBeanFactory<MyService,Object>();
        instance.setObjectType("org.viablespark.criteria.MyService");
        instance.register(a,b,c);
        
        instance.init();
        
        return instance;
    }
    
    @Test
    public void testFactoryUsingMapContext() throws Exception{
         var factory = registerBeans(); 
         MyService service = factory.getObject(new MapContext("payload","I am a string value") );
         assertEquals(MyServiceA.class, service.getClass());
         service.execute();
         
         service = factory.getObject(new MapContext("payload",Integer.valueOf(0),"switch","ON" ) );
         assertEquals(MyServiceB.class, service.getClass());
         service.execute();
         
         DataEntity pojo = new DataEntity("ABC","furniture");         
         service = factory.getObject(pojo);
         assertEquals(MyServiceC.class, service.getClass());
         service.execute();
         
    }
    
   
       
    
}
