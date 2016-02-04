package src.problem.patternrecognition;

import java.util.HashSet;
import java.util.Set;

import src.problem.components.IClass;
import src.problem.components.IField;
import src.problem.components.IModel;
import src.problem.components.IRelation;
import src.problem.components.PatternType;
import src.problem.components.RelationType;

public class CompositeSpotter implements IPatternSpotter {
	private IModel model;

	@Override
	public void spot(IModel model) {
		this.model = model;
		for (IClass clazz : this.model.getClasses()) {

			checkForComposite(model, clazz);
		}
		checkForSubClasses(model);
	}

	private void checkForComposite(IModel model, IClass clazz) {
		String component = "";
		Set<String> inherits = new HashSet<String>();
		Set<IRelation> relations = model.getRelations();

		// get all inherited classes (extended or implemented)
		for (IRelation relation : relations) {
			if (relation.getSrc().equals(clazz.getName())) {
				if (relation.getType() == RelationType.EXTENDS) {
					IClass c = findClass(relation.getDest());
					inherits.add(relation.getDest());
					if(c != null && !c.getIsInterface()) {
						for(String c2 : c.getInterfaces()) {
							inherits.add(c2);
						}
					}
					inherits.add(relation.getDest());
				} else if(relation.getType() == RelationType.IMPLEMENTS) {
					inherits.add(relation.getDest());
				}
			}
		}

		// detect decoration by cross-referencing inheritances and aggregates
		for (IField field : clazz.getFields()) {

			if (field.hasGenericType()) {
				String type = field.getGenericType().replace("<", "");
				type = type.replace(">", "");
				if (inherits.contains(type)) {
					component = type;
					clazz.setStereotype("composite");
					clazz.setPattern(PatternType.COMPOSITE);

					for (IClass clazz2 : model.getClasses()) {
						if (clazz2.getName().equals(type)) {
							clazz2.setStereotype("component");
							clazz2.setPattern(PatternType.COMPOSITE);
						}
					}

					return;
				}
			}
		}
	}

	private void checkForSubClasses(IModel model) {
		for (IRelation r : model.getRelations()) {
			if (r.getType() == RelationType.EXTENDS || r.getType() == RelationType.IMPLEMENTS) {
				IClass src = findClass(r.getSrc());
				if(src != null) {
					if(src.getPattern() == PatternType.COMPOSITE) {
						IClass dest = findClass(r.getDest());
						if(dest != null) {
							if(src.getStereotype().equals("composite")) {
								dest.setStereotype("component");
							}
						}
					}
				}
				
				
				IClass dest = findClass(r.getDest());
				if (dest != null ) {
					if (dest.getPattern() == PatternType.COMPOSITE) {
						if (src != null && src.getPattern() != PatternType.COMPOSITE) {
							src.setPattern(PatternType.COMPOSITE);
							if (dest.getStereotype().equals("composite")) {
								src.setStereotype("composite");
							} else {
								src.setStereotype("leaf");
							}
						}
					}
				}
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
