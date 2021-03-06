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

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import net.freedom.gj.beans.factory.ext.GuiceTreeBeanFactory;
import net.freedom.gj.beans.mapper.BeanMapper;
import net.freedom.gj.beans.mapper.BeanMapperImpl;

/**
 *
 * @author Martin Jamszolik
 */
public class GuiceBeanMapperModule extends AbstractModule{

    @Override
    protected void configure() {
        
    }
    
    @Provides @Singleton
    BeanMapper provideBeanMapper(Injector injector) throws Exception{
        BeanMapperImpl mapper = new BeanMapperImpl();
        GuiceTreeBeanFactory factory = new GuiceTreeBeanFactory();
        factory.setObjectType("net.freedom.gj.beans.config.MapperConfiguration");
        factory.setInjector(injector);
        mapper.setBeanFactory(factory);           
        factory.init();
        
      return mapper;
    }

}
