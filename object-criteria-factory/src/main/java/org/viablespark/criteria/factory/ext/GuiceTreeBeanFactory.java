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
package org.viablespark.criteria.factory.ext;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import java.util.ArrayList;
import java.util.List;

import org.viablespark.criteria.annotation.Criteria;
import org.viablespark.criteria.annotation.CriteriaList;
import org.viablespark.criteria.factory.AbstractTreeBeanFactory;

/**
 *
 * @author Martin Jamszolik
 */
public class GuiceTreeBeanFactory<BeanType, DataType> extends AbstractTreeBeanFactory<BeanType, DataType> {

    private Injector injector;

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    protected List<BeanType> findAllByType(Class<BeanType> type) {
        List<BeanType> list = new ArrayList<>();
        List<Binding<BeanType>> result = injector.findBindingsByType(TypeLiteral.get(type));
        for (Binding<BeanType> b : result) {
            BeanType bean = b.getProvider().get();
            if ( bean.getClass().isAnnotationPresent(Criteria.class) 
                    ||bean.getClass().isAnnotationPresent(CriteriaList.class) 
                    ||type.isInstance(bean) ) {
                list.add(bean);
            }
        }
        return list;
    }
}
