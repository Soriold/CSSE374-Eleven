package GUI;

import java.awt.EventQueue;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import src.problem.visible.DesignParser;

public class Analyzer implements Runnable {

	private DesignParser dp;
	private Properties props;
	private JFrame frame;
	private String error;

	public Analyzer(DesignParser dp, Properties props, JFrame frame) {
	        this.dp = dp;
	        this.props = props;
	        this.frame = frame;
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

