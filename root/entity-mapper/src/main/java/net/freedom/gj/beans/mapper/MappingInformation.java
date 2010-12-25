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

import java.util.ArrayList;
import java.util.List;

/**
 * This context for Bean Mapper. Left this for future use.
 * @author Goutham Gogineni
 *
 */
public class MappingInformation {
	private List<MappingData> mappingData = new ArrayList<MappingData>();
	private List<FunctionData> functionsData;
	private List<PostProcessor> postProcessors;
	
	public List<PostProcessor> getPostProcessors() {
		return postProcessors;
	}
	public void setPostProcessors(List<PostProcessor> postProcessors) {
		this.postProcessors = postProcessors;
	}
	public List<FunctionData> getFunctionsData() {
		return functionsData;
	}
	public void setFunctionsData(List<FunctionData> functionsData) {
		this.functionsData = functionsData;
	}
	public List<MappingData> getMappingData() {
		return mappingData;
	}
	public void setMappingData(List<MappingData> mappingData) {
		this.mappingData = mappingData;
	}
}
