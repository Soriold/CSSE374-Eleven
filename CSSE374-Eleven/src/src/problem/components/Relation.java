package src.problem.components;

import src.problem.outputvisitor.IVisitor;

public class Relation implements IRelation {
	
	private String dest;
	private String src;
	private RelationType type;

	public Relation(String src, String dest, RelationType type) {
		super();
		this.dest = dest;
		this.src = src;
		this.type = type;
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
	public RelationType getType() {
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
	public void setType(RelationType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		IRelation relation = (Relation)obj;
		boolean destEquals = this.dest.equals(relation.getDest());
		boolean srcEquals = this.src.equals(relation.getSrc());
		boolean typeEquals = this.type == relation.getType() ||
							this.type == RelationType.ASSOCIATION && relation.getType() == RelationType.USES ||
							this.type == RelationType.USES && relation.getType() == RelationType.ASSOCIATION;
		return destEquals && srcEquals && typeEquals;
	}

	//@Override
	//public void accept(IVisitor v) {
		
		
	//}
}
