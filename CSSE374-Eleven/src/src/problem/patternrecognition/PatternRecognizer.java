package src.problem.patternrecognition;

import src.problem.components.IClass;
import src.problem.components.Model;

public class PatternRecognizer {

	private static ISingleClassPatternSpotter[] spotters = { new SingletonSpotter() };
	private static IMultipleClassPatternSpotter[] mSpotters = {};

	public static void recognize(IClass c) {
		for (ISingleClassPatternSpotter s : spotters) {
			s.spot(c);
		}
	}

	public static void recognize(Model model) {
		for (IMultipleClassPatternSpotter s : mSpotters) {
			s.spot(model);
		}
	}
}
