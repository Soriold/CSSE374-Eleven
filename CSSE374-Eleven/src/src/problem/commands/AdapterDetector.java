package src.problem.commands;

import java.util.ArrayList;
import java.util.List;
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
	
	private List<String> getParameters(Properties prop) {
		List<String> ret = new ArrayList<String>();
		ret.add(prop.getProperty("Adapter-MethodDelegation"));
		return ret;
	}

}
