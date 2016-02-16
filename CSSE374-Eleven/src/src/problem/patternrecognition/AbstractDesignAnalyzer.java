package src.problem.patternrecognition;

import java.util.Map;

import src.problem.components.IModel;

public abstract class AbstractDesignAnalyzer implements IDesignAnalyzer {
	
	protected Map<String, String> params;
	protected IModel m;

	@Override
	public void setParameters(Map<String,String> params) {
		this.params = params;
	}

	
	public abstract void spot(IModel m);

}
