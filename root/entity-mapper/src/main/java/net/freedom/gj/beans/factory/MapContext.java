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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Martin Jamszolik
 */
public class MapContext {

    private Map<Object,Object> map = new HashMap<Object,Object>(3);

    public MapContext(){}

    /**
     * Convenient array based arguments for assembling hashmap of string values
     * For Example. new MapContext("client","SFI","lob","HO")
     * retults in client=>SFI, lob=>HO
     * So if you want to a match, client becomes the key and SFI becomes the value
     *
     * @param args  - Every odd pair results in key=>value pair.
     */
    public MapContext(Object... args) {
        putArray(args);
    }    

    public MapContext put(Object... args){
        putArray(args);
        return this;
    }

    public Object get(Object key){
        return map.get(key);
    }


    private void putArray( Object... args){
        for (int i = 0; i < args.length; i++) {
            if (i % 2 != 0) {
                map.put(args[i - 1], args[i]);
            }
        }
    }

}
