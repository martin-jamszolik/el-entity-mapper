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

import java.util.List;
import net.freedom.gj.beans.criteria.BeanCriteria;
import net.freedom.gj.beans.criteria.CriteriaBuilder;
import net.freedom.gj.beans.criteria.PropertyCriteria;
import net.freedom.gj.beans.matcher.InstanceOfMatcher;

/**
 *
 * @author martin
 */
public class MyServiceA implements MyService, BeanCriteria{

    public void execute() {
        System.out.println("executed A");
    }

    public List<PropertyCriteria> getCriteria() {
        return new CriteriaBuilder().build("payload", new InstanceOfMatcher(String.class) ).getCriteria();
    }
    
}
