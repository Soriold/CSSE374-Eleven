package src.problem.components;

import java.util.ArrayList;
import java.util.List;

import src.problem.asm.Pair;
import src.problem.outputvisitor.ITraverser;
import src.problem.outputvisitor.IVisitor;

public class Method implements IMethod {

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Method other = (Method) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		} else if (!parameters.equals(other.parameters)) {
			return false;
		} else if (!returnType.equals(other.returnType)) {
			return false;
		}
		return true;
	}

}