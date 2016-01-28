package src.problem.components;

import src.problem.outputvisitor.ITraverser;

public interface IRelation extends ITraverser {
	public String getDest();

	public String getSrc();

	public RelationType getType();

	public void setDest(String dest);

	public void setSrc(String src);

	public void setType(RelationType type);
	
	public void setLabel(String s);
	
	public String getLabel();

}
