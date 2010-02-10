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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PropertyCriterion{
	private String propertyName;
	private PropertyMatcher propertyMatcher;
	
	public PropertyCriterion(){
		
	}
	
	public PropertyCriterion(String name, PropertyMatcher propertyMatcher){
		this.propertyName = name;
		this.propertyMatcher = propertyMatcher;
	}
	
	public boolean matchesCriterion(Object data){
		try {
            Object value = null;
            if( data instanceof MapContext ){
                value = ((MapContext)data).get(getPropertyName());
            }else{
                String name = "get" + getPropertyName().substring(0, 1).toUpperCase() + getPropertyName().substring(1);
                Method method = data.getClass().getMethod(name, (Class[])null);
                value = method.invoke(data, (Object[])null);
            }
			
			return propertyMatcher.matches(value);

		} catch (NoSuchMethodException e) {
			// ignore
		} catch (IllegalArgumentException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		} catch (InvocationTargetException e) {
			// ignore
		}
		
		return false;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String name) {
		this.propertyName = name;
	}
	public PropertyMatcher getPropertyMatcher() {
		return propertyMatcher;
	}
	public void setPropertyMatcher(PropertyMatcher propertyMatcher) {
		this.propertyMatcher = propertyMatcher;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PropertyCriterion other = (PropertyCriterion) obj;
		if (propertyMatcher == null) {
			if (other.propertyMatcher != null)
				return false;
		} else if (!propertyMatcher.equals(other.propertyMatcher))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		return true;
	}

}
