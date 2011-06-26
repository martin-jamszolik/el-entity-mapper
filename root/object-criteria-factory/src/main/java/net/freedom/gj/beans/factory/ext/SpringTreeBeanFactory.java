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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.freedom.gj.beans.annotation.Criteria;
import net.freedom.gj.beans.criteria.BeanCriteria;
import net.freedom.gj.beans.factory.AbstractTreeBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 *
 * @author Martin Jamszolik
 */
public class SpringTreeBeanFactory<BeanType, DataType> extends AbstractTreeBeanFactory<BeanType, DataType>
                                                       implements BeanFactoryAware {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    protected List<Object> findAllByType(Class type) {
       List<Object> list = new ArrayList<Object>();
       Map<String, Object> objects = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                (ListableBeanFactory)beanFactory, type );

		for(String key : objects.keySet()){
            if(objects.get(key).getClass().isAnnotationPresent(Criteria.class)
                    || type.isInstance(objects.get(key))){
			    list.add(objects.get(key));
            }
		}
        return list;
    }
}
