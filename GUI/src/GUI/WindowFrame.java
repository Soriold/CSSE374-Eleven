package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import src.problem.components.Model;
import src.problem.visible.DesignParser;

public class WindowFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1825270332058411713L;
	private JPanel contentPane;
	public Model m;
	private Properties props;

	/**
	 * Create the frame.
	 * @param props 
	 * 
	 * @throws Exception
	 */
	public WindowFrame(String outputPath, Properties props) throws Exception {
		this.props = props;
		setupFrame();
		UMLBuilder.buildUML(outputPath);
		setupClassListPanel();
		setupUMLPanel();
	}

	private void setupUMLPanel() {
		JScrollPane UMLPanel = new JScrollPane(new JLabel(new ImageProxy("input-output\\uml.png")));
		contentPane.add(UMLPanel);
		UMLPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth() * .7), contentPane.getHeight() - 65));
	}

	private void setupClassListPanel() throws IOException {
		ClassListPanel classListPanel = new ClassListPanel(DesignParser.getInstance().getModel(), props);
		contentPane.add(classListPanel);
		classListPanel
				.setPreferredSize(new Dimension((int) (contentPane.getWidth() * .25), contentPane.getHeight() - 65));
	}

	private void setupFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contentPane.setSize(new Dimension(this.getWidth(), this.getHeight()));
	}

}
