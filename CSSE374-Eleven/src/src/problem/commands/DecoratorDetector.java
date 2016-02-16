package src.problem.commands;

import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.DecoratorSpotter;

public class DecoratorDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		DecoratorSpotter s = new DecoratorSpotter();
		s.spot(m);
	}

}
