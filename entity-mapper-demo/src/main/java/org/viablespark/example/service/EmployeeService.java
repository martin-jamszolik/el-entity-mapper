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

package org.viablespark.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.viablespark.example.Util;
import org.viablespark.example.jpa.EmployeeEntity;
import org.viablespark.example.pojo.Employee;
import org.springframework.stereotype.Service;
import org.viablespark.mapper.BeanMapper;

/**
 *
 * @author Martin Jamszolik
 */
@Service
public class EmployeeService {


    @Autowired
    private BeanMapper mapper;

    /**
     * Service method to demonstrate usage of a configured
     * mapper in a typical scenario.
     * @param emp
     */
    public void save(Employee emp){
       EmployeeEntity entity =  mapper.map(emp, new EmployeeEntity() );
       persist( entity );        
    }



    private void persist(EmployeeEntity obj ){
        //Here we can use a dao or JPA EntityManager to perform database save.
        //em.persist(obj);
        
        Util.print(obj);
        EmployeeDao.persist(obj);
    }




}
