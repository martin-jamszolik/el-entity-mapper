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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.viablespark.example.ui.EmployeeView;
import org.springframework.context.support.AbstractApplicationContext;

/**
 *
 * @author Martin Jamszolik
 */
public class Main {

    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(EmployeeView.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Welcome to EL entity mapper demo.\n");

        AnnotationConfigApplicationContext ctx
                = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        ctx.getBean(ServiceRunner.class).runService();
        ctx.registerShutdownHook();

    }
}
