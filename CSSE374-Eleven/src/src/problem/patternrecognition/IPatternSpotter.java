package src.problem.patternrecognition;

import src.problem.components.IClass;
import src.problem.components.PatternType;

public interface IPatternSpotter {
	
	public PatternType spot(IClass c);
}
