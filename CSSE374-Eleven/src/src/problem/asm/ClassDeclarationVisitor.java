package src.problem.asm;

import java.util.Arrays;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.components.*;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	private IClass clazz;
	
	public ClassDeclarationVisitor(int api, IClass clazz) {
		super(api);
		this.clazz = clazz;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		//System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		if(name.contains("/")) {
			String[] splitType = name.split("/");
			name = splitType[splitType.length - 1];
		}
		
		clazz.setName(name);
		clazz.setSuperClass(superName);
		for (String i : interfaces) {
			clazz.addInterface(i);
		}
		if ((access & Opcodes.ACC_INTERFACE) != 0) {
			clazz.setIsInterface(true);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}