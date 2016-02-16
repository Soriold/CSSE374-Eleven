package GUI;

import java.util.Properties;

import src.problem.visible.DesignParser;

public class AnalyzeThread implements Runnable {

	private DesignParser dp;
	private Properties props;

	public AnalyzeThread(DesignParser dp, Properties props) {
	        this.dp = dp;
	        this.props = props;
	    }

	public void run() {
		try {
			System.out.println("runnin");
			dp.run(props);
			System.out.println("done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

