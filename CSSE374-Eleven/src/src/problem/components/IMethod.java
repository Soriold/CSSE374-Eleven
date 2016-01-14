package src.problem.components;

import java.util.List;

import src.problem.asm.Pair;
import src.problem.outputvisitor.ITraverser;

public interface IMethod extends ITraverser {

	public String getName();

	public void setName(String name);

	public String getReturnType();

	public void setReturnType(String returnType);

	public String getVisibility();

	public void setVisibility(String visibility);

	public List<String> getModifiers();

	public void addModifier(String modifier);

	public List<IParameter> getParameters();

	public void addParameter(IParameter parameter);

	public List<Pair<String, String>> getMethodCalls();
	
	public void addMethodCall(String method, String clazz);
}
