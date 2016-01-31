package src.problem.patternrecognition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.problem.components.*;

public class DecoratorSpotter implements IPatternSpotter {
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
				if(c != null && c.getClass().isAssignableFrom(ClassDecorator.class)) {
					ClassDecorator clazz = (ClassDecorator)c;
					if(clazz.getPattern() == PatternType.DECORATOR) {
						c = findClass(r.getSrc());
						if(c != null) {
							clazz = new ClassDecorator(c);
							clazz.setPattern(PatternType.DECORATOR);
							clazz.setStereotype("decorator");
							model.getClasses().remove(c);
							model.getClasses().add(clazz);
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
				ClassDecorator c = new ClassDecorator(clazz);
				c.setStereotype("decorator");
				c.setPattern(PatternType.DECORATOR);
				model.getClasses().remove(clazz);
				model.getClasses().add(c);
				
				//change relation to "decorates" relation
				for (IRelation relation : relations) {
					if (relation.getSrc().equals(c.getName()) && relation.getDest().equals(decoratee)) {
						if(relation.getType() == RelationType.ASSOCIATION) {
							relation.setLabel("decorates");
						}
					}
				}

				//add "component" stereotype to decoratee class
				for (IClass c2 : model.getClasses()) {
					if (c2.getName().equals(decoratee)) {
						System.out.println("decoratee: " + decoratee);
						ClassDecorator clazz2 = new ClassDecorator(c2);
						clazz2.setStereotype("component");
						clazz2.setPattern(PatternType.DECORATOR);
						model.getClasses().remove(c2);
						model.getClasses().add(clazz2);
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
