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

public class PropertyCriteria {
	private List<PropertyCriterion> criteria = null;

	public List<PropertyCriterion> getCriteria() {
		if(criteria == null){
			criteria = new ArrayList<PropertyCriterion>();
		}
		return criteria;
	}

	public void setCriteria(List<PropertyCriterion> criteria) {
		this.criteria = criteria;
	}
	
	public void addCriterion(PropertyCriterion criterion){
		getCriteria().add(criterion);
	}
	
	public void addCriterion(String propertyName, PropertyMatcher matcher){
		addCriterion(new PropertyCriterion(propertyName, matcher));
	}
}
