package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jidesoft.swing.CheckBoxTree;
import com.jidesoft.swing.CheckBoxTreeSelectionModel;

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
		CheckBoxTreeSelectionModel model = tree.getCheckBoxTreeSelectionModel();
		TreePath[] paths = tree.getSelectionPaths();
		System.out.println(paths);
		for(int i = 0; i < paths.length; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
			String className = (String) node.getUserObject();
			System.out.println(className);
		}
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		return null;
	}
	
	
	

}
