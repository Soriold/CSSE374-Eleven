package src.problem.visible;

import java.io.FileInputStream;
import java.util.Properties;

public class Driver {

	public static void main(String[] args) throws Exception {
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream("default.properties");
		defaultProps.load(in);
		in.close();
		DesignParser p = DesignParser.getInstance();
		p.run(defaultProps);

	}

}
