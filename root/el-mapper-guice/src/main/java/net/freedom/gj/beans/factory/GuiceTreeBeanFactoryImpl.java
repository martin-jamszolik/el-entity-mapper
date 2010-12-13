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

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.freedom.gj.beans.config.MapperConfiguration;
import net.freedom.gj.beans.mapper.MapperConfigurationContext;



/**
 * @author Martin Jamszolik
 */
public class GuiceTreeBeanFactoryImpl implements BeanFactory<MapperConfiguration, MapperConfigurationContext>{
	private Injector injector;
    private String objectType = null;
    
    
	private BeanTreeNode rootNode = null;
	
	
	
	public void init() throws Exception{
        Class type = Class.forName(objectType);
        List<Binding<BeanCriteria>> result = injector.findBindingsByType( TypeLiteral.get(type) );
        for( Binding<BeanCriteria> b : result){
          addObject( b.getProvider().get() );            
        }
	}
	
	public MapperConfiguration getObject(MapperConfigurationContext data) {
		Set<MapperConfiguration> objects = getObjects(data);
		
		if(objects == null || objects.isEmpty()){
			return null;
		}
		
		if(objects.size() > 1){
			throw new IllegalStateException("Expected single object but multiple objects are found.");
		}
		
		return objects.iterator().next();
	}
	
	public Set<MapperConfiguration> getObjects(MapperConfigurationContext data) {
            Set<MapperConfiguration> result = getObjects(rootNode, data);
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
	
	private BeanTreeNode<MapperConfiguration> getMatchedChild(BeanTreeNode<MapperConfiguration> parent, PropertyCriterion criterion){
		BeanTreeNode<MapperConfiguration> matchedChild = null;
		if(parent.hasChildren()){
			for(BeanTreeNode child : parent.getChildren()){
				if(child.getCriterion().equals(criterion)){
					matchedChild = child;
					break;
				}
			}
		}
		if(matchedChild == null){
			matchedChild = new BeanTreeNode<MapperConfiguration>();
			matchedChild.setCriterion(criterion);
			parent.addChildNode(matchedChild);
		}

		return matchedChild;
	}
	
	private Set<MapperConfiguration> getObjects(BeanTreeNode<MapperConfiguration> node, Object data){
		if(node == null){
			return null;
		}
		
		if(!node.hasChildren()){
			return node.getObjects();
		}
		
		Set<MapperConfiguration> objects = new HashSet<MapperConfiguration>();
		if(node.getObjects() != null){
			objects.addAll(node.getObjects());
		}
		Set<MapperConfiguration> temp = null;
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
	

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
    
    

}
