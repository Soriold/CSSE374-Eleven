package src.problem.patternrecognition;

import java.util.HashSet;
import java.util.Set;

import src.problem.components.*;

public class AdapterSpotter extends AbstractDesignAnalyzer {

	@Override
	public void spot(IModel m) {
		this.m = m;
		Set<IRelation> relations = m.getRelations();
		Set<IRelation> filtered = new HashSet<IRelation>();
		Set<String> impclasses = new HashSet<String>();
		Set<String> assocclasses = new HashSet<String>();
		for (IRelation r : relations) {
			if (r.getType().equals("IMPLEMENTS")) {
				filtered.add(r);
				impclasses.add(r.getSrc());
			}
			if (r.getType().equals("ASSOCIATION")) {
				filtered.add(r);
				assocclasses.add(r.getSrc());
			}
			if (r.getType().equals("USES")) {
				filtered.add(r);
				impclasses.add(r.getSrc());
			}
		}
		for (String s : impclasses) {
			if (assocclasses.contains(s)) {
				checkForAdapter(s, filtered);
			}
		}
	}

	private boolean checkForAdapter(String s, Set<IRelation> filtered) {
		IClass adapter = findClass(s);
		IClass adaptee = null;
		IClass target = null;
		for (IRelation r : filtered) {
			if (r.getSrc().equals(s) && r.getType().equals("ASSOCIATION")) {
				adaptee = findClass(r.getDest());
			}
			if (r.getSrc().equals(s) && r.getType().equals("IMPLEMENTS")) {
				target = findClass(r.getDest());

			}
		}
		if (adaptee == null || target == null)
			return false;
		for (IField f : adapter.getFields()) {
			if (f.getType().equals(adaptee.getName())) {
				adapter.setStereotype("adapter");
				adaptee.setStereotype("adaptee");
				target.setStereotype("target");
				adapter.setPattern("ADAPTER");
				adaptee.setPattern("ADAPTER");
				target.setPattern("ADAPTER");
				for (IRelation r : m.getRelations()) {
					if (r.getSrc().equals(s) && r.getDest().equals(adaptee.getName())
							&& r.getType().equals("ASSOCIATION")) {
						r.setLabel("adapts");
					}
				}
				return true;
			}
		}

		return false;
	}

	private IClass findClass(String s) {
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(s)) {
				return c;
			}
		}
		return null;
	}
}
