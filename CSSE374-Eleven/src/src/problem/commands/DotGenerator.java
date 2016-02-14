package src.problem.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import src.problem.components.IModel;
import src.problem.outputvisitor.GraphVizOutputStream;

public class DotGenerator implements IPhase {

	@Override
	public void executeOn(IModel m, Properties prop) throws IOException {
		String outputPath = prop.getProperty("Output-Directory");
		GraphVizOutputStream gvos = new GraphVizOutputStream(new FileOutputStream(outputPath + "\\GVOutput.txt"));
		gvos.write(m);
		gvos.close();
	}

}
