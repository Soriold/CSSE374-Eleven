package src.problem.commands;

import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.AdapterSpotter;

public class AdapterDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		AdapterSpotter s = new AdapterSpotter();
		
		s.spot(m);

	}

}
