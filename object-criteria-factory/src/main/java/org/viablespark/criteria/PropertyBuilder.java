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

import org.viablespark.criteria.matcher.PropertyMatcher;

/**
 *
 * @author Martin Jamszolik
 */
public class PropertyBuilder {

    PropertyCriteria criteria;

    public PropertyBuilder(){
        criteria = new PropertyCriteria();
    }
    
    public PropertyBuilder(PropertyCriteria _criteria){
        criteria = _criteria;
    }

     public PropertyBuilder  build(String key, PropertyMatcher matcher) {
		criteria.addCriterion(key,matcher);
        return this;
    }

     public PropertyCriteria getPropertyCriteria(){
         return criteria;
     }

}