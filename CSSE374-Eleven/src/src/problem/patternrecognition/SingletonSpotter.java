package src.problem.patternrecognition;

import java.util.List;

import src.problem.components.IClass;
import src.problem.components.IField;
import src.problem.components.IMethod;
import src.problem.components.IMethodCall;
import src.problem.components.IModel;

public class SingletonSpotter extends AbstractDesignAnalyzer {

	private String name;
	private boolean requiresGetInstanceMethod = false;

	private void spot(IClass c) {
		parseParameters();
		this.name = c.getName();
		boolean hasPrivateStaticInstance = this.checkInstances(c.getFields());
		boolean hasPublicStaticMethod = this.checkMethods(c.getMethods());
		boolean hasStaticGetterThatCallsConstructor = this.checkForStaticGetterThatCallsConstructor(c.getMethods());
		boolean hasGetInstance = this.checkForGetInstance(c.getMethods());
		if (hasPrivateStaticInstance && hasPublicStaticMethod || hasStaticGetterThatCallsConstructor) {
			if (this.requiresGetInstanceMethod) {
				if (hasGetInstance) {
					c.setPattern("SINGLETON");
					c.setStereotype("Singleton");
				}
			} else {
				c.setPattern("SINGLETON");
				c.setStereotype("Singleton");
			}
		}
	}

	private void parseParameters() {
		if (this.params.get("Singleton-RequireGetInstance") != null) {
			this.requiresGetInstanceMethod = Boolean.parseBoolean(this.params.get("Singleton-RequireGetInstance"));
		}
	}

	private boolean checkForGetInstance(List<IMethod> methods) {
		for (IMethod m : methods) {
			if (m.getName().equals("getInstance")) {
				if (m.getReturnType().equals(this.name)) {
					if (m.getVisibility().equals("public")) {
						if (m.getModifiers().contains("static")) {
							return true;
						}
					}
				}
			}
		}
		return false;
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
		for (IClass c : m.getClasses()) {
			spot(c);
		}
	}

}
