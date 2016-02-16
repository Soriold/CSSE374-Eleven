package src.problem.commands;

import java.util.HashMap;
import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.SingletonSpotter;

public class SingletonDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		SingletonSpotter s = new SingletonSpotter();
		s.setParameters(getParameters(prop));
		s.spot(m);

	}

	private HashMap<String, String> getParameters(Properties prop) {
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("Singleton-RequireGetInstance", prop.getProperty("Singleton-RequireGetInstance"));
		return ret;
	}

}
