package src.problem.components;

import src.problem.outputvisitor.IVisitor;

public class Parameter implements IParameter {

	private String type;

	public Parameter() {

	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (!type.equals(other.type))
			return false;
		return true;
	}

}
