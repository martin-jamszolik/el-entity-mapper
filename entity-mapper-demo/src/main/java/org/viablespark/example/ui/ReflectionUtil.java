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
package org.viablespark.example.ui;

import org.viablespark.mapper.util.Lg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Martin Jamszolik
 */
public class ReflectionUtil {

    public static Object invokeGetter(Object getterOwner, Method method) throws Exception {
        try {
            return method.invoke(getterOwner);

        } catch (NullPointerException e) {
            return null;
        }

    }

    public static void invokeSetter(Object setterOwner, Method method, Object value) {
        try {

            method.invoke(setterOwner, value);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Method> getGetterMethods(Class<?> clazz) {
        List<Method> allMethods = new ArrayList<>();
        do {
            try {
                allMethods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            } catch (Exception e) {
                Lg.log(ReflectionUtil.class, Lg.WARN, "Error ", e);
            }
        } while ((clazz = clazz.getSuperclass()).getPackage().getName().startsWith("org.viablespark.example"));

        List<Method> getterMethods = new ArrayList<>();
        for (Method m : allMethods) {
            if (m.getName().startsWith("get")) {
                getterMethods.add(m);
            }
        }
        return getterMethods;
    }
}
