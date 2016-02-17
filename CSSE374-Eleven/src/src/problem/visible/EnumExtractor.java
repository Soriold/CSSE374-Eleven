package src.problem.visible;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class EnumExtractor {

	public static Set<String> extractKeys(String path) throws IOException {
		Properties defaultProps = getPropertiesFromPath(path);
		return extractKeys(defaultProps);
	}

	public static Set<String> extractKeys(Properties p) {
		Set<String> ret = new HashSet<String>();
		for (Object key : p.keySet()) {
			ret.add((String) key);
		}
		return ret;
	}

	public static Set<String> extractValues(String path) throws IOException {
		Properties defaultProps = getPropertiesFromPath(path);
		return extractValues(defaultProps);
	}

	public static Set<String> extractValues(Properties p) {
		Set<String> ret = new HashSet<String>();
		for (Object key : p.keySet()) {
			ret.add((String) p.getProperty((String) key));
		}
		return ret;
	}

	public static Map<String, String> extractKVPairs(String path) throws IOException {
		Properties defaultProps = getPropertiesFromPath(path);
		return extractKVPairs(defaultProps);
	}

	public static Map<String, String> extractKVPairs(Properties p) {
		Map<String, String> ret = new HashMap<String, String>();
		for (Object key : p.keySet()) {
			ret.put((String) key, (String) p.getProperty((String) key));
		}
		return ret;
	}
	
	private static Properties getPropertiesFromPath(String path) throws FileNotFoundException, IOException {
		Properties defaultProps = new Properties();
		if(path == null) {
			return defaultProps;
		}
		FileInputStream in = new FileInputStream(path);
		defaultProps.load(in);
		in.close();
		return defaultProps;
	}

}
