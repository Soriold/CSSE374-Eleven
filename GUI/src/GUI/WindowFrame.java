package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.components.Model;
import src.problem.visible.DesignParser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;

public class WindowFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1825270332058411713L;
	private JPanel contentPane;
	public Model m;
	private Properties props;
	private ClassListPanel panel;
	private JFrame configFrame;

	/**
	 * Create the frame.
	 * 
	 * @param props
	 * @param configFrame
	 * 
	 * @throws Exception
	 */
	public WindowFrame(String outputPath, Properties props, JFrame configFrame) throws Exception {
		this.props = props;
		this.configFrame = configFrame;
		setupFrame();
		UMLBuilder.buildUML(outputPath);
		setupClassListPanel();
		setupUMLPanel();
	}

	private void setupUMLPanel() {
		JScrollPane UMLPanel = new JScrollPane(new JLabel(new ImageProxy("input-output\\uml.png")));
		contentPane.add(UMLPanel);
		UMLPanel.setPreferredSize(new Dimension((int) (contentPane.getWidth() * .7), contentPane.getHeight() - 100));
	}

	private void setupClassListPanel() throws IOException {
		JPanel leftPanel = new JPanel();
		panel = new ClassListPanel(DesignParser.getInstance().getModel(), props);
		
		JButton generate = new JButton("Generate UML");
		
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Properties p = PropertiesGenerator.getPropertiesFromClasses(panel.getSelectedClasses(), props);
				updateUML(p);
			}
		});
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		leftPanel.setLayout(new BorderLayout(0, 0));
		leftPanel.add(panel);
		leftPanel.add(generate, BorderLayout.SOUTH);
		contentPane.add(leftPanel);
		panel.setPreferredSize(new Dimension((int) (contentPane.getWidth() * .25), contentPane.getHeight() - 100));
	}

	private void setupFrame() {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 800);
		setupMenuBar();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenuItem mnFile = new JMenu("File");
		menuBar.add(mnFile);
		JMenuItem mntmLoad = new JMenuItem("Load a new configuration file");
		mntmLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigFrame frame = new ConfigFrame();
				frame.setVisible(true);
				configFrame.dispose();
				closeWindow();
			}

		});
		mnFile.add(mntmLoad);
		JMenuItem mntmExport = new JMenuItem("Export...");
		mntmExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser exporter = new JFileChooser();
				int retVal = exporter.showSaveDialog(null);
				if (retVal == JFileChooser.APPROVE_OPTION) {
					File f = exporter.getSelectedFile();
					BufferedImage image;
					if (!exporter.getSelectedFile().getAbsolutePath().endsWith(".png")) {
						f = new File(exporter.getSelectedFile().getAbsolutePath() + ".png");
					}
					try {
						image = ImageIO.read(new File("input-output\\uml.png"));
						ImageIO.write(image, "png", f);
						JOptionPane.showMessageDialog(contentPane, "Export successful.");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(contentPane, "Error exporting image.");
					}
				}
			}

		});
		mnFile.add(mntmExport);
		JMenuItem mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		JMenuItem mntmInstructions = new JMenuItem("Instructions");
		mntmInstructions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File("docs\\Supporting Documentation.pdf"));
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(contentPane, "Error opening help file.");
				}
			}
			
		});
		mnHelp.add(mntmInstructions);
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, 
						"Version 1.0 (2016)\nCreated by Ben Kimmel, Tayler How, and Shayna Oriold.", 
						"About UML Generator", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		mnHelp.add(mntmAbout);
	}

	protected void closeWindow() {
		this.dispose();
	}

	private void updateUML(Properties p) {
		List<IClass> classes = panel.getSelectedClasses();

		String path = (String) props.get("Input-Folder");
		ArrayList<String> classesToParse = new ArrayList<String>();
		for (IClass c : classes) {
			classesToParse.add(path + "\\" + c.getName() + ".class");
		}
		String[] classList = (String[]) classesToParse.toArray();
		// props.setProperty(key, value)
	}

}
