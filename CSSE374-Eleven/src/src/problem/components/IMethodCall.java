package src.problem.components;

import java.util.List;

public interface IMethodCall {

	public String getSourceClass();
	public String getSourceMethod();
	
	public String getDestinationClass();
	public String getDestinationMethod();
	
	public List<String> getSourceParameters();
	public List<String> getDestParameters();
	
	public void setSourceClass(String clazz);
	public void setSourceMethod(String method);
	
	public void setDestinationClass(String clazz);
	public void setDestinationMethod(String method);
	
	public void addSourceParameter(String p);
	public void addDestParameter(String p);
}
