package src.problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import src.problem.components.*;

public class ClassFieldVisitor extends ClassVisitor {
	
	private IClass clazz;
	
	public ClassFieldVisitor(int api, IClass clazz) {
		super(api);
		this.clazz = clazz;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass clazz) {
		super(api, decorated);
		this.clazz = clazz;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		type = simplifyClassName(type);
		
		IField field = new Field();
		field.setName(name);
		field.setType(type);
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			field.setVisibility("public");
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			field.setVisibility("protected");
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			field.setVisibility("private");
		} else {
			field.setVisibility("default");
		}
		
		this.clazz.addField(field);
		
		return toDecorate;
	};
	
	String simplifyClassName(String arg) {
		if(arg.contains(".")) {
			String[] splitType = arg.split("\\.");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}
}
