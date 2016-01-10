package src.problem.components;

import src.problem.outputvisitor.IGraphVizComponent;

public interface IParameter extends IGraphVizComponent {

	public String getType();

	public void setType(String type);

}
