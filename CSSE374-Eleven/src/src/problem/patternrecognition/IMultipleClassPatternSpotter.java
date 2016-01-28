package src.problem.patternrecognition;

import src.problem.components.IModel;
import src.problem.components.PatternType;

public interface IMultipleClassPatternSpotter {

	public PatternType spot(IModel c);
}
