package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import src.problem.visible.DesignParser;
import src.problem.components.IModel;
import src.problem.components.Model;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.awt.Dimension;

public class WindowFrame extends JFrame {

	private JPanel contentPane;
	public Model m;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public WindowFrame(String outputPath) throws Exception {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		BufferedReader reader = new BufferedReader(new FileReader(outputPath + "\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
		generateGV(b.toString());
		
		ClassListPanel classListPanel = new ClassListPanel(m);
		contentPane.add(classListPanel);
		classListPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.25), contentPane.getHeight() - 65));

		JScrollPane UMLPanel = new JScrollPane(new JLabel(new ImageProxy("input-output\\uml.png")));
		contentPane.add(UMLPanel);
		UMLPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth()*.7), contentPane.getHeight() - 65));
	}
	
	public static void generateGV(String arg) {
		String path = "temp.dot";

		try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			writer.write(arg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessBuilder pb = new ProcessBuilder("graphviz-2.38\\release\\bin\\dot.exe", "-Tpng", "temp.dot", "-o",
				"input-output\\uml.png");
		try {
			File log = new File("errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			Process p = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
