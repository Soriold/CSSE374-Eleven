package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
	
	public static File findFile(String name, String directoryPath) throws FileNotFoundException {
		List<File> flattened = flatten(new File(directoryPath));
		for(File f: flattened) {
			if(f.getName().equals(name)) {
				return f;
			}
			System.out.println(f.getAbsolutePath());
		}
		System.out.println(name);
		throw new FileNotFoundException();
	}

	private static List<File> flatten(File file) {
		List<File> flattened = new ArrayList<File>();
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					flattened.addAll(flatten(f));
				} else {
					flattened.add(f);
				}
			} 
		} else {
			flattened.add(file);
		}
		return flattened;
	}

}
