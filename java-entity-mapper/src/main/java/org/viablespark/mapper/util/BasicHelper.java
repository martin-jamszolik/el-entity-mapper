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
package org.viablespark.mapper.util;

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

    /**
     * Helps extract values from String object like xml streams etc.
     * Utility method that could save you from using xml parser framework.
     *
     *
     * @param key - indicator to where your value is stored
     * @param delimiter - indicator to where you stop extracting the value
     * @param ctx - body that contains the value you like to extract.
     * @return
     */
    public static String resolveKey(String key, String delimiter, String ctx) {

        if (ctx.contains(key)) {
            int ind = ctx.indexOf(key);
            String ans = ctx.substring(ind + key.length());
            ans = ans.substring(0, (ans.indexOf(delimiter) == -1 ? ans.length() : ans.indexOf(delimiter)));
            return ans;
        }
        return null;
    }
}
