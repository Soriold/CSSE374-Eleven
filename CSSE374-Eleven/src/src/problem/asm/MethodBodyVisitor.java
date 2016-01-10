package src.problem.asm;

import org.objectweb.asm.MethodVisitor;

import src.problem.components.IClass;

public class MethodBodyVisitor extends MethodVisitor {
	
	private IClass clazz;

	public MethodBodyVisitor(int api, MethodVisitor methodVisitor, IClass clazz) {
		super(api, methodVisitor);
		this.clazz = clazz;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if (name.equals("<init>")) {
			String usedClassToAdd = simplifyClassName(owner);
			clazz.addUsedClass(usedClassToAdd);
		}
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains("/")) {
			String[] splitType = arg.split("/");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}

}
