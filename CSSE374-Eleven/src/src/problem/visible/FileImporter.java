package src.problem.visible;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileImporter {
	
	public static List<byte[]> getClasses(String input) throws IOException, Exception {
		if (input.contains(",")) {
			return getClassesFromInputFiles(input.split(","));
		} else {
			return getClassesFromInputFolder(input);
		}
	}
	
	private static List<byte[]> getClassesFromInputFolder(String inputPath) throws IOException, Exception {
		List<byte[]> ret = new ArrayList<byte[]>();
		File folder = new File(inputPath);
		if (folder.listFiles() == null) {
			throw new FileNotFoundException("No files in the given folder.");
		}
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				ret.addAll(getClassesFromInputFolder(fileEntry.getAbsolutePath()));
			} else {
				ret.add(getFileByteArray(fileEntry.toPath()));
			}
		}
		return ret;
	}
	
	private static List<byte[]> getClassesFromInputFiles(String[] paths) throws IOException, Exception {
		List<byte[]> ret = new ArrayList<byte[]>();
		for (String s : paths) {
			ret.add(getFileByteArray(Paths.get(s)));
		}
		return ret;
	}

	private static byte[] getFileByteArray(Path path) throws IOException {
		return Files.readAllBytes(path);

	}

}
