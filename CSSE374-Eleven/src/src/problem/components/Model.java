package src.problem.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Model implements IModel {
	private List<IClass> classes;
	private Set<IRelation> relations;

	public Model() {
		this.classes = new ArrayList<IClass>();
		this.relations = new HashSet<IRelation>();
	}
	
	@Override
	public void addClass(IClass clazz) {
		this.classes.add(clazz);
	}
	
	@Override
	public List<IClass> getClasses() {
		return this.classes;
	}

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
		ArrayList<String> classNames = this.getClassNames();
		StringBuilder ret = new StringBuilder();
		for (IClass c : this.classes) {
			for(IRelation r : c.getRelations()) {
				if(classNames.contains(r.getSrc()) && classNames.contains(r.getDest())) {
					switch(r.getType()) {
						case EXTENDS:
							ret.append("edge [ arrowhead = \"onormal\" style = \"solid\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
							break;
						case IMPLEMENTS:
							ret.append("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
							break;
						case ASSOCIATION:
							ret.append("edge [ arrowhead = \"vee\" style = \"solid\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
							break;
						case USES:
							ret.append("edge [ arrowhead = \"vee\" style = \"dashed\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
							break;
					}
				}
			}
		}
		return ret.toString();
	}

	private ArrayList<String> getClassNames() {
		ArrayList<String> classNames = new ArrayList<>();
		for (IClass c : this.classes) {
			classNames.add(c.getName());
		}
		return classNames;
	}
	
	public Set<IRelation> getRelations() {
		return this.relations;
	}

	public void addRelation(IRelation relation) {
		this.relations.add(relation);
	}
}
