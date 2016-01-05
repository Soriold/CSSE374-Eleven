package src.problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import src.problem.components.IClass;
import src.problem.components.IMethod;
import src.problem.components.IParameter;
import src.problem.components.Method;
import src.problem.components.Parameter;

public class ClassMethodVisitor extends ClassVisitor {
	
	private IClass clazz;
	
	public ClassMethodVisitor(int api, IClass clazz) {
		super(api);
		this.clazz = clazz;
	}

	public ClassMethodVisitor(int api, ClassVisitor decorated, IClass clazz) {
		super(api, decorated);
		this.clazz = clazz;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		//System.out.println("method " + name);
		// DONE: create an internal representation of the current method and
		// pass it to the methods below
		IMethod method = new Method();
		method.setName(name);
		
		addAccessLevel(access, method);
		addReturnType(desc, method);
		addArguments(desc, method);
		// DONE: add the current method to your internal representation of the
		// current class
		this.clazz.addMethod(method);
		return toDecorate;
	}

	void addAccessLevel(int access, IMethod method) {
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

	void addReturnType(String desc, IMethod method) {
		String returnType = Type.getReturnType(desc).getClassName();
		//System.out.println("return type: " + returnType);
		// DONE: ADD this information to your representation of the current
		// method.
		if(returnType.contains(".")) {
			String[] splitType = returnType.split("\\.");
			returnType = splitType[splitType.length - 1];
		}
		method.setReturnType(returnType);
	}

	void addArguments(String desc, IMethod method) {
		Type[] args = Type.getArgumentTypes(desc);
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].getClassName();
			//System.out.println("arg " + i + ": " + arg);
			// DONE: ADD this information to your representation of the current
			// method.
			if(arg.contains(".")) {
				String[] splitType = arg.split("\\.");
				arg = splitType[splitType.length - 1];
			}
			IParameter parameter = new Parameter();
			parameter.setType(arg);
			method.addParameter(parameter);
		}
	}
}