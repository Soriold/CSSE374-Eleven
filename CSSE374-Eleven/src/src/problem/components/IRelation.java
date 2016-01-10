package src.problem.components;

import src.problem.components.Relation.RelationType;

public interface IRelation {
	
	public String getDest();
	public String getSrc();
	public RelationType getType();
	public void setDest(String dest);
	public void setSrc(String src);
	public void setType(RelationType type);

}
