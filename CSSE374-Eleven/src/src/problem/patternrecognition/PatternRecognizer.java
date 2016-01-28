package src.problem.patternrecognition;

import src.problem.components.IClass;
import src.problem.components.Model;
import src.problem.components.PatternType;

public class PatternRecognizer {

	private static ISingleClassPatternSpotter[] spotters = {new SingletonSpotter()};
	
	public static PatternType recognize(IClass c) {
		for(ISingleClassPatternSpotter s : spotters) {
			PatternType p = s.spot(c);
			if (p != PatternType.NOT_FOUND){
				return p;
			}
		}
		return PatternType.NONE;
	}

	public static void recognize(Model model) {
		
		
	}
	
}
