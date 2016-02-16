package src.problem.visible;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.StandardCopyOption;

public class FileImporter {

	public static List<byte[]> getClassesFromInputFolder(String inputPath) throws IOException, Exception {
		List<byte[]> inputClasses;
//		if (inputPath != null) {
//			System.out.println("copying");
//			Files.copy(Paths.get(inputPath), Paths.get("currentSrc\\"), StandardCopyOption.REPLACE_EXISTING);
//		}
		inputClasses = findClasses(inputPath);
		return inputClasses;
	}

	private static List<byte[]> findClasses(String path) throws Exception {
		List<byte[]> classNames = null;
		// if (path.endsWith(".jar")) {
		// classNames = findClassesFromJar(path);
		// } else {
		classNames = findClassesFromFolder(path);
		// }
		return classNames;
	}

	private static List<byte[]> findClassesFromFolder(String path) throws IOException {

		List<byte[]> ret = new ArrayList<byte[]>();
		File folder = new File(path);
		for (File fileEntry : folder.listFiles()) {
			ret.add(getFileByteArray(fileEntry.toPath()));
		}
		return ret;
	}

	private static byte[] getFileByteArray(Path path) throws IOException {
		return Files.readAllBytes(path);

	}

	// private static List<byte[]> findClassesFromJar(String path) throws
	// FileNotFoundException, IOException {
	// List<String> classNames;
	// classNames = new ArrayList<String>();
	// ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
	// for (ZipEntry entry = zip.getNextEntry(); entry != null; entry =
	// zip.getNextEntry()) {
	// if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
	// String className = entry.getName().replace('/', '.');
	// classNames.add(className.substring(0, className.length() -
	// ".class".length()));
	// }
	// }
	// zip.close();
	// return classNames;
	// }
}
