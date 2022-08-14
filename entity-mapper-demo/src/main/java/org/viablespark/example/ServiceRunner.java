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

import org.springframework.beans.factory.annotation.Autowired;
import org.viablespark.example.pojo.Employee;
import org.viablespark.example.service.EmployeeService;
import org.springframework.stereotype.Component;
import org.viablespark.example.ui.DemoFrame;

/**
 *
 * @author Martin Jamszolik
 */
@Component
public class ServiceRunner {

    @Autowired
    private EmployeeService service;

    @Autowired
    private DemoFrame ui;

    public void runService(){
        Employee emp = Util.generateEmployee();
        Util.print(emp);

        service.save(emp);
        
        createUI();

    }


    private void createUI(){
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ui.setVisible(true);
            }
        });


    }



}
