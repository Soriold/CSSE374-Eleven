package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UMLBuilder {

	public static void buildUML(String pathToOutput) {
//		String path = "temp.dot";
//
//		try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8,
//				StandardOpenOption.CREATE);) {
//			writer.write(pathToOutput);
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
