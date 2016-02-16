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

	public void validateRelations() {
		for (IRelation r : this.relations) {
			if(!hasClassWithName(r.getSrc()) || !hasClassWithName(r.getDest())) {
				this.relations.remove(r);
			}
		}
	}

	public boolean hasClassWithName(String name) {
		for (IClass clazz : this.classes) {
			if (clazz.getName().equals(name))
				return true;
		}
		return false;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for (IClass c : this.classes) {
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		ArrayList<String> classNames = getClassNames();
		for (IRelation r : this.relations) {
			if (classNames.contains(r.getSrc()) && classNames.contains(r.getDest())) {
				ITraverser t = (ITraverser) r;
				t.accept(v);
			}
		}
		v.postVisit(this);
	}
}
