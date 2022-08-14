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

import jakarta.el.ArrayELResolver;
import jakarta.el.BeanELResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.FunctionMapper;
import jakarta.el.ListELResolver;
import jakarta.el.MapELResolver;
import jakarta.el.ResourceBundleELResolver;
import jakarta.el.ValueExpression;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Martin Jamszolik
 */
public class BeanMapperELContext extends ELContext {

    private static final ELResolver READ_WRITE = new CompositeELResolver() {

        {
            add(new ArrayELResolver(false));
			add(new ListELResolver(false));
			add(new MapELResolver(false));
			add(new ResourceBundleELResolver());
			add(new BeanELResolver(false));
        }
    };
    private static FunctionWrapper function;
    private static VariableWrapper variable;

    @Override
    public ELResolver getELResolver() {
        return READ_WRITE;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return function;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variable;
    }

    static class FunctionWrapper extends FunctionMapper {

        @Override
        public Method resolveFunction(String string, String string1) {
            throw new UnsupportedOperationException("Function Not supported yet.");
        }
    }

    static class VariableWrapper extends VariableMapper {

        Map<String, ValueExpression> map = new HashMap<String, ValueExpression>();

        @Override
        public ValueExpression resolveVariable(String variable) {
            return map.get(variable);
        }

        @Override
        public ValueExpression setVariable(String variable, ValueExpression expression) {
            return map.put(variable, expression);
        }
    }

    public void setSource(ValueExpression obj) {
        if (variable == null) {
            variable = new VariableWrapper();
        }
        variable.setVariable("source", obj);
    }

    public void setTarget(ValueExpression obj) {
        if (variable == null) {
            variable = new VariableWrapper();
        }
        variable.setVariable("target", obj);
    }
}
