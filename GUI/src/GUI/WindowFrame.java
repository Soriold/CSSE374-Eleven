package GUI;

import java.awt.Dimension;import java.awt.FlowLayout;<<<<<<<HEAD=======
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;import java.io.FileReader;>>>>>>>origin/master
import java.io.IOException;
import java.util.Properties;

import javax.imageio.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import src.problem.components.Model;
import src.problem.visible.DesignParser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

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
		panel = new ClassListPanel(DesignParser.getInstance().getModel(), props);
		contentPane.add(panel);
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
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
		mnHelp.add(mntmInstructions);
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
	}

	protected void closeWindow() {
		this.dispose();
	}

	public void updateUML(Properties p) {
		DesignParser dp = DesignParser.getInstance();

		try {
			dp.run(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		UMLBuilder.buildUML(p.getProperty("Output-Directory"));
		setupUMLPanel();
	}

}
