package src.problem.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.problem.outputvisitor.ITraverser;
import src.problem.outputvisitor.IVisitor;


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

//	private String getEdges() {
//		ArrayList<String> classNames = this.getClassNames();
//		StringBuilder ret = new StringBuilder();
//		for (IClass c : this.classes) {
//			for(IRelation r : this.relations) {
//				if(classNames.contains(r.getSrc()) && classNames.contains(r.getDest())) {
//					switch(r.getType()) {
//						case EXTENDS:
//							ret.append("edge [ arrowhead = \"onormal\" style = \"solid\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
//							break;
//						case IMPLEMENTS:
//							ret.append("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
//							break;
//						case ASSOCIATION:
//							ret.append("edge [ arrowhead = \"vee\" style = \"solid\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
//							break;
//						case USES:
//							ret.append("edge [ arrowhead = \"vee\" style = \"dashed\" ]\n" + r.getSrc() + " -$ " + r.getDest() + "\n");
//							break;
//					}
//				}
//			}
//		}
//		return ret.toString();
//	}

//	private ArrayList<String> getClassNames() {
//		ArrayList<String> classNames = new ArrayList<>();
//		for (IClass c : this.classes) {
//			classNames.add(c.getName());
//		}
//		return classNames;
//	}
	
	public Set<IRelation> getRelations() {
		return this.relations;
	}

	public void addRelation(IRelation relation) {
		this.relations.add(relation);
	}

	@Override
	public void accept(IVisitor v) {
		System.out.println("visiting model");
		v.preVisit(this);
		for (IClass c : this.classes) {
			System.out.println("visiting class" + c.getName());
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.postVisit(this);
	}
}
