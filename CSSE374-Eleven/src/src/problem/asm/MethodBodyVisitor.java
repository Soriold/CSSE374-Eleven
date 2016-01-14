package src.problem.asm;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

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
		String classname = this.clazz.getName();
		owner = simplifyClassName(owner);
		MethodCall mc = new MethodCall(classname, this.method.getName(), owner, name);
		if (name.equals("<init>")) {
			IRelation relation = new Relation(classname, owner, RelationType.USES);
			this.model.addRelation(relation);
			mc.setDestinationMethod("create");
		}
		for(Type t : Type.getArgumentTypes(desc)) {
			mc.addDestParameter(simplifyClassName(t.toString()).replace(";",""));
		}
		for(IParameter p : this.method.getParameters()){
			mc.addSourceParameter(p.getType());
		}
		this.method.addMethodCall(mc);
//		System.out.println(mc.getDestParameters().toString());
//
//		System.out.println(mc.getSourceParameters().toString());
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains("/")) {
			String[] splitType = arg.split("/");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}

}
