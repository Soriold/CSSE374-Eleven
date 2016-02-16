package src.problem.patternrecognition;

import java.util.Map;

import src.problem.components.IModel;

public interface IDesignAnalyzer {

	public void setParameters(Map<String,String> params);
	public void spot(IModel m);
}
