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

package net.freedom.gj.beans.mapper;

import java.util.List;
import net.freedom.gj.beans.converter.Converter;

/**
 * This class holds information to map a single property.
 * @author Goutham Gogineni
 *
 */
public class MappingData {
	private String sourceExpression;
	private String targetExpression;
	private Converter converter;
	private boolean array;
	private boolean collection;
	private List<MappingData> collectionMappingData;
	private String collectionObjectType;
	private boolean createTarget;
	
	public boolean isCreateTarget() {
		return createTarget;
	}

	public void setCreateTarget(boolean createTarget) {
		this.createTarget = createTarget;
	}

	public String getCollectionObjectType() {
		return collectionObjectType;
	}

	public void setCollectionObjectType(String collectionObjectType) {
		this.collectionObjectType = collectionObjectType;
	}

	public MappingData(){
		
	}
	
	public MappingData(String sourceExpression, String targetExpression, Converter converter){
		this.sourceExpression = sourceExpression;
		this.targetExpression = targetExpression;
		this.converter = converter;
	}
	
	public List<MappingData> getCollectionMappingData() {
		return collectionMappingData;
	}
	public void setCollectionMappingData(List<MappingData> collectionMappingData) {
		this.collectionMappingData = collectionMappingData;
	}
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}
	public boolean isCollection() {
		return collection;
	}
	public void setCollection(boolean collection) {
		this.collection = collection;
	}
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public String getSourceExpression() {
		return sourceExpression;
	}
	public void setSourceExpression(String sourceExpression) {
		this.sourceExpression = sourceExpression;
	}
	public String getTargetExpression() {
		return targetExpression;
	}
	public void setTargetExpression(String targetExpression) {
		this.targetExpression = targetExpression;
	}
	
    @Override
	public MappingData clone(){
		MappingData mappingData = new MappingData();
		mappingData.setArray(isArray());
		mappingData.setCollection(isCollection());
		mappingData.setCollectionMappingData(getCollectionMappingData());
		mappingData.setCollectionObjectType(getCollectionObjectType());
		mappingData.setConverter(getConverter());
		mappingData.setSourceExpression(getSourceExpression());
		mappingData.setTargetExpression(getTargetExpression());
		
		return mappingData;
	}
}
