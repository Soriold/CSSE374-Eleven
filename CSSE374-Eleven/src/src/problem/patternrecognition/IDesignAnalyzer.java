package src.problem.patternrecognition;

import java.util.List;

import src.problem.components.IModel;

public interface IDesignAnalyzer {

	public void setParameters(List<String> params);
	public void spot(IModel m);
}
