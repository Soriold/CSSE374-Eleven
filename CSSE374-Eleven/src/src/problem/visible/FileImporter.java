package src.problem.visible;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileImporter {

	public static List<byte[]> getClassesFromInputFolder(String inputPath) throws IOException, Exception {
		List<byte[]> ret = new ArrayList<byte[]>();
		File folder = new File(inputPath);
		if(folder.listFiles() == null) {
			throw new FileNotFoundException("No files in the given folder.");
		}
		for (File fileEntry : folder.listFiles()) {
			ret.add(getFileByteArray(fileEntry.toPath()));
		}
		return ret;
	}

	private static byte[] getFileByteArray(Path path) throws IOException {
		return Files.readAllBytes(path);

	}

}
