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

package net.freedom.gj.beans.mapper;

import net.freedom.gj.beans.criteria.MapContext;

/**
 * This context is used to get applicable MapperConfiguration instances.
 * @author Goutham Gogineni
 * @author Martin Jamszolik
 *
 */
public class DefaultMapperConfigurationContext extends MapContext implements MapperConfigurationContext {

    
    public DefaultMapperConfigurationContext(Object... args) {
		super(args);
	}

    public Object getSource() {
       return get("source");
    }

    public Object getTarget() {
        return get("target");
    }
}
