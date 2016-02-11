package src.problem.commands;

import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.SingletonSpotter;

public class SingletonDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		SingletonSpotter s = new SingletonSpotter();
		
		s.spot(m);

	}

}
