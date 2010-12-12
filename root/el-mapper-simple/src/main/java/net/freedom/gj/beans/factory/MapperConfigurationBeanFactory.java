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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.freedom.gj.beans.config.SimpleFileMapperConfiguration;
import net.freedom.gj.beans.config.MapperConfiguration;
import net.freedom.gj.beans.mapper.MapperConfigurationContext;

/**
 *
 * @author Martin Jamszolik
 */
public class MapperConfigurationBeanFactory implements BeanFactory<MapperConfiguration,MapperConfigurationContext> {

    List<SimpleFileMapperConfiguration> list = new ArrayList<SimpleFileMapperConfiguration>();

    public void add( SimpleFileMapperConfiguration config ){
        list.add( config );
    }

    public MapperConfiguration getObject(MapperConfigurationContext data) {
        Set<MapperConfiguration> result = getObjects(data);
        if( result.isEmpty() ){
            return null;
        }
        return result.iterator().next(); 
    }

    public Set<MapperConfiguration> getObjects(MapperConfigurationContext data) {        
        String source = data.getSource().getClass().getName();
        String target = data.getTarget().getClass().getName();        
        Set<MapperConfiguration> contextList = new HashSet<MapperConfiguration>();
        for( SimpleFileMapperConfiguration c : list ){
            if( source.equals(c.getSourceType()) && target.equals(c.getTargetType()) ){
                contextList.add(c);
            }
        }
        return contextList;
    }
}
