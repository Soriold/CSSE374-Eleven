package src.problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import src.problem.components.*;

public class ClassMethodVisitor extends ClassVisitor {
	
	private IClass clazz;
	private IModel model;
	
	public ClassMethodVisitor(int api, IClass clazz, IModel model) {
		super(api);
		this.clazz = clazz;
		this.model = model;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass clazz, IModel model) {
		super(api, decorated);
		this.clazz = clazz;
		this.model = model;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//		System.out.println("access " + access);
//		System.out.println("name " + name);
//		System.out.println("desc " + desc);
//		System.out.println("sig " + signature);
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		//System.out.println("method " + name);
		// DONE: create an internal representation of the current method and
		// pass it to the methods below
		IMethod method = new Method();
		method.setName(name);
		method.setOwner(this.clazz.getName());
		
		addAccessLevel(access, method);
		addReturnType(desc, method);
		addArguments(desc, method);
		
		MethodBodyVisitor mbv = new MethodBodyVisitor(this.api, toDecorate, this.clazz, this.model, method);
		// DONE: add the current method to your internal representation of the
		// current class
		this.clazz.addMethod(method);
		
		return mbv;
	}

	private void addAccessLevel(int access, IMethod method) {
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "public";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "protected";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "private";
		} else {
			level = "default";
		}
		
		//System.out.println("access level: " + level);
		//DONE: ADD this information to your representation of the current
		// method.
		method.addModifier(level);
		method.setVisibility(level);
	}

	private void addReturnType(String desc, IMethod method) {
		String returnType = Type.getReturnType(desc).getClassName();
		//System.out.println("return type: " + returnType);
		// DONE: ADD this information to your representation of the current
		// method.
		returnType = simplifyClassName(returnType);
		//System.out.println("return type: "+ returnType);
		method.setReturnType(returnType);
		IRelation relation = new Relation(this.clazz.getName(), returnType, RelationType.USES);
		this.model.addRelation(relation);
	}

	private void addArguments(String desc, IMethod method) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			//System.out.println("arg " + i + ": " + arg);
			// DONE: ADD this information to your representation of the current
			// method.
			arg = simplifyClassName(arg);
			//System.out.println("arg type: "+ arg);
			IParameter parameter = new Parameter();
			parameter.setType(arg);
			IRelation relation = new Relation(this.clazz.getName(), arg, RelationType.USES);
			this.model.addRelation(relation);
			method.addParameter(parameter);
		}
	}
	
	private String simplifyClassName(String arg) {
		if(arg.contains(".")) {
			String[] splitType = arg.split("\\.");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}
}