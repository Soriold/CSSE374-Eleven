package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class Method implements IMethod, IGraphVizComponent {

	private String name;
	private String returnType;
	private String visibility;
	private List<String> modifiers;
	private List<IParameter> parameters;
	
	public Method() {
		this.modifiers = new ArrayList<String>();
		this.parameters = new ArrayList<IParameter>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public String getVisibility() {
		return visibility;
	}

	@Override
	public List<String> getModifiers() {
		return modifiers;
	}

	@Override
	public List<IParameter> getParameters() {
		return parameters;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public void addModifier(String modifier) {
		this.modifiers.add(modifier);
	}

	@Override
	public void addParameter(IParameter parameter) {
		this.parameters.add(parameter);
	}

	@Override
	public String getGraphViz() {
		StringBuilder ret = new StringBuilder();
		ret.append(this.name);
		ret.append(" [label = \"{");
		ret.append(this.name);
		ret.append("||");
		return ret.toString();
	}

}
