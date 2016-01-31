package src.problem.patternrecognition;

import src.problem.components.Model;

public class PatternRecognizer {

	private static IPatternSpotter[] mSpotters = { new AdapterSpotter(), new DecoratorSpotter(),
			new SingletonSpotter() };

	public static void recognize(Model model) {
		for (IPatternSpotter s : mSpotters) {
			s.spot(model);
		}
	}
}
