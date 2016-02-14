package problem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DataDisplay {
	protected String company;
	protected String data;
	protected JFrame frame;

	protected JPanel topPanel;
	protected JTextField txtField;
	protected JButton button;

	protected JScrollPane scrollPane;
	protected JTextArea textArea;
	protected JLabel label;
	
	private NewDataStandardizer standardizer;
	
	
	public DataDisplay() {
		this.standardizer = new NewDataStandardizer();
	}
	
	protected void createAndShowGUI() {
		frame = new JFrame("Data Unifier");
		
		topPanel = new JPanel();
		txtField = new JTextField("./input_output/io.gogl");
		txtField.setPreferredSize(new Dimension(200,25));
		topPanel.add(txtField);

		button = new JButton("Unify!");
		topPanel.add(button);

		// Add the top panel to the top of the window
		frame.add(topPanel, BorderLayout.NORTH);
		
		
		textArea = new JTextArea(5, 60);
		textArea.setPreferredSize(new Dimension(300, 200));		
		scrollPane = new JScrollPane(textArea);
		
		// Add the scroll pane to the center of the window
		frame.add(scrollPane, BorderLayout.CENTER);
		
		// Add the label as status
		label = new JLabel("<No Data>");
		frame.add(label, BorderLayout.SOUTH);

		// Add action listener to the button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Parse the source file
				parse(txtField.getText());
				label.setText("Company: " + getCompany());
				textArea.setText(getData());
			}
		});
		
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void execute() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// Basically, shows up the GUI.
            	createAndShowGUI();
            }
        });		
	}
	
	public void parse(String path) {
		this.data = this.standardizer.standardize(path);
		this.company = this.standardizer.getCompany(path);
	}
	
	
	public String getCompany() {
		return this.company;
	}
	
	public String getData() {
		return this.data;
	}
	
	// The main method
	public static void main(String[] args) {
		DataDisplay unifier = new DataDisplay();
		unifier.execute();
	}	
}
