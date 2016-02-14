package GUI;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import src.problem.components.IClass;
import src.problem.components.Model;

public class ClassListPanel extends JScrollPane {

	private List<IClass> classes;
	private ArrayList<JCheckBox> checkboxes;

	public ClassListPanel(Model m) {

		CheckBoxList list = new CheckBoxList();
		list.setCellRenderer(new CheckboxListRenderer());
		checkboxes = new ArrayList<JCheckBox>();

		for (IClass c : m.getClasses()) {
			JCheckBox current = new JCheckBox();
			current.setLabel(c.getName());
			current.setSelected(true);
			checkboxes.add(current);
		}
		list.setListData(checkboxes.toArray(new JCheckBox[checkboxes.size()]));
		this.setViewportView(list);

	}

}
