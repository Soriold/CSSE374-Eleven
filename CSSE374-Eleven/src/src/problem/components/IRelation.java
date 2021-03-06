package src.problem.components;

import java.util.Set;

import src.problem.outputvisitor.ITraverser;

public interface IRelation extends ITraverser, ITaggable {
	public String getDest();

	public String getSrc();

	public String getType();

	public void setDest(String dest);

	public void setSrc(String src);

	public void setType(String type);
	
	public void setLabel(String s);
	
	public String getLabel();
	
	public Set<String> getRelationTypes();

}
