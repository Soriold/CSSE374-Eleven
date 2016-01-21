package src.problempatternrecognition;

import src.problem.components.IClass;
import src.problem.components.PatternType;

public class PatternRecognizer {

	private static IPatternSpotter[] spotters = {new SingletonSpotter()};
	
	public static PatternType recognize(IClass c) {
		for(IPatternSpotter s : spotters) {
			PatternType p = s.spot(c);
			if (p != PatternType.NOT_FOUND){
				return p;
			}
		}
		return PatternType.NONE;
	}
	
}
