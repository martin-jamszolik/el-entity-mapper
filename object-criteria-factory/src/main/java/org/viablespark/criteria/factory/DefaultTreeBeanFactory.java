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
package org.viablespark.criteria.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.viablespark.criteria.annotation.Criteria;
import org.viablespark.criteria.annotation.CriteriaList;

/**
 * Basic Implementation of BeanFactory.Ideal for use where IOC container is not
 available.Configuration of the Factory will require manual registry. 
 * 
 * @author Martin Jamszolik
 * @param <BeanType>
 * @param <DataType>
 */
public class DefaultTreeBeanFactory<BeanType, DataType> extends AbstractTreeBeanFactory<BeanType, DataType> {

    private final Set<BeanType> beans = new HashSet<>();
    
    @SafeVarargs
    public final void register( final BeanType... items ){
        Arrays.stream(items).forEach( item -> beans.add(item));
    }    
    
    @Override
    protected List<BeanType> findAllByType(Class type) {
        List<BeanType> list = new ArrayList<>();
        for( BeanType b: beans){
            if( b.getClass().isAnnotationPresent(Criteria.class)
                    || b.getClass().isAnnotationPresent(CriteriaList.class)
                    || type.isInstance(b) ){
                list.add( b );
            }
        }        
        return list;        
    }
    
}
