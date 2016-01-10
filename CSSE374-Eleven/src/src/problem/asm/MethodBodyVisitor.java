package src.problem.asm;

import org.objectweb.asm.MethodVisitor;

import src.problem.components.*;

public class MethodBodyVisitor extends MethodVisitor {
	
	private IClass clazz;
	private IModel model;

	public MethodBodyVisitor(int api, MethodVisitor methodVisitor, IClass clazz, IModel model) {
		super(api, methodVisitor);
		this.clazz = clazz;
		this.model = model;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if (name.equals("<init>")) {
			String usedClassToAdd = simplifyClassName(owner);
			IRelation relation = new Relation(this.clazz.getName(), usedClassToAdd, RelationType.USES);
			this.model.addRelation(relation);
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
