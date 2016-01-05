package src.problem.components;

import java.util.List;

public interface IMethod {
	
	public String getName();
	public String getReturnType();
	public String getVisibility();
	public List<String> getModifiers();
	public List<IParameter> getParameters();
	
}
