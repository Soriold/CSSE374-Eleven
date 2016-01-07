package src.problem.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllClasses implements IGraphVizComponent {
	private List<IClass> classes;
	private Set<String> edges;

	public AllClasses() {
		this.classes = new ArrayList<>();
		this.edges = new HashSet<>();
	}

	public void addClass(IClass clazz) {
		this.classes.add(clazz);
	}

	public List<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public String getGraphViz() {
		StringBuilder ret = new StringBuilder();
		ret.append("digraph G {fontname = \"Bitstream Vera Sans\" fontsize = 8\nnode [fontname ="
				+ "\"Bitstream Vera Sans\" fontsize = 8 shape = \"record\"] edge [fontname = "
				+ "\"Bitstream Vera Sans\" fontsize = 8]");
		for (IClass clazz : this.classes) {
			ret.append(clazz.getGraphViz());
			ret.append("\n");
		}
		ret.append(this.getEdges());
		ret.append("\n");
		ret.append("}");
		String mod = ret.toString().replace("<", "\\<");
		mod = mod.replace(">", "\\>");
		mod = mod.replace("$", ">");
		return mod;
	}

	private String getEdges() {
		StringBuilder ret = new StringBuilder();
		for (IClass c : this.classes) {
			ret.append(getAssociations(c));
			this.getInterfacesEdges(c);
			this.getSuperClassEdges(c.getName(), c.getSuperClass());
			this.getUsesEdges(c);
		}
		for( String s : this.edges) {
			ret.append(s);
		}
		return ret.toString();
	}

	private void getUsesEdges(IClass clazz) {
		ArrayList<String> classNames = this.getClassNames();
		for (IMethod m : clazz.getMethods()) {
			if (classNames.contains(m.getReturnType())) {
				this.createUsesEdge(clazz.getName(), m.getReturnType());
			}
			for (IParameter p : m.getParameters()) {
				if (classNames.contains(p.getType())) {
					this.createUsesEdge(clazz.getName(), p.getType());
				}
			}
		}
		
		for (String usedClass : clazz.getUsedClasses()) {
			if (classNames.contains(usedClass)) {
				this.createUsesEdge(clazz.getName(), usedClass);
			}
		}
	}

	private void getSuperClassEdges(String name, String superClass) {
		if (this.getClassNames().contains(superClass)) {
			this.createExtendsEdge(name, superClass);
		}
	}

	private void getInterfacesEdges(IClass c) {
		ArrayList<String> classNames = this.getClassNames();
		for (String s : c.getInterfaces()) {
			if (classNames.contains(s)) {
				if (c.getIsInterface()) {
					this.createExtendsEdge(c.getName(), s);
				} else {
					this.createImplementsEdge(c.getName(), s);
				}
			}
		}
	}

	private String getAssociations(IClass c) {
		StringBuilder ret = new StringBuilder();
		List<IField> fields = c.getFields();
		for(IField f : fields) {
			if(this.getClassNames().contains(f.getType())) {
				createAssociationEdge(c.getName(), f.getType());
			}
		}
		return ret.toString();
	}

	private void createImplementsEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"onormal\" style = \"dashed\" ]\n" + src + " -$ " + dest + "\n";
		this.edges.add(ret);
	}

	private void createExtendsEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"onormal\" style = \"solid\" ]\n" + src + " -$ " + dest + "\n";
		this.edges.add(ret);
	}

	private void createUsesEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"vee\" style = \"dashed\" ]\n" + src + " -$ " + dest + "\n";
		this.edges.add(ret);
	}
	
	private void createAssociationEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"vee\" style = \"solid\" ]\n" + src + " -$ " + dest + "\n";
		this.edges.add(ret);
	}

	private ArrayList<String> getClassNames() {
		ArrayList<String> classNames = new ArrayList<>();
		for (IClass c : this.classes) {
			classNames.add(c.getName());
		}
		return classNames;
	}
}
