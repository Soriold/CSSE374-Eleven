package src.problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import src.problem.components.IClass;
import src.problem.components.IMethod;
import src.problem.components.IModel;
import src.problem.components.IRelation;
import src.problem.components.MethodCall;
import src.problem.components.Relation;

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
		//System.out.println("DESCRIPTION: "+ desc);
		String classname = this.clazz.getName();
		owner = simplifyClassName(owner);
		MethodCall mc = new MethodCall(this.method, owner, name);
		if (name.equals("<init>")) {
			IRelation relation = new Relation(classname, owner, "USES");
			this.model.addRelation(relation);
			mc.setDestinationMethod("create");
		}
		for(Type t : Type.getArgumentTypes(desc)) {
			mc.addDestParameter(simplifyClassName(t.toString()).replace(";",""));
		}
		//System.out.println(Type.getReturnType(desc).toString());
		//System.out.println(this.getReturnType(Type.getReturnType(desc).toString()));
		mc.setDestReturnType(this.getReturnType(Type.getReturnType(desc).toString()));
	
		this.method.addMethodCall(mc);
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains("/")) {
			String[] splitType = arg.split("/");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}
	
	private String getReturnType(String s) {
		switch(s) {
		case "V":
			return "void";
		case "I":
			return "int";
		case "Z":
			return "boolean";
		case "B":
			return "byte";
		case "C":
			return "char";
		case "D":
			return "double";
		case "F":
			return "float";
		case "J":
			return "long";
		case "S":
			return "short";
		default:
			String[] split = s.split("/");
			return split[split.length - 1].replace(";", "").trim();
					
		}
	}

}
