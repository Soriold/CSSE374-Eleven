package src.problem.patternrecognition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.problem.components.*;

public class DecoratorSpotter implements IMultipleClassPatternSpotter {
	private IModel model;

	@Override
	public void spot(IModel model) {
		this.model = model;
		for (IClass clazz : this.model.getClasses()) {
			List<IParameter> constructorParams = getConstructorParams(clazz);
			List<IField> fields = clazz.getFields();
			List<String> aggs = getAggregates(constructorParams, fields);
			checkForDecorator(model, clazz, aggs);
		}
		checkForSubClasses(model);
	}
	
	private void checkForSubClasses(IModel model) {
		for(IRelation r : model.getRelations()) {
			if(r.getType() == RelationType.EXTENDS || r.getType() == RelationType.IMPLEMENTS) {
				IClass c = findClass(r.getDest());
				if(c != null) {
					if(c.getPattern() == PatternType.DECORATOR) {
						c = findClass(r.getSrc());
						if(c != null) {
							c.setPattern(PatternType.DECORATOR);
							c.setStereotype("decorator");
						}
					}
				}
			}
		}
	}

	private List<IParameter> getConstructorParams(IClass clazz) {
		for (IMethod method : clazz.getMethods()) {
			if (method.getName().equals("<init>")) {
				return method.getParameters();
			}
		}
		List<IParameter> none = new ArrayList<IParameter>();
		return none;
	}
	
	private List<String> getAggregates(List<IParameter> params, List<IField> fields) {
		List<String> aggs = new ArrayList<String>();
		for (IParameter param : params) {
			for (IField field : fields) {
				if (param.getType().equals(field.getType())) {
					aggs.add(param.getType());
				}
			}
		}
		return aggs;
	}
	
	private void checkForDecorator(IModel model, IClass clazz, List<String> aggs) {
		String decoratee = "";
		Set<String> inherits = new HashSet<String>();
		Set<IRelation> relations = model.getRelations();
		
		//get all inherited classes (extended or implemented)
		for (IRelation relation : relations) {
			if (relation.getSrc().equals(clazz.getName())) {
				if (relation.getType() == RelationType.EXTENDS || relation.getType() == RelationType.IMPLEMENTS) {
					inherits.add(relation.getDest());
				}
			}
		}
		
		//detect decoration by cross-referencing inheritances and aggregates
		for (String agg : aggs) {
			if (inherits.contains(agg)) {
				decoratee = agg;
				
				//add decorator stereotype and pattern type to class
				clazz.setStereotype("decorator");
				clazz.setPattern(PatternType.DECORATOR);
				
				//change relation to "decorates" relation
				for (IRelation relation : relations) {
					if (relation.getSrc().equals(clazz.getName()) && relation.getDest().equals(decoratee)) {
						if(relation.getType() == RelationType.ASSOCIATION) {
							relation.setLabel("decorates");
						}
					}
				}

				//add "component" stereotype to decoratee class
				for (IClass c : model.getClasses()) {
					if (c.getName().equals(decoratee)) {
						System.out.println("decoratee: " + decoratee);
						c.setStereotype("component");
						c.setPattern(PatternType.DECORATOR);
					}
				}
				
				return;
			}
		}
	}
	
	private IClass findClass(String s) {
		for (IClass c : model.getClasses()) {
			if (c.getName().equals(s)) {
				return c;
			}
		}
		return null;
	}
}