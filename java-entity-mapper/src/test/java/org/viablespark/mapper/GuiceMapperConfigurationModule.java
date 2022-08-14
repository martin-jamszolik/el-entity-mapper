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
package org.viablespark.mapper;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.viablespark.mapper.config.MapperConfiguration;
import org.viablespark.mapper.config.SimpleFileMapperConfiguration;

/**
 *
 * @author Martin Jamszolik
 */
public class GuiceMapperConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
           
    }

    @Provides
    @Named("mapper1")
    public MapperConfiguration provideMapping1() {
        SimpleFileMapperConfiguration fileConfig = new SimpleFileMapperConfiguration();
        fileConfig.setConfigurationFile("/org/viablespark/beanA-to-beanB-mapping1.txt");
        fileConfig.setSourceType(EntityBeanA.class);
        fileConfig.setTargetType(EntityBeanB.class);

        return fileConfig;
    }
    
    @Provides
    @Named("mapper2")
    public MapperConfiguration provideMapping2() {
        SimpleFileMapperConfiguration fileConfig = new SimpleFileMapperConfiguration();
        fileConfig.setConfigurationFile("/org/viablespark/beanA-to-beanB-mapping2.txt");
        fileConfig.setSourceType(EntityBeanA.class);
        fileConfig.setTargetType(EntityBeanB.class);

        return fileConfig;
    }

}
