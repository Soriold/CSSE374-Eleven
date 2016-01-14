package src.problem.asm;

import org.objectweb.asm.MethodVisitor;

import src.problem.components.*;

public class MethodBodyVisitor extends MethodVisitor {
	
	private IClass clazz;
	private IModel model;
	private IMethod method;

	public MethodBodyVisitor(int api, MethodVisitor methodVisitor, IClass clazz, IModel model, IMethod method) {
		super(api, methodVisitor);
		this.clazz = clazz;
		this.model = model;
		this.method = method;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		owner = simplifyClassName(owner);
		if (name.equals("<init>")) {
			IRelation relation = new Relation(this.clazz.getName(), owner, RelationType.USES);
			this.model.addRelation(relation);
		} else {
			this.method.addMethodCall(name, owner);
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
