package GUI;


import java.awt.Checkbox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;

import src.problem.components.IClass;
import src.problem.components.Model;

public class ClassListPanel extends JScrollPane {
	
	private List<IClass> classes;
	private ArrayList<Checkbox> checkboxes;
	
	public ClassListPanel(Model m) {
		
		JList list = new JList();
		DefaultListModel model = new DefaultListModel();
		
		for(IClass c : m.getClasses()) {
			model.addElement(c.getName());
		}
		add(list);
		list.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
		//list.setSize(new Dimension(this.getWidth(), this.getHeight()));
		
		
		classes = m.getClasses();
	}

}
