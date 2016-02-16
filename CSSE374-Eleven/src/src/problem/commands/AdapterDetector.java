package src.problem.commands;

import java.util.HashMap;
import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.AdapterSpotter;

public class AdapterDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		AdapterSpotter s = new AdapterSpotter();
		s.setParameters(getParameters(prop));
		s.spot(m);

	}

	private HashMap<String, String> getParameters(Properties prop) {
		HashMap<String, String> ret = new HashMap<String, String>();
		ret.put("Adapter-MethodDelegation", prop.getProperty("Adapter-MethodDelegation"));
		return ret;
	}

}
