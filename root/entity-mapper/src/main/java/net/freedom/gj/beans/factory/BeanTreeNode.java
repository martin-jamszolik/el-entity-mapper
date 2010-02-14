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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanTreeNode {
	private PropertyCriterion criterion;
	private Set<BeanCriteria> objects;
	private List<BeanTreeNode> children;
	
	public boolean hasChildren(){
		return children != null && children.size() > 0; 
	}

	public boolean hasObjects(){
		return objects != null && objects.size() > 0;
	}
	
	public void addObject(BeanCriteria object){
		if(getObjects() == null){
			setObjects(new HashSet<BeanCriteria>());
		}
		getObjects().add(object);
	}
	
	public void addChildNode(BeanTreeNode child){
		if(getChildren() == null){
			setChildren(new ArrayList<BeanTreeNode>()); 
		}
		getChildren().add(child);
	}

	public List<BeanTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<BeanTreeNode> children) {
		this.children = children;
	}

	public PropertyCriterion getCriterion() {
		return criterion;
	}

	public void setCriterion(PropertyCriterion criterion) {
		this.criterion = criterion;
	}

	public Set<BeanCriteria> getObjects() {
		return objects;
	}

	public void setObjects(Set<BeanCriteria> objects) {
		this.objects = objects;
	}

	
}
