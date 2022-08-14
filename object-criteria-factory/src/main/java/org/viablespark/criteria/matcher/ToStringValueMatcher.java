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
package org.viablespark.criteria.matcher;

/**
 * Matcher Implementation that is most suited for the annotation
 * based approach to bean criteria.
 * Given any arbritrary Value. Compare for equality based on the object toString()
 * value.
 * This way, you may annotate a bean with the intention to match against other then
 * string property.
 * 
 * @author Martin Jamszolik
 */
public class ToStringValueMatcher implements PropertyMatcher {
    private String toStringValue;

    public ToStringValueMatcher(String toStringValue) {
        this.toStringValue = toStringValue;
    }

    public ToStringValueMatcher() {
    }
    
    
    
    public void setValue(Object matchingValue) {
        toStringValue = matchingValue.toString();
    }

    public boolean matches(Object value) {
        return value == null ? false : toStringValue.equals(value.toString());
    }
    
}
