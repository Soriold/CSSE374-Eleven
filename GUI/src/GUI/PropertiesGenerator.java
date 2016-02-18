package gui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import src.problem.components.IClass;

public class PropertiesGenerator {

	public static Properties getPropertiesFromClasses(List<IClass> classes, Properties prop) {
		Properties p;
		if (prop.getProperty("Input-Folder").contains(",")) {
			p = getPropertiesFromManyFiles(classes, prop);
		} else {
			p = getPropertiesFromFolder(classes, prop);
		}
		p = correctInputClasses(classes, p);
		return p;
	}

	private static Properties correctInputClasses(List<IClass> classes, Properties p) {
		String[] currentClasses = p.getProperty("Input-Classes").split(",");
		StringBuilder sb = new StringBuilder();
		ArrayList<String> newClasses = new ArrayList<String>();
		List<String> classNames = getClassNames(classes);
		for(String s : currentClasses) {
			if(contains(classNames, s)) {
				sb.append(s + ",");
				newClasses.add(s);
			}
		}
		String ctp = cleanSB(sb);
		p.setProperty("Input-Classes", ctp);
		return p;
	}

	private static String cleanSB(StringBuilder sb) {
		String ctp = sb.toString();
		if (ctp.length() > 0) {
			ctp = ctp.substring(0, ctp.length() - 1);
		}
		return ctp;
	}

	private static Properties getPropertiesFromFolder(List<IClass> classes, Properties prop) {
		String folder = prop.getProperty("Input-Folder");
		StringBuilder sb = new StringBuilder();
		for (IClass c : classes) {
			// sb.append(folder + "\\" + c.getName() + ".class,");
			try {
				sb.append(FileFinder.findFile(c.getName() + ".class", folder).getAbsolutePath() + ",");
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
			}
		}
		return buildProperties(prop, sb);
	}

	private static Properties getPropertiesFromManyFiles(List<IClass> classes, Properties prop) {

		List<String> clazzes = getClassNames(classes);
		String[] inputFiles = prop.getProperty("Input-Folder").split(",");
		StringBuilder sb = new StringBuilder();
		for (String file : inputFiles) {
			if (contains(clazzes, file)) {
				sb.append(file);
			}
		}
		return buildProperties(prop, sb);
	}

	private static Properties buildProperties(Properties prop, StringBuilder sb) {
		Properties ret = (Properties) prop.clone();
		ret.setProperty("Input-Folder", cleanSB(sb));
		return ret;
	}

	private static boolean contains(List<String> clazzes, String file) {
		for (String clazz : clazzes) {
			if (file.contains(clazz)) {
				return true;
			}
		}
		return false;
	}

	private static List<String> getClassNames(List<IClass> classes) {
		List<String> ret = new ArrayList<String>();
		for (IClass c : classes) {
			ret.add(c.getName());
		}
		return ret;
	}

}