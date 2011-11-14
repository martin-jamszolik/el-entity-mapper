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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author Goutham Gogineni
 * @param <BeanType>
 * @param <String> 
 */
public class MapBeanFactoryImpl<BeanType, String> implements BeanFactory<BeanType, String> {

    private Map<String, BeanType> objectsMap;

    public void setObjectsMap(Map<String, BeanType> objectsMap) {
        this.objectsMap = objectsMap;
    }

    public BeanType getObject(String key) {
        return objectsMap.get(key);
    }

    public Set<BeanType> getObjects(String key) {
        BeanType object = getObject(key);
        Set<BeanType> objects = new HashSet<BeanType>();
        if (object != null) {
            objects.add(object);
        }

        return objects;
    }
}
