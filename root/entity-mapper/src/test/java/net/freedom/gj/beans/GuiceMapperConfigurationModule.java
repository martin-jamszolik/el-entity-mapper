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

package net.freedom.gj.beans;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.freedom.gj.beans.config.MapperConfiguration;
import net.freedom.gj.beans.config.SimpleFileMapperConfiguration;

/**
 *
 * @author Martin Jamszolik
 */
public class GuiceMapperConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
       
    }

    
    @Provides @Singleton
     MapperConfiguration provideMapperConfiguration(){
        SimpleFileMapperConfiguration fileConfig = new SimpleFileMapperConfiguration();
        fileConfig.setConfigurationFile("/net/freedom/gj/beans/txt/beanA-to-beanB-mapping1.txt");
        fileConfig.setSourceType("net.freedom.gj.beans.EntityBeanA");
        fileConfig.setTargetType("net.freedom.gj.beans.EntityBeanB");
        
        return fileConfig;
     }
    
}
