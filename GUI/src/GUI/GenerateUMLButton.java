package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import com.jidesoft.swing.CheckBoxTree;

public class GenerateUMLButton extends JButton {
	
	private CheckBoxTree tree;
	private ArrayList<String> selectedClasses;

	public GenerateUMLButton(String string, CheckBoxTree tree) {
		super(string);
		this.tree = tree;
		setPreferredSize(new Dimension(150, 25));
		setVisible(true);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				regenerateUML();
			}
			
		});
	}

	protected void regenerateUML() {
		getSelectedCheckBoxes();
	}

	private ArrayList<String> getSelectedCheckBoxes() {
		return selectedClasses;
		//DefaultMutableTreeNode root = tree.get
		
	}
	
	

}
