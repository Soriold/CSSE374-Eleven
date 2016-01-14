package src.problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import src.problem.components.IClass;
import src.problem.components.IMethod;
import src.problem.components.IModel;
import src.problem.components.IRelation;
import src.problem.components.MethodCall;
import src.problem.components.Relation;
import src.problem.components.RelationType;

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
		String classname = this.clazz.getName();
		owner = simplifyClassName(owner);
		MethodCall mc = new MethodCall(this.method, owner, name);
		if (name.equals("<init>")) {
			IRelation relation = new Relation(classname, owner, RelationType.USES);
			this.model.addRelation(relation);
			mc.setDestinationMethod("create");
		}
		for(Type t : Type.getArgumentTypes(desc)) {
			mc.addDestParameter(simplifyClassName(t.toString()).replace(";",""));
		}
	
		this.method.addMethodCall(mc);
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains("/")) {
			String[] splitType = arg.split("/");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}

}
