package src.problem.components;

import java.util.Set;

import src.problem.outputvisitor.IVisitor;

public class Relation extends AbstractTaggable implements IRelation {

	private String dest;
	private String src;
	private String type;
	private String label;
	private static Set<String> relationTypes;

	public Relation(String src, String dest, String type) {
		super();
		this.dest = dest;
		this.src = src;
		if (relationTypes.contains(type)) {
			this.type = type;
		} else {
			this.type = "";
		}
	}

	@Override
	public String getDest() {
		return dest;
	}

	@Override
	public String getSrc() {
		return src;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setDest(String dest) {
		this.dest = dest;
	}

	@Override
	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public void setType(String type) {
		if (relationTypes.contains(type)) {
			this.type = type;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		IRelation relation = (Relation) obj;
		boolean destEquals = this.dest.equals(relation.getDest());
		boolean srcEquals = this.src.equals(relation.getSrc());
		boolean typeEquals = this.type.equals(relation.getType())
				|| (this.type.equals("ASSOCIATION") && relation.getType().equals("USES"))
				|| (this.type.equals("USES") && relation.getType().equals("ASSOCIATION"));
		boolean result = destEquals && srcEquals && typeEquals;
		if (result && this.type.equals("USES") && relation.getType().equals("ASSOCIATION")) {
			this.type = "ASSOCIATION";
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((src == null) ? 0 : src.hashCode());
		if (type.equals("USES")) {
			result = prime * result + ((type == null) ? 0 : "ASSOCIATION".hashCode());
		} else {
			result = prime * result + ((type == null) ? 0 : type.hashCode());
		}
		return result;
	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

	@Override
	public void setLabel(String s) {
		this.label = s;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public Set<String> getRelationTypes() {
		return relationTypes;
	}

	public static void setRelationTypes(Set<String> types) {
		relationTypes = types;
	}
}
