package gui;

import java.util.Properties;

import src.problem.visible.DesignParser;

public class Analyzer implements Runnable {

	private DesignParser dp;
	private Properties props;
	private String error;

	public Analyzer(DesignParser dp, Properties props) {
	        this.dp = dp;
	        this.props = props;
	        this.error = null;
	    }

	public void run() {
		try {
			dp.run(props);
		} catch (Exception e) {
			error = e.getMessage();
		}
	}
	
	public String getError() {
		return error;
	}
}

