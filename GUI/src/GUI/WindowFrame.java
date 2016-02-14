package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.problem.asm.DesignParser;
import src.problem.components.IClass;
import src.problem.components.Model;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.outputvisitor.SDEditOutputStream;
import src.problem.patternrecognition.PatternRecognizer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.awt.Dimension;
import javax.swing.JTree;
import javax.swing.JList;

public class WindowFrame extends JFrame {

	private JPanel contentPane;
	public Model m;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowFrame frame = new WindowFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public WindowFrame() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream("default.properties");
		defaultProps.load(in);
		in.close();
		DesignParser dp = DesignParser.getInstance();
//		System.out.println(Arrays.toString(
//				findClasses("S:\\GitHub\\CSSE374-Eleven\\CSSE374-Eleven\\CSSE374-Eleven\\asm-all-5.0.4.jar")));
		dp.run(defaultProps);
		
		Model m = dp.getModel();
		
		BufferedReader reader = new BufferedReader(new FileReader("input-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
		dp.generateGV(b.toString());
		
		ClassListPanel classListPanel = new ClassListPanel(m);
		contentPane.add(classListPanel);
		classListPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.25), contentPane.getHeight() - 65));

		UMLPanel UMLPanel = new UMLPanel(new JLabel(new ImageProxy("input-output\\uml.png")));
		contentPane.add(UMLPanel);
		UMLPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.7), contentPane.getHeight() - 65));
	}

}
