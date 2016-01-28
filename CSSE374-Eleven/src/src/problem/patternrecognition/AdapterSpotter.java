package src.problem.patternrecognition;

import java.util.HashSet;
import java.util.Set;

import src.problem.components.*;

public class AdapterSpotter implements IMultipleClassPatternSpotter {
	
	private IModel m;

	@Override
	public void spot(IModel m) {
		this.m = m;
		Set<IRelation> relations = m.getRelations();
		Set<IRelation> filtered = new HashSet<IRelation>();
		Set<String> impclasses = new HashSet<String>();
		Set<String> assocclasses = new HashSet<String>();
		for(IRelation r : relations) {
			if(r.getType() == RelationType.IMPLEMENTS) {
				filtered.add(r);
				impclasses.add(r.getSrc());
			}
			if(r.getType() == RelationType.ASSOCIATION) {
				filtered.add(r);
				assocclasses.add(r.getSrc());
			}
		}
		for(String s : impclasses) {
			if(assocclasses.contains(s)) {
				checkForAdapter(s, filtered);
			}
		}
	}

	private boolean checkForAdapter(String s, Set<IRelation> filtered) {
		IClass adapter = findClass(s);
		IClass adaptee = null;
		IClass target = null;
		for(IRelation r : filtered) {
			if(r.getSrc().equals(s) && r.getType() == RelationType.ASSOCIATION) {
				adaptee = findClass(r.getDest());
			}
			if(r.getSrc().equals(s) && r.getType() == RelationType.IMPLEMENTS) {
				target = findClass(r.getDest());
			}
		}
		for(IField f : adapter.getFields()) {
			if(f.getType().equals(adaptee.getName())) {
			}
		}
		
		return false;
	}

	private IClass findClass(String s) {
		for(IClass c : m.getClasses()) {
			if(c.getName() == s) {
				return c;
			}
		}
		return null;
	}
}
