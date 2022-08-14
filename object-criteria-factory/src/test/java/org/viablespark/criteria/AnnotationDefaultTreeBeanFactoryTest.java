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
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author martin
 */
public class AnnotationDefaultTreeBeanFactoryTest {
    

    private DefaultTreeBeanFactory<MyService,Object> registerBeans() throws Exception{
       
        // create implementations
        MyAnnotatedServiceA d = new MyAnnotatedServiceA();
        MyAnnotatedServiceB e = new MyAnnotatedServiceB();
        MyAnnotatedServiceC c = new MyAnnotatedServiceC();
        
        
        System.out.println("Beans Registered");
        DefaultTreeBeanFactory<MyService,Object> instance = new DefaultTreeBeanFactory<MyService,Object>();
        instance.setObjectClass(org.viablespark.criteria.MyService.class);
        instance.register(d,e,c);
        
        instance.init();
        
        return instance;
    }
    
    @Test
    public void testFactoryUsingMapContext() throws Exception{
        
         var factory = registerBeans();
         // Service A
         MyService service = factory.getObject(new MapContext("payload","I am a string value") );
         assertEquals(MyAnnotatedServiceA.class, service.getClass());
         service.execute();
         
         // Service A again due to OR annotation.
         service = factory.getObject(new MapContext("payload",new Date(),"other","extra") );
         assertEquals(MyAnnotatedServiceA.class, service.getClass());         
         service.execute();
         
         service = factory.getObject(new MapContext("payload",0,"switch","ON" ) );
         assertEquals(MyAnnotatedServiceB.class, service.getClass());
         service.execute();
         
         
         DataEntity pojo = new DataEntity("ABC","furniture");         
         service = factory.getObject(pojo);
         assertEquals(MyAnnotatedServiceC.class, service.getClass());
         service.execute();
         
    }
    
   
       
    
}
