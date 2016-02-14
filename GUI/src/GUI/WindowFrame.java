package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import src.problem.asm.DesignParser;
import src.problem.components.IClass;
import src.problem.components.Model;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.outputvisitor.SDEditOutputStream;
import src.problem.patternrecognition.PatternRecognizer;

import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
	 * @throws IOException 
	 */
	public WindowFrame() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 856, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		DesignParser dp = new DesignParser();
		m = new Model();
		GraphVizOutputStream gvos = new GraphVizOutputStream(new FileOutputStream("GVOutput.txt"));

		Scanner scanner = new Scanner(new File("lab7-2Args.txt"));
		scanner.useDelimiter("\r\n");
		ArrayList<String> argumentsAL = new ArrayList<String>();
		while (scanner.hasNext()) {
			argumentsAL.add(scanner.next());
		}
		scanner.close();

		String[] arguments = new String[] {};
		arguments = argumentsAL.toArray(arguments);

		for (String className : arguments) {
			IClass clazz = dp.parse(className, m);
			m.addClass(clazz);
		}
		PatternRecognizer.recognize(m);
		gvos.write(m);
		gvos.close();
		
		
		ClassListPanel classListPanel = new ClassListPanel(m);
		contentPane.add(classListPanel);
		classListPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.25), contentPane.getHeight() - 65));

		UMLPanel UMLPanel = new UMLPanel(m);
		contentPane.add(UMLPanel);
		UMLPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.7), contentPane.getHeight() - 65));
	}

}
