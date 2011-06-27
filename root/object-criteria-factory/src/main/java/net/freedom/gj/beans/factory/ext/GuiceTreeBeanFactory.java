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
package net.freedom.gj.beans.factory.ext;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import java.util.ArrayList;
import java.util.List;

import net.freedom.gj.beans.annotation.Criteria;
import net.freedom.gj.beans.factory.AbstractTreeBeanFactory;

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
    protected List<Object> findAllByType(Class type) {
        List<Object> list = new ArrayList<Object>();
        List<Binding<Object>> result = injector.findBindingsByType(TypeLiteral.get(type));
        for (Binding<Object> b : result) {
            Object bean = b.getProvider().get();
            if ( bean.getClass().isAnnotationPresent(Criteria.class) || type.isInstance(bean) ) {
                list.add(bean);
            }
        }
        return list;
    }
}
