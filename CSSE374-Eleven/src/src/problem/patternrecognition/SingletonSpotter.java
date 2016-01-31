package src.problem.patternrecognition;

import java.util.List;

import src.problem.components.IClass;
import src.problem.components.IField;
import src.problem.components.IMethod;
import src.problem.components.IMethodCall;
import src.problem.components.IModel;
import src.problem.components.PatternType;

public class SingletonSpotter implements IPatternSpotter {

	private String name;

	private void spot(IClass c) {
		this.name = c.getName();
		boolean hasPrivateStaticInstance = this.checkInstances(c.getFields());
		boolean hasPublicStaticMethod = this.checkMethods(c.getMethods());
		boolean hasStaticGetterThatCallsConstructor = this.checkForStaticGetterThatCallsConstructor(c.getMethods());
		if (hasPrivateStaticInstance && hasPublicStaticMethod || hasStaticGetterThatCallsConstructor) {
			c.setPattern(PatternType.SINGLETON);
			c.setStereotype("Singleton");
		}
	}

	private boolean checkMethods(List<IMethod> methods) {
		for (IMethod m : methods) {
			if (m.getReturnType().equals(this.name)) {
				if (m.getVisibility().equals("public")) {
					for (String s : m.getModifiers()) {
						if (s.equals("static")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkForStaticGetterThatCallsConstructor(List<IMethod> methods) {
		for (IMethod m : methods) {
			if (m.getReturnType().equals(this.name)) {
				if (m.getVisibility().equals("public")) {
					for (String s : m.getModifiers()) {
						if (s.equals("static")) {
							for (IMethodCall mc : m.getMethodCalls()) {
								if (mc.getDestMethod().getOwner().equals(this.name)
										&& mc.getDestMethod().getName().equals("create")) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkInstances(List<IField> fields) {
		for (IField f : fields) {
			if (f.getType().equals(this.name)) {
				if (f.getVisibility().equals("private")) {
					for (String s : f.getModifiers()) {
						if (s.equals("static")) {
							return true;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void spot(IModel m) {
		for(IClass c : m.getClasses()) {
			spot(c);
		}
	}

}
