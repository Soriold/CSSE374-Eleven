package src.problem.asm;

import org.objectweb.asm.MethodVisitor;

import src.problem.components.IClass;

public class MethodBodyVisitor extends MethodVisitor {
	
	private IClass clazz;

	public MethodBodyVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MethodBodyVisitor(int arg0, MethodVisitor arg1, IClass clazz) {
		super(arg0, arg1);
		this.clazz = clazz;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		System.out.println(opcode);
		System.out.println(owner);
		System.out.println(name);
		System.out.println(desc);
		System.out.println(itf);
		System.out.println();
		
		
	}

}
