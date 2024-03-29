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

import jakarta.el.ELContext;
import jakarta.el.ExpressionFactory;
import jakarta.el.ValueExpression;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;



import org.viablespark.criteria.factory.BeanFactory;
import org.viablespark.mapper.config.MapperConfiguration;
import static org.viablespark.mapper.util.BasicHelper.isSet;
import org.viablespark.mapper.util.BeanMapperELContext;
import org.viablespark.mapper.util.Lg;

/**
 * This is default implementation of BeanMapper.
 *
 * @author Goutham Gogineni
 * @author Martin Jamszolik
 * @since 1.0
 */
public class BeanMapperImpl implements BeanMapper {

    /**
     * Factory to get set of MapperConfiguration beans.
     */
    private BeanFactory<MapperConfiguration, MapperConfigurationContext> beanFactory = null;
    /**
     * EL ExpressionFactory instance.
     */
    private final ExpressionFactory expr = ExpressionFactory.newInstance();

    public void setBeanFactory(
            BeanFactory<MapperConfiguration, MapperConfigurationContext> beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Copies data from source object to target object.
     *
     * @param source Source object.
     * @param target Target object.
     * @return Returns target object.
     */
    @Override
    public <T> T map(Object source, T target) {
        // Get the set of MapperConfiguration's that are applicable
        MapperConfigurationContext context = new DefaultMapperConfigurationContext("source", source, "target", target);
        Set<MapperConfiguration> configurations = beanFactory.getObjects(context);

        // If there are no MapperConfiguration's return the target.
        if (!isSet(configurations) && configurations.isEmpty()) {
            return target;
        }

        // Get EL context with source and target objects configured 
        ELContext elContext = getElContext(context.getSource(), context.getTarget());

        // Apply mappings from each configuration.
        for (MapperConfiguration configuration : configurations) {

            MappingInformation info = configuration.getMappingInformation();
            // Get mappings and apply them
            for (MappingData mappingData : info.getMappingData()) {
                map(mappingData, elContext);
            }

            // Do any post processing if required
            if (isSet(info.getPostProcessors())) {
                for (PostProcessor processor : info.getPostProcessors()) {
                    processor.process(context.getSource(), context.getTarget());
                }
            }
        }

        return target;
    }

    /**
     * Gets ELContext instance. It uses SimpleContext. It also configures source
     * and target objects with names source and target.
     *
     * @param source Source object
     * @param target Target object
     * @return Returns ELContext object.
     */
    private ELContext getElContext(Object source, Object target) {
        BeanMapperELContext context = new BeanMapperELContext();

        context.setSource(expr.createValueExpression(source, source.getClass()));
        context.setTarget(expr.createValueExpression(target, target.getClass()));

        return context;
    }

    /**
     * Using sourceExpression and targetExpression from MappingData, it copies
     * the data from source object to target object. If the target object is
     * complex object and if the object is null it creates them if they have a
     * no argument constructor. If a Converter is configured, it converts data
     * before setting the value in target object.
     *
     * @param mappingData Provides mapping information.
     * @param context ELContext object.
     */
    private void map(MappingData mappingData, ELContext context) {
        try {
            // Get source value
            Object value = expr.createValueExpression(context,
                    "${source." + mappingData.getSourceExpression() + "}", Object.class).getValue(context);

            // Create target if it is null
            createTargetObject(mappingData, context, value);

            // If the target is Array or Collection, loop through the collection
            if ((mappingData.isArray() || mappingData.isCollection())) {
                map(mappingData, context, value);

                return;
            }

            // Convert source value if a converter is specified 
            if (mappingData.getConverter() != null) {
                value = mappingData.getConverter().convert(value);
            }

            // Copy the source value to the target
            expr.createValueExpression(context,
                    "${target." + mappingData.getTargetExpression() + "}",
                    (value == null ? Object.class : value.getClass())).setValue(context, value);

        } catch (Exception e) {
            Lg.log(this, Lg.ERROR, 
                    "Mapper Failed to map [" + mappingData.getSourceExpression()
                            + "] to [" + mappingData.getTargetExpression()
                            + "] Exception: "
                            + e.getMessage(),e);
        }
    }

    /**
     * Handles mapping if the source/target is a collection. Currently it
     * handles Arrays and Lists. Support for Set has to be added.
     */
    private void map(MappingData mappingData, ELContext context, Object value) {
        MappingData clonedMappingData;
        for (int i = 0; i < getLength(value); i++) {
            if (mappingData.getCollectionMappingData() == null || mappingData.getCollectionMappingData().isEmpty()) {
                map(new MappingData(mappingData.getSourceExpression() + "[" + i + "]", mappingData.getTargetExpression() + "[" + i + "]", mappingData.getConverter()), context);
            } else {
                for (MappingData data : mappingData.getCollectionMappingData()) {
                    clonedMappingData = data.clone();
                    if (clonedMappingData.getSourceExpression() == null || clonedMappingData.getSourceExpression().length() == 0) {
                        clonedMappingData.setSourceExpression(mappingData.getSourceExpression() + "[" + i + "]");
                    } else {
                        clonedMappingData.setSourceExpression(mappingData.getSourceExpression() + "[" + i + "]." + clonedMappingData.getSourceExpression());
                    }

                    if (clonedMappingData.getTargetExpression() == null || clonedMappingData.getTargetExpression().length() == 0) {
                        clonedMappingData.setTargetExpression(mappingData.getTargetExpression() + "[" + i + "]");
                    } else {
                        clonedMappingData.setTargetExpression(mappingData.getTargetExpression() + "[" + i + "]." + clonedMappingData.getTargetExpression());
                    }
                    map(clonedMappingData, context);
                }
            }
        }
    }

    /**
     * Creates objects if the objects in the expression are null.
     *
     * @param mappingData mapping Information used to construct EL
     * @param context ELContext instance.
     * @param sourceValue the source of data
     */
    private void createTargetObject(MappingData mappingData, ELContext context, Object sourceValue) throws Exception {
        // Getting individual object expressions
        String wholeExpression = "target." + mappingData.getTargetExpression();
        String[] expressions = wholeExpression.split("\\.");

        // The base and last object are not created. All other objects are created if they are null. 
        if ((mappingData.isArray() || mappingData.isCollection()) ? expressions.length < 2 : expressions.length < 3) {
            return;
        }

        Class<?> type;
        String expression = null;
        ValueExpression valueExpression;
        Object value;

        // For each object
        for (int i = 0; i < ((mappingData.isArray() || mappingData.isCollection()) ? expressions.length : expressions.length - 1); i++) {
            // Prepare expression
            expression = expression == null ? expressions[i] : expression + "." + expressions[i];
            valueExpression = expr.createValueExpression(context, "${" + expression + "}", Object.class);

            // Get value and type
            value = valueExpression.getValue(context);
            type = valueExpression.getType(context);

            // If type is null assume it is an Object type
            if (type == null) {
                type = Object.class;
            }

            // If value is null, try to create it
            if (value == null) {
                // If the value type is an interface, and if it is collection or map, create it. 
                // Else we don't know the implementation type to create. So, throw exception.
                if (type.isInterface()) {
                    if (type.equals(Map.class)) { // If it is a Map, create HashMap
                        valueExpression.setValue(context, new HashMap<String, Object>());
                    } else if (type.equals(List.class)) { // If it is List, create ArrayList and populate dummy data to prevent ArrayIndexOutOfBound exception.
                        List<Object> list = new ArrayList<>();
                        valueExpression.setValue(context, list);
                        for (int j = 0; j < getLength(sourceValue); j++) {
                            list.add(newInstance(Class.forName(mappingData.getCollectionObjectType()), null));
                        }
                    } else if (type.equals(Set.class)) { // If it is Set, create HashSet 
                        valueExpression.setValue(context, new HashSet<>());
                    } else if (type.equals(SortedSet.class)) { // If it is SortedSet, create TreeSet
                        valueExpression.setValue(context, new TreeSet<>());
                    } else {
                        throw new InstantiationException("Could not identify the implementation of " + type);
                    }

                    continue;
                }

                // Value type is not an interface, try to create it. 
                valueExpression.setValue(context, newInstance(type, sourceValue));
            }
        }
    }

    /**
     * Creates an instance of class type
     *
     * @param type Class type
     * @param value Source value
     * @return Returns an instance of type
     */
    private Object newInstance(Class<?> type, Object value) throws Exception {
        if (type.isArray()) {
            return Array.newInstance(type.getComponentType(), getLength(value));
        }
        return type.getDeclaredConstructor().newInstance();
    }

    /**
     * Gets size of Collection or length of Array object
     *
     * @param value Collection or Array object
     * @return Returns size of Collection or length of Array object
     */
    private int getLength(Object value) {
        return value == null ? 0 : ((value instanceof Collection) ? ((Collection<?>) value).size() : ((Object[]) value).length);
    }
}
