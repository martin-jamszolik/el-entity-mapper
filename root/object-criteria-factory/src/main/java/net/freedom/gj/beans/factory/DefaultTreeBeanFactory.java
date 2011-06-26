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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.freedom.gj.beans.annotation.Criteria;
import net.freedom.gj.beans.criteria.BeanCriteria;

/**
 * Basic Implementation of BeanFactory. Ideal for use where IOC container is not
 * available. Configuration of the Factory will require manual approach.  
 * The inverse of this would be an implementation backed by Spring or Guice IOC. where
 * the configuration would be more dynamic and at runtime.
 *  
 * 
 * @author Martin Jamszolik
 */
public class DefaultTreeBeanFactory<BeanType, DataType> extends AbstractTreeBeanFactory<BeanType, DataType> {

    private Set<Object> beans = new HashSet<Object>();
    
    public void register(Object... items ){
        beans.addAll(Arrays.asList(items));
    }    
    
    @Override
    protected List<Object> findAllByType(Class type) {
        List<Object> list = new ArrayList<Object>();
        for( Object b: beans){
            if( b.getClass().isAnnotationPresent(Criteria.class)
                    || type.isInstance(b) ){
                list.add( b );
            }
        }        
        return list;        
    }
    
}
