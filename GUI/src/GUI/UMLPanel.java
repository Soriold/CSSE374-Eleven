package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import src.problem.components.Model;

public class UMLPanel extends JScrollPane {
	
	public UMLPanel(Component arg0) {
		super(arg0);
	}

	private BufferedImage image;
	
	@Override
	protected void paintComponent(Graphics g) {
		 try {
			image = ImageIO.read(new File("input-output\\uml.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         JLabel picLabel = new JLabel(new ImageIcon(image));
         this.add(picLabel);
	}
    

}
