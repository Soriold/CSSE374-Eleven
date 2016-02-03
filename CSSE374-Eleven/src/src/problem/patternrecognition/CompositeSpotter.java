package src.problem.patternrecognition;

import java.util.HashSet;
import java.util.Set;

import src.problem.components.ClassDecorator;
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

			checkForDecorator(model, clazz);
		}
		checkForSubClasses(model);
	}

	private void checkForDecorator(IModel model, IClass clazz) {
		String component = "";
		Set<String> inherits = new HashSet<String>();
		Set<IRelation> relations = model.getRelations();

		// get all inherited classes (extended or implemented)
		for (IRelation relation : relations) {
			if (relation.getSrc().equals(clazz.getName())) {
				if (relation.getType() == RelationType.EXTENDS || relation.getType() == RelationType.IMPLEMENTS) {
					inherits.add(relation.getDest());
				}
			}
		}

		// detect decoration by cross-referencing inheritances and aggregates
		for (IField field : clazz.getFields()) {
			if (field.hasGenericType()) {
				if (inherits.contains(field.getGenericType())) {
					component = field.getGenericType();

					// add decorator stereotype and pattern type to class
					ClassDecorator cd = new ClassDecorator(clazz);
					cd.setStereotype("composite");
					cd.setPattern(PatternType.COMPOSITE);
					model.getClasses().remove(clazz);
					model.getClasses().add(cd);

					// add "component" stereotype to decoratee class
					for (IClass clazz2 : model.getClasses()) {
						if (clazz2.getName().equals(component)) {
							System.out.println("composite component: " + component);
							ClassDecorator cd2 = new ClassDecorator(clazz2);
							cd2.setStereotype("component");
							cd2.setPattern(PatternType.COMPOSITE);
							model.getClasses().remove(clazz2);
							model.getClasses().add(cd2);
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
				IClass dest = findClass(r.getDest());
				if (dest != null && dest.getClass().isAssignableFrom(ClassDecorator.class)) {
					ClassDecorator cd = (ClassDecorator) dest;
					if (cd.getPattern() == PatternType.COMPOSITE) {
						IClass src = findClass(r.getSrc());
						if (src != null) {
							ClassDecorator cd2 = new ClassDecorator(src);
							cd2.setPattern(PatternType.COMPOSITE);
							if (cd.getStereotype().equals("composite")) {
								cd2.setStereotype("composite");
							} else {
								cd2.setStereotype("leaf");
							}
							model.getClasses().remove(src);
							model.getClasses().add(cd2);
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
