package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class AllClasses implements IGraphVizComponent {
	private List<IClass> classes;

	public AllClasses() {
		this.classes = new ArrayList<>();
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
		ArrayList<String> classNames = this.getClassNames();
		for (IClass c : this.classes) {
			for (String s : c.getInterfaces()) {
				if (classNames.contains(s)) {
					ret.append(this.createImplementsEdge(c.getName(), s));
					ret.append("\n");
				}
			}
			if (classNames.contains(c.getSuperClass())) {
				ret.append(this.createExtendsEdge(c.getName(), c.getSuperClass()));
				ret.append("\n");
			}
		}
		return ret.toString();
	}

	private String createImplementsEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"onormal\" style = \"dashed\" ]\n" + src + " -$ " + dest;
		return ret;
	}

	private String createExtendsEdge(String src, String dest) {
		String ret = "edge [ arrowhead = \"onormal\"]\n" + src + " -$ " + dest;
		return ret;
	}

	private ArrayList<String> getClassNames() {
		ArrayList<String> classNames = new ArrayList<>();
		for (IClass c : this.classes) {
			classNames.add(c.getName());
		}
		return classNames;
	}
}
