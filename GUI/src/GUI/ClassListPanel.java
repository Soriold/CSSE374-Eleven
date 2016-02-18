package gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.jidesoft.swing.CheckBoxTree;
import com.jidesoft.swing.CheckBoxTreeSelectionModel;

import src.problem.components.IClass;
import src.problem.components.IModel;

public class ClassListPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525384631048678405L;
	private ArrayList<String> patterns;
	private CheckBoxTree tree;
	private List<IClass> selectedClasses;
	private IModel m;

	public ClassListPanel(IModel m, Properties props) throws IOException {
		patterns = new ArrayList<String>();
		this.selectedClasses = new ArrayList<IClass>();

		loadPatterns();
		this.m = m;

		createCheckBoxTree(m);

//		JButton generate = new JButton("Generate UML");
//		
//		generate.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				getSelectedCheckBoxes(m);
//			}
//		});
//
//		JPanel buttonPanel = new JPanel();
//		buttonPanel.setLayout(new BorderLayout(0, 0));
//		buttonPanel.add(generate, BorderLayout.SOUTH);
//		this.setViewportView(buttonPanel);
	}

	public List<IClass> getSelectedClasses() {
		getSelectedCheckBoxes(this.m);
		return this.selectedClasses;
	}

	private void getSelectedCheckBoxes(IModel classModel) {
		CheckBoxTreeSelectionModel model = tree.getCheckBoxTreeSelectionModel();
		TreePath[] paths = model.getSelectionPaths();
		for(int i = 0; i < paths.length; i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
			String className = (String) node.getUserObject();
			if(className.equals("Patterns")) {
				selectedClasses = classModel.getClasses();
			}else if(patterns.contains(className) || className.equals("NONE")) {
				for(int j = 0; j < node.getChildCount(); j++) {
					DefaultMutableTreeNode curr = (DefaultMutableTreeNode) node.getChildAt(j);
					selectedClasses.add(findClass((String) curr.getUserObject(), classModel));
				}
			}else {
				selectedClasses.add(findClass(className, classModel));
			}
		}
	}

	private IClass findClass(String className, IModel classModel) {
		List<IClass> classes = classModel.getClasses();
		for(IClass c : classes) {
			if(c.getName().equals(className)) {
				return c;
			}
		}
		return null;
	}

	private void createCheckBoxTree(IModel m) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patterns");
	    TreeModel treeModel = new DefaultTreeModel(root);
		//CheckBoxTreeSelectionModel model = new CheckBoxTreeSelectionModel(treeModel);
		tree = new CheckBoxTree();
		tree.setModel(treeModel);

		for (String s : patterns) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(s);
			findClassesWithPattern(s, m, node);
			root.add(node);
		}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode("NONE");
		findClassesWithPattern("NONE", m, node);
		root.add(node);
		expandAllNodes(tree, 0, tree.getRowCount());

		tree.getCheckBoxTreeSelectionModel().addSelectionPath(tree.getPathForRow(0));

		this.setColumnHeaderView(tree);
	}

	private void loadPatterns() throws FileNotFoundException, IOException {
		BufferedReader in = new BufferedReader(new FileReader("patternTypesConfig.txt"));
		String line = "";
		while ((line = in.readLine()) != null) {
			String[] current = line.split("-");
			patterns.add(current[0]);
		}
		in.close();
	}

	private void findClassesWithPattern(String s, IModel m, DefaultMutableTreeNode node) {
		for (IClass c : m.getClasses()) {
			if (c.getPattern().equals(s)) {
				node.add(new DefaultMutableTreeNode(c.getName()));
			}
		}
	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = startingIndex; i < rowCount; ++i) {
			tree.expandRow(i);
		}

		if (tree.getRowCount() != rowCount) {
			expandAllNodes(tree, rowCount, tree.getRowCount());
		}
	}

}
