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

/**
 * The DataStandardizer class standardizes the Business Intelligence data provided by 
 * Google and Microsoft to a common format.
 * 
 * @author Chandan R. Rupakheti
 * @author Mark Hays
 */
public class DataStandardizer {
	protected String company;
	protected String data;
	protected JFrame frame;

	protected JPanel topPanel;
	protected JTextField txtField;
	protected JButton button;

	protected JScrollPane scrollPane;
	protected JTextArea textArea;
	protected JLabel label;
	
	
	public DataStandardizer() {
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
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), charset)) {

			// First line represents the name of a company
			this.company = reader.readLine();
		    
			// The rest is the data
		    StringBuffer buffer = new StringBuffer();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	
		    	if(company.equals("google")) {	// Change: geo1 - 100 => geo1 : 100
		    		buffer.append(line.replace('-', ':'));
		    	}
		    	else { // Its Microsoft, Change: ms1,100 => ms : 100
		    		String[] fields = line.split(",");
		    		buffer.append(fields[0].trim());
		    		buffer.append(" : ");
		    		buffer.append(fields[1].trim());
		    	}
		    	buffer.append("\n");
		    }
		    
		    // Done unifying the data
		    this.data = buffer.toString();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}	
	}
	
	
	public String getCompany() {
		return this.company;
	}
	
	public String getData() {
		return this.data;
	}
	
	// The main method
	public static void main(String[] args) {
		DataStandardizer unifier = new DataStandardizer();
		unifier.execute();
	}	
}
