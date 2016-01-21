package src.problem.patternrecognition;

import java.util.List;

import src.problem.components.IClass;
import src.problem.components.IField;
import src.problem.components.IMethod;
import src.problem.components.PatternType;

public class SingletonSpotter implements IPatternSpotter {
	
	private String name;

	@Override
	public PatternType spot(IClass c) {
		this.name = c.getName();
//		System.out.println("pattern detecting for: " + this.name);
		PatternType ret = PatternType.NOT_FOUND;
		boolean hasPrivateStaticInstance = this.checkInstances(c.getFields());
		boolean hasPublicStaticMethod = this.checkMethods(c.getMethods());
//		System.out.println("after processing method is " + hasPublicStaticMethod);
//		System.out.println("after processing instance is " + hasPrivateStaticInstance);
		if(hasPrivateStaticInstance && hasPublicStaticMethod) {
			ret = PatternType.SINGLETON;
		}
		//System.out.println(ret.toString());
		return ret;
	}

	private boolean checkMethods(List<IMethod> methods) {
		for(IMethod m : methods) {
			if (m.getReturnType().contains(this.name)) {
//				System.out.println("method matches class type");
				if (m.getVisibility().equals("public")) {
//					System.out.println("method public");
					for(String s : m.getModifiers()) {
						//System.out.println("Class: " + this.name + " Method: " + m.getName() + " Modifier: " + s);
						if(s.equals("static")) {
//							System.out.println("method static");
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkInstances(List<IField> fields) {
		for(IField f : fields) {
			if (f.getType().contains(this.name)) {
//				System.out.println("field matches class type");
				if (f.getVisibility().equals("private")) {
//					System.out.println("field private");
//					for(String s : f.getModifiers()) {
//						if(s.equals("static")) {
////							System.out.println("field static");
//							return true;
//						}
//					}
					return true;
				}
			}
		}
		return false;
	}

}
