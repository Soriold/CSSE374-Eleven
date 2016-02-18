package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import src.problem.components.IClass;

public class PropertiesGenerator {

	public static Properties getPropertiesFromClasses(List<IClass> classes, Properties prop) {
		if (prop.getProperty("Input-Folder").contains(",")) {
			return getPropertiesFromManyFiles(classes, prop);
		} else {
			return getPropertiesFromFolder(classes, prop);
		}
	}

	private static Properties getPropertiesFromFolder(List<IClass> classes, Properties prop) {
		String folder = prop.getProperty("Input-Folder");
		StringBuilder sb = new StringBuilder();
		for (IClass c : classes) {
			sb.append(folder + "\\" + c.getName() + ".class,");
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
		String ctp = sb.toString();
		ctp = ctp.substring(0, ctp.length() - 1);
		ret.setProperty("Input-Folder", ctp);
		return ret;
	}

	private static boolean contains(List<String> clazzes, String file) {
		for(String clazz : clazzes) {
			if(file.contains(clazz)) {
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