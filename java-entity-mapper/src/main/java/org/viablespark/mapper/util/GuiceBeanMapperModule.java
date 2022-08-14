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

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.viablespark.criteria.factory.ext.GuiceTreeBeanFactory;
import org.viablespark.mapper.BeanMapper;
import org.viablespark.mapper.BeanMapperImpl;
import org.viablespark.mapper.MapperConfigurationContext;
import org.viablespark.mapper.config.MapperConfiguration;

/**
 *
 * @author Martin Jamszolik
 */
public class GuiceBeanMapperModule extends AbstractModule{

    @Override
    protected void configure() {
        
    }
    
    @Provides @Singleton
    public BeanMapper provideBeanMapper(Injector injector) throws Exception{
        BeanMapperImpl mapper = new BeanMapperImpl();
        GuiceTreeBeanFactory<MapperConfiguration,MapperConfigurationContext> factory = new GuiceTreeBeanFactory<>();
        factory.setObjectClass(MapperConfiguration.class);
        factory.setInjector(injector);
        mapper.setBeanFactory(factory);           
        factory.init();
        
      return mapper;
    }

}
