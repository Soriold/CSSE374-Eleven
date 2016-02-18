package GUI;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.components.Model;

import com.jidesoft.swing.*;

public class ClassListPanel extends JScrollPane {

	private ArrayList<String> patterns;
	private CheckBoxTree tree;

	public ClassListPanel(IModel m) throws IOException {
		patterns = new ArrayList<String>();

		loadPatterns();

		createCheckBoxTree(m);

		GenerateUMLButton generate = new GenerateUMLButton("Generate UML", tree);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(0, 0));
		buttonPanel.add(generate, BorderLayout.SOUTH);
		this.setViewportView(buttonPanel);
	}

	private void createCheckBoxTree(IModel m) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patterns");
		DefaultTreeModel model = new DefaultTreeModel(root);
		tree = new CheckBoxTree(model);

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
