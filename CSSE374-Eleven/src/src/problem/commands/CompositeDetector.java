package src.problem.commands;

import java.util.Properties;

import src.problem.components.IModel;
import src.problem.patternrecognition.CompositeSpotter;

public class CompositeDetector implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws Exception {
		CompositeSpotter s = new CompositeSpotter();
		s.spot(m);

	}

}
