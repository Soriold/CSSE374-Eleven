package GUI;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class UMLBuilder {

	public static void buildUML(String pathToOutput) {
		ProcessBuilder pb = new ProcessBuilder("graphviz-2.38\\release\\bin\\dot.exe", "-Tpng", pathToOutput, "-o",
				"input-output\\uml.png");
		try {
			File log = new File("errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
