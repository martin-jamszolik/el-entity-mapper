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
package net.freedom.gj.beans.factory;

import net.freedom.gj.beans.annotation.Criteria;
import net.freedom.gj.beans.annotation.Criterion;
import net.freedom.gj.beans.annotation.Matcher;
import net.freedom.gj.beans.criteria.*;
import net.freedom.gj.beans.matcher.AlwaysTrueMatcher;
import net.freedom.gj.beans.matcher.InstanceOfMatcher;
import net.freedom.gj.beans.matcher.PropertyValueEqualsMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Using a Tree based bean factory means that you can assign multi-variant criteria
 * to retrieve object implementations.  The criteria information is placed within the 
 * object itself for the benefit of keeping the information close to the object that gets 
 * affected.
 * @author Goutham Gogineni
 * @author Martin Jamszolik 
 * 
 */
public abstract class AbstractTreeBeanFactory<BeanType, DataType> implements BeanFactory<BeanType, DataType> {

    private String beanType = null;
    private BeanTreeNode<BeanType> rootNode = null;

    public void setObjectType(String objectType) {
        this.beanType = objectType;
    }

    public void init() throws Exception {
        Class type = Class.forName(beanType);
        List<Object> result = findAllByType(type);
        for (Object bean : result) {
            addObject(bean);
        }
    }

    protected abstract List<Object> findAllByType(Class type);

    protected void addObject(Object object) {
        if (rootNode == null) {
            rootNode = new BeanTreeNode<BeanType>();
            rootNode.setCriterion(new PropertyCriterion());
            rootNode.getCriterion().setPropertyName("root");
        }

        List<PropertyCriteria> criteriaList =  extractCriteria(object);

        if (criteriaList != null) {
            for (PropertyCriteria propertyCriteria : criteriaList) {
                List<PropertyCriterion> criteria = new ArrayList<PropertyCriterion>(propertyCriteria.getCriteria());
                Collections.sort(criteria, new PropertyCriterionComparator());
                addObject(rootNode, criteria, (BeanType) object);
            }
        }
    }

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
            matchedChild = new BeanTreeNode<BeanType>();
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

        Set<BeanType> objects = new HashSet<BeanType>();
        if (node.getObjects() != null) {
            objects.addAll(node.getObjects());
        }
        Set<BeanType> temp = null;
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
     * Helper method to create the property criteria.  The provided object will be checked for supported annotations
     * and create the necessary property criteria.  If annotations are not present, then return property criteria
     * that has been implemented based of extending BeanCriteria interface.  Otherwise, return null.
     * @param object
     * @return List of PropertyCriteria - either built through annotation or retrieved from concrete class.  Null otherwise.
     */
    private List<PropertyCriteria> extractCriteria(Object object){
        List<PropertyCriteria> l = null;

        if(object.getClass().isAnnotationPresent(Criteria.class)){

            CriteriaBuilder criteriaBuilder = new CriteriaBuilder();
            Criteria criteria = object.getClass().getAnnotation(Criteria.class);
            for(Criterion criterion : criteria.value()){
                Matcher matcher = criterion.propertyMatcher();
                switch (matcher.matcher()){
                    case ALWAYS_TRUE:
                        criteriaBuilder.build(criterion.propertyName(), new AlwaysTrueMatcher());
                        break;
                    case VALUE_EQUALS:
                        criteriaBuilder.build(criterion.propertyName(), new PropertyValueEqualsMatcher(matcher.stringToMatch()));
                        break;
                    case INSTANCE:
                        criteriaBuilder.build(criterion.propertyName(), new InstanceOfMatcher(matcher.instanceToMatch()));
                        break;
                }

            }
            return criteriaBuilder.getCriteria();

        }else if(object instanceof BeanCriteria){
            return ((BeanCriteria) object).getCriteria();
        }

        return null;
    }

    class BeanTreeNode<T> {

        private PropertyCriterion criterion;
        private Set<T> objects;
        private List<BeanTreeNode<T>> children;

        public boolean hasChildren() {
            return children != null && children.size() > 0;
        }

        public boolean hasObjects() {
            return objects != null && objects.size() > 0;
        }

        public void addObject(T object) {
            if (getObjects() == null) {
                setObjects(new HashSet<T>());
            }
            getObjects().add(object);
        }

        public void addChildNode(BeanTreeNode<T> child) {
            if (getChildren() == null) {
                setChildren(new ArrayList<BeanTreeNode<T>>());
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
