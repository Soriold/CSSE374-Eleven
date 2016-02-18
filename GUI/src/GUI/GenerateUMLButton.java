package GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.jidesoft.swing.*;

import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.visible.DesignParser;

public class GenerateUMLButton extends JButton {
	
	private CheckBoxTree tree;
	private List<IClass> selectedClasses;
	private ArrayList<String> patterns;
	private IModel classModel;
	private Properties props;

	public GenerateUMLButton(String string, CheckBoxTree tree, ArrayList<String> patterns, IModel m, Properties props) {
		super(string);
		this.tree = tree;
		this.patterns = patterns;
		this.classModel = m;
		this.selectedClasses = new ArrayList<IClass>();
		this.props = props;
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
		
		DesignParser dp = DesignParser.getInstance();
		//props.s
	}

	private void getSelectedCheckBoxes() {
		CheckBoxTreeSelectionModel model = tree.getCheckBoxTreeSelectionModel();
		TreePath[] paths = model.getSelectionPaths();
		for(int i = 0; i < paths.length; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
			String className = (String) node.getUserObject();
			if(className.equals("Patterns")) {
				selectedClasses = this.classModel.getClasses();
			}else if(patterns.contains(className) || className.equals("NONE")) {
				for(int j = 0; j < node.getChildCount(); j++) {
					DefaultMutableTreeNode curr = (DefaultMutableTreeNode) node.getChildAt(j);
					selectedClasses.add(findClass((String) curr.getUserObject()));
				}
			}else {
				selectedClasses.add(findClass(className));
			}
		}
	}

	private IClass findClass(String className) {
		List<IClass> classes = classModel.getClasses();
		for(IClass c : classes) {
			if(c.getName().equals(className)) {
				return c;
			}
		}
		return null;
	}
	
	
	

}
