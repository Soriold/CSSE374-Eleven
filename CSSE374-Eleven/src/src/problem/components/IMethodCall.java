package src.problem.components;

public interface IMethodCall {
	
	public IMethod getSourceMethod();
	public IMethod getDestMethod();
	
	public void setSourceMethod(IMethod m);
	public void setDestMethod(IMethod m);

//	public String getSourceClass();
//	public String getSourceMethod();
//	
//	public String getDestinationClass();
//	public String getDestinationMethod();
//	
//	public List<String> getSourceParameters();
//	public List<String> getDestParameters();
	
	public void setSourceClass(String clazz);
	public void setSourceMethod(String method);
	
	public void setDestinationClass(String clazz);
	public void setDestinationMethod(String method);
	
	public void addSourceParameter(String p);
	public void addDestParameter(String p);
	
	public void setDestReturnType(String t);
}
