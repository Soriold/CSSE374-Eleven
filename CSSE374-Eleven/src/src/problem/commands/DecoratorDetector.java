package src.problem.commands;

import java.util.HashMap;
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

	
	private HashMap<String, String> getParameters(Properties prop) {
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("Decorator-MethodDelegation", prop.getProperty("Decorator-MethodDelegation"));
		return ret;
	}

}
