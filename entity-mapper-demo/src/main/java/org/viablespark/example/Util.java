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
package org.viablespark.example;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.viablespark.example.jpa.EmployeeEntity;
import org.viablespark.example.pojo.Address;
import org.viablespark.example.pojo.Employee;
import org.viablespark.example.pojo.Group;

/**
 *
 * @author Martin Jamszolik
 */
public class Util {

    public static void print(EmployeeEntity entity) {
        System.out.println("EmployeeEntity:");

        List<Method> getters = getGetterMethods(entity.getClass());

        for( Method m : getters ){
            System.out.println("  "+m.getName().substring(3)+"="+ invokeGetter(entity, m) );
        }

        System.out.println("UserInfo:");
        getters = getGetterMethods(entity.getUserid().getClass());

        for( Method m : getters ){
            System.out.println("  "+m.getName().substring(3)+"="+ invokeGetter(entity.getUserid(), m) );
        }

    }

    public static void print(Employee emp) {
        System.out.println("Employee:");
        List<Method> getters = getGetterMethods(emp.getClass());

        for( Method m : getters ){
            System.out.println("  "+m.getName().substring(3)+"="+ invokeGetter(emp, m) );
        }

    }

    public static Employee generateEmployee() {

        Employee e = new Employee();
        e.setActive(Boolean.TRUE);
        e.setAddress(new Address("57 Test STreet", "Sarasota", "FL", "30201"));
        e.setAgencyId(23);
        e.setCompanyid(34);
        e.setCompanyid(533);
        e.setId(3443);
        e.setEmail("test.me.@mail.com");
        e.setFirstName("See");
        e.setLastName("Vision");
        e.setHasAdminRights(Boolean.TRUE);
        e.setLoginid("ERD3443EES");
        e.setMiddle("Jozef");
        e.setPassword("someSecret");
        e.setSalutation("Mr");
        e.setStatus("ACTIVE");
        e.setUpdatedby("Demo");
        e.setUpdateddate(new Date());
        e.setUserid("2343243243242342343242324");
        e.setSuffix("suffix");
        List<Group> groups = new ArrayList<Group>();
        groups.add(new Group("EMPLOYEE"));
        groups.add(new Group("EMPADMIN"));

        e.setGroups(groups);

        return e;
    }

    public static Object invokeGetter(Object getterOwner, Method method)  {
        try {
            return method.invoke(getterOwner);

        } catch (Exception e) {
            return null;
        }

    }

    public static void invokeSetter(Object setterOwner, Method method, Object value) throws Exception {
        try {

            method.invoke(setterOwner, value);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Method> getGetterMethods(Class<?> clazz) {
        List<Method> allMethods = new ArrayList<Method>();
        do {
            try {
                allMethods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            } catch (Exception e) {
            }
        } while ((clazz = clazz.getSuperclass()).getPackage().getName().startsWith("net.freedom.gj.example"));

        List<Method> getterMethods = new ArrayList<Method>();
        for (Method m : allMethods) {
            if (m.getName().startsWith("get")) {
                getterMethods.add(m);
            }
        }
        return getterMethods;
    }
}
