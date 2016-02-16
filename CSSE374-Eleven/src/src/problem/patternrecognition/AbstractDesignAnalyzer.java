package src.problem.patternrecognition;

import java.util.List;

import src.problem.components.IModel;

public abstract class AbstractDesignAnalyzer implements IDesignAnalyzer {
	
	protected List<String> params;
	protected IModel m;

	@Override
	public void setParameters(List<String> params) {
		this.params = params;
	}

	
	public abstract void spot(IModel m);

}
