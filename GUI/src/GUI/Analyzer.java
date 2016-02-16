package GUI;

import java.util.Properties;

import src.problem.visible.DesignParser;

public class Analyzer implements Runnable {

	private DesignParser dp;
	private Properties props;

	public Analyzer(DesignParser dp, Properties props) {
	        this.dp = dp;
	        this.props = props;
	    }

	public void run() {
		try {
			dp.run(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

