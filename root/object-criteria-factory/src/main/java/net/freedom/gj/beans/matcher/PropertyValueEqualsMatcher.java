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


package net.freedom.gj.beans.matcher;

public class PropertyValueEqualsMatcher implements PropertyMatcher{
	private Object compareWith;
	
	public PropertyValueEqualsMatcher(Object compareWith){
		this.compareWith = compareWith;
	}
	
	public boolean matches(Object value) {
		if(value == compareWith){
			return true;
		}
		
		if(value == null || compareWith == null){
			return false;
		}
		
		return value.equals(compareWith);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PropertyValueEqualsMatcher other = (PropertyValueEqualsMatcher) obj;
		if (compareWith == null) {
			if (other.compareWith != null)
				return false;
		} else if (!compareWith.equals(other.compareWith))
			return false;
		return true;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.compareWith != null ? this.compareWith.hashCode() : 0);
        return hash;
    }
}
