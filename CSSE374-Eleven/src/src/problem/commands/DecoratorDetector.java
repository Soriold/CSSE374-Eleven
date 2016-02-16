package src.problem.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.DecoratorSpotter;

public class DecoratorDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		DecoratorSpotter s = new DecoratorSpotter();
		s.setParameters(getParameters(prop));
		s.spot(m);
	}
	
	private List<String> getParameters(Properties prop) {
		List<String> ret = new ArrayList<String>();
		ret.add(prop.getProperty("Decorator-MethodDelegation"));
		return ret;
	}

}
