package src.problem.components;

import java.util.List;

import src.problem.outputvisitor.IGraphVizComponent;

public interface IMethod extends IGraphVizComponent {

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

}
