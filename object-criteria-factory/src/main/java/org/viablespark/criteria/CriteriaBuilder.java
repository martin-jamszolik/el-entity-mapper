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

import java.util.ArrayList;
import java.util.List;
import org.viablespark.criteria.matcher.PropertyMatcher;

/**
 * Help build complex criteria more elegantly.
 * 
 * @author Martin Jamszolik
 */
public class CriteriaBuilder {
    List<PropertyCriteria>criteriaList = null;

    public CriteriaBuilder(){
        criteriaList = new ArrayList<PropertyCriteria>();
    }
    
    public CriteriaBuilder( List<PropertyCriteria> _list ){
        criteriaList = _list;
    }
    public CriteriaBuilder build(PropertyBuilder builder ){
        criteriaList.add( builder.getPropertyCriteria());
        return this;
    }
    public List<PropertyCriteria>getCriteria(){
        return criteriaList;
    }
    
    
    public CriteriaBuilder build(String key, PropertyMatcher matcher){
        PropertyCriteria criteria = new PropertyCriteria();
        criteria.addCriterion(key,matcher);
        criteriaList.add( criteria );
        return this;
    }

}
