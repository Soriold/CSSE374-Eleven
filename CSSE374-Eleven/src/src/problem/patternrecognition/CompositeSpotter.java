package src.problem.patternrecognition;

import java.util.HashSet;
import java.util.Set;

import src.problem.components.IClass;
import src.problem.components.IField;
import src.problem.components.IModel;
import src.problem.components.IRelation;

public class CompositeSpotter extends AbstractDesignAnalyzer {

	@Override
	public void spot(IModel model) {
		this.m = model;
		for (IClass clazz : this.m.getClasses()) {

			checkForComposite(model, clazz);
		}
		checkForSubClasses(model);
	}

	private void checkForComposite(IModel model, IClass clazz) {
		@SuppressWarnings("unused")
		String component = "";
		Set<String> inherits = new HashSet<String>();
		Set<IRelation> relations = model.getRelations();

		// get all inherited classes (extended or implemented)
		for (IRelation relation : relations) {
			if (relation.getSrc().equals(clazz.getName())) {
				if (relation.getType().equals("EXTENDS")) {
					IClass c = findClass(relation.getDest());
					inherits.add(relation.getDest());
					if (c != null && !c.getIsInterface()) {
						for (String c2 : c.getInterfaces()) {
							inherits.add(c2);
						}
					}
					inherits.add(relation.getDest());
				} else if (relation.getType().equals("IMPLEMENTS")) {
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
					clazz.setPattern("COMPOSITE");

					for (IClass clazz2 : model.getClasses()) {
						if (clazz2.getName().equals(type)) {
							clazz2.setStereotype("component");
							clazz2.setPattern("COMPOSITE");
						}
					}
				}
			} else if (field.getType().contains("[]")) {
				String type = field.getType().replace("[", "");
				type = type.replace("]", "");
				if (inherits.contains(type)) {
					component = type;
					clazz.setStereotype("composite");
					clazz.setPattern("COMPOSITE");

					for (IClass clazz2 : model.getClasses()) {
						if (clazz2.getName().equals(type)) {
							clazz2.setStereotype("component");
							clazz2.setPattern("COMPOSITE");
						}
					}
				}
			}
		}
	}

	private void checkForSubClasses(IModel model) {
		for (IRelation r : model.getRelations()) {
			if (r.getType().equals("EXTENDS") || r.getType().equals("IMPLEMENTS")) {
				IClass src = findClass(r.getSrc());
				if (src != null) {
					if (src.getPattern().equals("COMPOSITE")) {
						IClass dest = findClass(r.getDest());
						if (dest != null) {
							if (src.getStereotype().equals("composite")) {
								if (dest.getStereotype() == null || !dest.getStereotype().equals("composite")) {
									dest.setStereotype("component");
									dest.setPattern("COMPOSITE");
								}
							}
						}
					}
				}

				IClass dest = findClass(r.getDest());
				if (dest != null ) {
					if (dest.getPattern().equals("COMPOSITE")) {
						if (src != null && !src.getPattern().equals("COMPOSITE")) {
							src.setPattern("COMPOSITE");
							if (dest.getStereotype().equals("composite")) {
								if (src.getStereotype() == null || !src.getStereotype().equals("composite")) {
									src.setStereotype("composite");
									checkForSubClasses(model);
									return;
								}
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
		for (IClass c : m.getClasses()) {
			if (c.getName().equals(s)) {
				return c;
			}
		}
		return null;
	}

}
