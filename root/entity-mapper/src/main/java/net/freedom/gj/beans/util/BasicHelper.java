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

package net.freedom.gj.beans.util;

/**
 *
 * @author Martin Jamszolik
 */
public class BasicHelper {


    
    public static boolean isSet(Object obj) {
        return obj == null ? false : (obj.toString().length() > 0);
    }

    /**
     * Help with NullPointerExceptions
     * @param existing - in case existing string already exists
     * @param next - the new string
     * @param inBetween - Sometimes | needs to be added in between ;
     * @return
     */
    public static String append(String existing, String next, String inBetween) {
        return isSet(existing) ? (existing + ((isSet(inBetween) && isSet(next)) ? inBetween : "") + next) : next;

    }


     /**
     * Check for null, and then convert any Object to a string
     * @param o
     * @return String representation of Object, or empty String
     */
    public static String toString(Object o) {
        if (o != null) {
            return o.toString();
        }
        return "";
    }


}
