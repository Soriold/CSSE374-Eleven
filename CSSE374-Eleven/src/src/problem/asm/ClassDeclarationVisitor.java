package src.problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.components.*;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private IClass clazz;
	private IModel model;
	
	public ClassDeclarationVisitor(int api, IClass clazz, IModel model) {
		super(api);
		this.clazz = clazz;
		this.model = model;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		//System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		name = simplifyClassName(name);
		superName = simplifyClassName(superName);
		IRelation relation  = null;
		
		clazz.setName(name);
		clazz.setSuperClass(superName);
		if(superName != null) {
			relation = new Relation(this.clazz.getName(), superName, "EXTENDS");
		}
		
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			clazz.setIsInterface(true);
		}
		
		this.model.addRelation(relation);
		for (String i : interfaces) {
			i = simplifyClassName(i);
			clazz.addInterface(i);
			if (clazz.getIsInterface()) {
				relation = new Relation(this.clazz.getName(), i, "EXTENDS");
			} else {
				relation = new Relation(this.clazz.getName(), i, "IMPLEMENTS");
			}
			this.model.addRelation(relation);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains("/")) {
			String[] splitType = arg.split("/");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}
}