package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class Method implements IMethod{

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
		
		if (this.visibility.equals("public")) {
			ret.append("+ ");
		} else if (this.visibility.equals("private")) {
			ret.append("- ");
		} else if (this.visibility.equals("protected")) {
			ret.append("# ");
		}
		
		ret.append(this.name);
		ret.append("(");
		for (int i=0; i<this.parameters.size(); i++) {
			if (i!=0) ret.append(", ");
			ret.append(this.parameters.get(i).getGraphViz());
		}
		ret.append(") : ");
		ret.append(this.returnType);
		ret.append("\\l");
		
		return ret.toString();
	}

}