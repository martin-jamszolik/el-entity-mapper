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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;


public class TreeBeanFactoryImpl implements BeanFactory<BeanCriteria, Object>, BeanFactoryAware{
	private org.springframework.beans.factory.BeanFactory beanFactory = null;
	private BeanTreeNode rootNode = null;
	private String objectType = null;
	
	@SuppressWarnings("unchecked")
	public void init() throws BeansException, ClassNotFoundException{
		Map<String, BeanCriteria> objects = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)beanFactory, Class.forName(objectType));
		for(String key : objects.keySet()){
			addObject(objects.get(key));
		}
	}
	
	public BeanCriteria getObject(Object data) {
		Set<BeanCriteria> objects = getObjects(data);
		
		if(objects == null || objects.size() == 0){
			return null;
		}
		
		if(objects.size() > 1){
			throw new IllegalStateException("Expected single object but multiple objects are found.");
		}
		
		return objects.iterator().next();
	}
	
	public Set<BeanCriteria> getObjects(Object data) {
            Set<BeanCriteria> result = getObjects(rootNode, data);
            if(result == null )
                return Collections.emptySet();

            return result;
	}
	
	private void addObject(BeanCriteria object){
		if(rootNode == null){
			rootNode = new BeanTreeNode();
			rootNode.setCriterion(new PropertyCriterion());
			rootNode.getCriterion().setPropertyName("root");
		}
		List<PropertyCriteria> criteriaList = object.getCriteria();
		for(PropertyCriteria propertyCriteria : criteriaList){
			List<PropertyCriterion> criteria = new ArrayList<PropertyCriterion>(propertyCriteria.getCriteria());
			Collections.sort(criteria, new PropertyCriterionComparator());
			addObject(rootNode, criteria, object);
		}
	}
	
	private void addObject(BeanTreeNode node, List<PropertyCriterion> criteria, BeanCriteria object){
		if(criteria == null || criteria.isEmpty()){
			node.addObject(object);
			return;
		}
		BeanTreeNode matchedChild = getMatchedChild(node, criteria.get(0));
		criteria.remove(0);
		addObject(matchedChild, criteria, object);
	}
	
	private BeanTreeNode getMatchedChild(BeanTreeNode parent, PropertyCriterion criterion){
		BeanTreeNode matchedChild = null;
		if(parent.hasChildren()){
			for(BeanTreeNode child : parent.getChildren()){
				if(child.getCriterion().equals(criterion)){
					matchedChild = child;
					break;
				}
			}
		}
		if(matchedChild == null){
			matchedChild = new BeanTreeNode();
			matchedChild.setCriterion(criterion);
			parent.addChildNode(matchedChild);
		}

		return matchedChild;
	}
	
	private Set<BeanCriteria> getObjects(BeanTreeNode node, Object data){
		if(node == null){
			return null;
		}
		
		if(!node.hasChildren()){
			return node.getObjects();
		}
		
		Set<BeanCriteria> objects = new HashSet<BeanCriteria>();
		if(node.getObjects() != null){
			objects.addAll(node.getObjects());
		}
		Set<BeanCriteria> temp = null;
		for(BeanTreeNode child : node.getChildren()){
			if(child.getCriterion().matchesCriterion(data)){
				temp = getObjects(child, data);
				if(temp != null){
					objects.addAll(temp);
				}
			}
		}
		
		return objects;
	} 
	
	
	public void setBeanFactory(org.springframework.beans.factory.BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
