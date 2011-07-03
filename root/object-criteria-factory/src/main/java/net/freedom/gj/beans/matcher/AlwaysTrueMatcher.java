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

/**
 *Use this matcher when logic is generic and you want to run
 *it for all context and all criteria.
 *
 * @author Martin Jamszolik
 */
public class AlwaysTrueMatcher implements PropertyMatcher{

    public boolean matches(Object value) {
        return true;
    }

    public void setValue(Object matchingValue) {
        // do nothing
    }

}
