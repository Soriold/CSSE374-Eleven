package src.problem.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import src.problem.components.IModel;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.visible.EnumExtractor;

public class DotGenerator implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws IOException {
		String outputPath = prop.getProperty("Output-Directory");
		GraphVizOutputStream gvos = new GraphVizOutputStream(new FileOutputStream(outputPath + "\\GVOutput.txt"));
		
		String patternPath = prop.getProperty("Pattern-Types", null);
		String relationPath = prop.getProperty("Relation-Types", null);
		String tagPath = prop.getProperty("Tag-Types", null);
		
		GraphVizOutputStream.setPatternTypes(EnumExtractor.extractKVPairs(patternPath));
		GraphVizOutputStream.setRelationTypes(EnumExtractor.extractKVPairs(relationPath));
		GraphVizOutputStream.setTagTypes(EnumExtractor.extractKVPairs(tagPath));
		
		gvos.write(m);
		gvos.close();
	}

}
