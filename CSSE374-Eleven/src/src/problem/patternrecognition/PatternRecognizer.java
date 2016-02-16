package src.problem.patternrecognition;

import src.problem.components.Model;

public class PatternRecognizer {

	private static IDesignAnalyzer[] mSpotters = { new AdapterSpotter(), new DecoratorSpotter(),
			new SingletonSpotter(), new CompositeSpotter() };

	public static void recognize(Model model) {
		for (IDesignAnalyzer s : mSpotters) {
			s.spot(model);
		}
	}
}
