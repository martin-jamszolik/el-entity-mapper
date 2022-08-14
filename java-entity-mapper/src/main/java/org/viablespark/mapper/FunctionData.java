

package org.viablespark.mapper;

import java.util.List;

public class FunctionData {
	private String prefix;
	private String methodName;
	private String invocationClass;
	private String invocationMethod;
	private List<String> invocationMethodParameters;
	
	public String getInvocationMethod() {
		return invocationMethod;
	}
	public void setInvocationMethod(String invocationMethod) {
		this.invocationMethod = invocationMethod;
	}
	public List<String> getInvocationMethodParameters() {
		return invocationMethodParameters;
	}
	public void setInvocationMethodParameters(
			List<String> invocationMethodParameters) {
		this.invocationMethodParameters = invocationMethodParameters;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getInvocationClass() {
		return invocationClass;
	}
	public void setInvocationClass(String invocationClass) {
		this.invocationClass = invocationClass;
	}
}
