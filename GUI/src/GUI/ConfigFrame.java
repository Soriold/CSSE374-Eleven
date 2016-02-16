package GUI;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import src.problem.visible.DesignParser;

public class ConfigFrame extends JFrame {
	
	private File configFile;
	private String configPath;
	
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigFrame frame = new ConfigFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ConfigFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JButton btnLoadConfig = new JButton("Load Properties");
		
		JLabel progressBarText = new JLabel("Properties file not selected.");
		progressBarText.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnLoadConfig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser c = new JFileChooser();
			      int rVal = c.showOpenDialog(ConfigFrame.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) {
			        configFile = c.getSelectedFile();
			        if(configFile.getName().endsWith(".properties")) {
			        	configPath = c.getCurrentDirectory().toString();
				        progressBarText.setText("Selected properties file: " + configFile.getName());
			        } else {
				        progressBarText.setText("Invalid properties file. Please select a file ending in .properties");
			        }
			      }

			}
			
		});
		
		JButton btnAnalyze = new JButton("Analyze");
		
		JProgressBar progressBar = new JProgressBar();
		
		btnAnalyze.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				DesignParser dp = DesignParser.getInstance();
				
				Properties props = new Properties();
				try {
					FileInputStream in = new FileInputStream(configFile.getAbsolutePath());
					props.load(in);
					in.close();
					
					Analyzer analyzer = new Analyzer(dp, props);
			        Thread t = new Thread(analyzer);
			        t.start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				while(dp.getCurrentPhase() == null) {
					progressBarText.setText("Beginning parse...");
				}
				String[] phases = props.getProperty("Phases").split(",");
				
				while(dp.getCurrentPhase() != null && dp.getCurrentPhase().equals("Class-Loading")) {
					progressBarText.setText("Running Class-Loading on " + dp.getCurrentParseClass());
					progressBar.setValue(100 / phases.length);
				}
				
				for(int i = 1; i < phases.length; i++) {
					while(dp.getCurrentPhase() != null && dp.getCurrentPhase().equals(phases[i])) {
						progressBarText.setText("Running " + phases[i]);
						progressBar.setValue((i+1)*100 / phases.length);
					}
				}
				
				progressBarText.setText("Done!");
				
				try {
					WindowFrame frame = new WindowFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(97)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnLoadConfig)
									.addGap(39)
									.addComponent(btnAnalyze, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(progressBarText, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoadConfig)
						.addComponent(btnAnalyze))
					.addGap(32)
					.addComponent(progressBarText)
					.addGap(29)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(84))
		);
		getContentPane().setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		getContentPane().setLayout(groupLayout);
	}

	protected void closeConfigFrame() {
		this.dispose();
	}
}
