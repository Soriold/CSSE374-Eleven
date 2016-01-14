package src.problem.components;

import java.util.ArrayList;
import java.util.List;

import src.problem.outputvisitor.ITraverser;
import src.problem.outputvisitor.IVisitor;

public class Method implements IMethod, Comparable<Method> {

	private String name;
	private String owner;
	private String returnType;
	private String visibility;
	private List<String> modifiers;
	private List<IParameter> parameters;
	private List<IMethodCall> methodCalls;

	public Method() {
		this.modifiers = new ArrayList<String>();
		this.parameters = new ArrayList<IParameter>();
		this.methodCalls = new ArrayList<IMethodCall>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getOwner() {
		return owner;
	}
	
	@Override
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String getReturnType() {
		return returnType;
	}

	@Override
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String getVisibility() {
		return visibility;
	}

	@Override
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public List<String> getModifiers() {
		return modifiers;
	}

	@Override
	public void addModifier(String modifier) {
		this.modifiers.add(modifier);
	}

	@Override
	public List<IParameter> getParameters() {
		return parameters;
	}

	@Override
	public void addParameter(IParameter parameter) {
		this.parameters.add(parameter);
	}
	
	@Override
	public List<IMethodCall> getMethodCalls() {
		return this.methodCalls;
	}
	
	@Override
	public void addMethodCall(IMethodCall mc) {
		this.methodCalls.add(mc);
	}

//	@Override
//	public String getGraphViz() {
//		StringBuilder ret = new StringBuilder();
//
//		if (this.visibility.equals("public")) {
//			ret.append("+ ");
//		} else if (this.visibility.equals("private")) {
//			ret.append("- ");
//		} else if (this.visibility.equals("protected")) {
//			ret.append("# ");
//		}
//
//		ret.append(this.name);
//		ret.append("(");
//		for (int i = 0; i < this.parameters.size(); i++) {
//			if (i != 0)
//				ret.append(", ");
//			ret.append(this.parameters.get(i).getGraphViz());
//		}
//		ret.append(") : ");
//		ret.append(this.returnType);
//		ret.append("\\l");
//
//		return ret.toString();
//	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for (IParameter p : this.parameters) {
			ITraverser t = (ITraverser) p;
			t.accept(v);
		}
		v.visit(this);
		v.postVisit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.owner == null) ? 0 : this.owner.hashCode());
		result = prime * result + ((this.parameters == null) ? 0 : this.parameters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (this.name == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.name.equals(other.getName()))
			return false;
		if (this.owner == null) {
			if (other.getOwner() != null)
				return false;
		} else if (!this.owner.equals(other.getOwner()))
			return false;
		if (this.parameters == null) {
			if (other.getParameters() != null)
				return false;
		} else if (!this.parameters.equals(other.getParameters()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Method o) {
		return this.name.compareTo(o.getName());
	}

	

}