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
package org.viablespark.criteria.factory;

import org.viablespark.criteria.*;
import org.viablespark.criteria.annotation.*;
import org.viablespark.criteria.matcher.PropertyMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Using a Tree based bean factory means that you can assign multi-variant
 * criteria to retrieve object implementations. The criteria information is
 * placed within the object itself for the benefit of keeping the information
 * close to the object that gets affected.
 *
 * @author Goutham Gogineni
 * @author Martin Jamszolik
 * @param <BeanType> Bean Class definition
 * @param <DataType> Criteria context
 *
 */
public abstract class AbstractTreeBeanFactory<BeanType, DataType> implements BeanFactory<BeanType, DataType> {

    private Class<BeanType> beanClass = null;
    private BeanTreeNode<BeanType> rootNode = null;

    @SuppressWarnings("unchecked")
    public void setObjectType(String objectType) throws ClassNotFoundException {
        setObjectClass( (Class<BeanType>) Class.forName(objectType));
    }
    
    @SuppressWarnings("unchecked")
    public void setObjectClass(Class<BeanType> beanClass) {
        this.beanClass = beanClass;
    }

    public void init() throws Exception { 

        List<BeanType> result = findAllByType(beanClass);
        result.forEach(bean -> {
            addObject(bean);
        });
    }

    protected abstract List<BeanType> findAllByType(Class<BeanType> type);

    protected void addObject(BeanType object) {
        if (rootNode == null) {
            rootNode = new BeanTreeNode<>();
            rootNode.setCriterion(new PropertyCriterion());
            rootNode.getCriterion().setPropertyName("root");
        }

        List<PropertyCriteria> criteriaList = extractCriteria(object);

        if (criteriaList != null) {
            for (PropertyCriteria propertyCriteria : criteriaList) {
                List<PropertyCriterion> criteria = new ArrayList<>(propertyCriteria.getCriteria());
                Collections.sort(criteria, new PropertyCriterionComparator());
                addObject(rootNode, criteria, object);
            }
        }
    }

    @Override
    public BeanType getObject(DataType data) {
        Set<BeanType> objects = getObjects(data);

        if (objects == null || objects.isEmpty()) {
            return null;
        }

        if (objects.size() > 1) {
            throw new IllegalStateException("Expected single object but multiple objects where found.");
        }

        return objects.iterator().next();
    }

    @Override
    public Set<BeanType> getObjects(DataType data) {
        Set<BeanType> result = getObjects(rootNode, data);
        if (result == null) {
            return Collections.emptySet();
        }

        return result;
    }

    private void addObject(BeanTreeNode<BeanType> node, List<PropertyCriterion> criteria, BeanType object) {
        if (criteria == null || criteria.isEmpty()) {
            node.addObject(object);
            return;
        }
        BeanTreeNode<BeanType> matchedChild = getMatchedChild(node, criteria.get(0));
        criteria.remove(0);
        addObject(matchedChild, criteria, object);
    }

    private BeanTreeNode<BeanType> getMatchedChild(BeanTreeNode<BeanType> parent, PropertyCriterion criterion) {
        BeanTreeNode<BeanType> matchedChild = null;
        if (parent.hasChildren()) {
            for (BeanTreeNode<BeanType> child : parent.getChildren()) {
                if (child.getCriterion().equals(criterion)) {
                    matchedChild = child;
                    break;
                }
            }
        }
        if (matchedChild == null) {
            matchedChild = new BeanTreeNode<>();
            matchedChild.setCriterion(criterion);
            parent.addChildNode(matchedChild);
        }

        return matchedChild;
    }

    private Set<BeanType> getObjects(BeanTreeNode<BeanType> node, Object data) {
        if (node == null) {
            return null;
        }

        if (!node.hasChildren()) {
            return node.getObjects();
        }

        Set<BeanType> objects = new HashSet<>();
        if (node.getObjects() != null) {
            objects.addAll(node.getObjects());
        }
        Set<BeanType> temp;
        for (BeanTreeNode<BeanType> child : node.getChildren()) {
            if (child.getCriterion().matchesCriterion(data)) {
                temp = getObjects(child, data);
                if (temp != null) {
                    objects.addAll(temp);
                }
            }
        }

        return objects;
    }

    /**
     * Helper method to create the property criteria. The provided object will
     * be checked for supported annotations and create the necessary property
     * criteria. If annotations are not present, then return property criteria
     * that has been implemented based of extending BeanCriteria interface.
     * Otherwise, return null.
     *
     * @param object
     * @return List of PropertyCriteria - either built through annotation or
     * retrieved from concrete class. Null otherwise.
     */
    private List<PropertyCriteria> extractCriteria(Object object) {

        if (object instanceof BeanCriteria) {
            return ((BeanCriteria) object).getCriteria();
        }
        if (object == null) {
            return null;
        }

        try {
            if (object.getClass().isAnnotationPresent(Criteria.class)
                    || object.getClass().isAnnotationPresent(CriteriaList.class)) {

                Criteria[] list;

                if (object.getClass().isAnnotationPresent(CriteriaList.class)) {
                    list = (object.getClass().getAnnotation(CriteriaList.class)).value();
                } else {
                    list = new Criteria[]{object.getClass().getAnnotation(Criteria.class)};
                }

                CriteriaBuilder builder = new CriteriaBuilder();
                for (Criteria cri : list) {
                    PropertyBuilder propertyBuilder = new PropertyBuilder();
                    for (Matcher matcher : cri.value()) {
                        PropertyMatcher instance = matcher.matcher().getConstructor().newInstance();
                        instance.setValue(matcher.classValue() == UnsetDefault.class ? matcher.stringValue() : matcher.classValue());
                        propertyBuilder.build(matcher.property(), instance);
                    }
                    builder.build(propertyBuilder);
                }
                return builder.getCriteria();
            }

        } catch (Exception ex) {
            Logger.getLogger(AbstractTreeBeanFactory.class.getName())
                    .log(Level.SEVERE, "Error while extracting criteria", ex);

        }
        return null;
    }

    class BeanTreeNode<T> {

        private PropertyCriterion criterion;
        private Set<T> objects;
        private List<BeanTreeNode<T>> children;

        public boolean hasChildren() {
            return children != null && !children.isEmpty();
        }

        public boolean hasObjects() {
            return objects != null && !objects.isEmpty();
        }

        public void addObject(T object) {
            if (getObjects() == null) {
                setObjects(new HashSet<>());
            }
            getObjects().add(object);
        }

        public void addChildNode(BeanTreeNode<T> child) {
            if (getChildren() == null) {
                setChildren(new ArrayList<>());
            }
            getChildren().add(child);
        }

        public List<BeanTreeNode<T>> getChildren() {
            return children;
        }

        public void setChildren(List<BeanTreeNode<T>> children) {
            this.children = children;
        }

        public PropertyCriterion getCriterion() {
            return criterion;
        }

        public void setCriterion(PropertyCriterion criterion) {
            this.criterion = criterion;
        }

        public Set<T> getObjects() {
            return objects;
        }

        public void setObjects(Set<T> objects) {
            this.objects = objects;
        }
    }
}
