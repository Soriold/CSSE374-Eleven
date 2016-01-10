package src.problem.components;

import src.problem.outputvisitor.ITraverser;

public interface IParameter extends ITraverser {

	public String getType();

	public void setType(String type);

}
