package lab13code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class TXTHandler implements IHandler {

	@Override
	public Process eventProcessed(String event, Path f) {
		ProcessBuilder processBuilder = null;
		String command = null;
		String arg = null;
		
		String fileName = f.toString();
		System.out.println("Processing " + fileName + "...");
	
		if(fileName.endsWith(".txt")) {
			command = "Notepad";
			arg = fileName;
		} else {
			return null;
		}

		// Run the application if support is available
		try {
			System.out.format("Launching %s ...%n", command);
			processBuilder = new ProcessBuilder(command, arg);

			// Start and add the process to the processes list
			Process process = processBuilder.start();
			return process;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
