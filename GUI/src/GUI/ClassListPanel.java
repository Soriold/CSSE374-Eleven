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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

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
	    TreeModel treeModel = new DefaultTreeModel(root);
		CheckBoxTreeSelectionModel model = new CheckBoxTreeSelectionModel(treeModel);
		tree = new CheckBoxTree();
		tree.setSelectionModel(model);

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
		
//		tree.getCheckBoxTreeSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
//            public void valueChanged(TreeSelectionEvent e) {
//                TreePath[] paths = e.getPaths();
//                for (TreePath path : paths) {
//                    model.addElement((e.isAddedPath(path) ? "Added - " : "Removed - ") + path);
//                }
//                eventsModel.addElement("---------------");
//                eventsList.ensureIndexIsVisible(eventsModel.size() - 1);
//
//                TreePath[] treePaths = _tree.getCheckBoxTreeSelectionModel().getSelectionPaths();
//                DefaultListModel selectedModel = new DefaultListModel();
//                if (treePaths != null) {
//                    for (TreePath path : treePaths) {
//                        selectedModel.addElement(path);
//                        for (TreePath childPath : getChildrenElement(path)) {
//                            selectedModel.addElement(childPath);
//                        }
//                    }
//                }
//                selectedList.setModel(selectedModel);
//            }
//
//            private List<TreePath> getChildrenElement(TreePath parentPath) {
//                List<TreePath> childList = new ArrayList<TreePath>();
//                Object parentNode = parentPath.getLastPathComponent();
//                int childCount = _tree.getModel().getChildCount(parentNode);
//                for (int i = 0; i < childCount; i++) {
//                    Object child = _tree.getModel().getChild(parentNode, i);
//                    final TreePath childPath = parentPath.pathByAddingChild(child);
//                    childList.add(childPath);
//                    childList.addAll(getChildrenElement(childPath));
//                }
//                return childList;
//            }
//        });

		
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
