
package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    public Icon img;

    public ImagePanel(Icon img){
        this.img = img;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, this);
    }

}
