package src.problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import src.problem.components.*;

public class ClassFieldVisitor extends ClassVisitor {
	
	private IClass clazz;
	private IModel model;
	
	public ClassFieldVisitor(int api, IClass clazz, IModel model) {
		super(api);
		this.clazz = clazz;
		this.model = model;
	}

	public ClassFieldVisitor(int api, ClassVisitor decorated, IClass clazz, IModel model) {
		super(api, decorated);
		this.clazz = clazz;
		this.model = model;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		type = simplifyClassName(type);
		System.out.println(signature);
		signature = extractType(signature);
		System.out.println(Type.getType(signature));
		
		IRelation relation;
		if(!signature.equals("")) {
			String temp = signature.substring(1, signature.length() - 1);
			//System.out.println("==>>" + type + "  " + temp + "  "+ signature);
			relation = new Relation(this.clazz.getName(), type, RelationType.ASSOCIATION);
		} else {
			relation = new Relation(this.clazz.getName(), type, RelationType.ASSOCIATION);
		}
		
		type += signature;
		
		IField field = new Field();
		if(!signature.equals("")) {
			field.setHasGenericType(true);
			field.setGenericType(signature);
		}
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
		
		if((access & Opcodes.ACC_STATIC) != 0) {
			field.addModifier("static");
		}
		
		this.clazz.addField(field);
		this.model.addRelation(relation);
		
		return toDecorate;
	};

	private String simplifyClassName(String arg) {
		if(arg.contains(".")) {
			String[] splitType = arg.split("\\.");
			arg = splitType[splitType.length - 1];
		}
		return arg;
	}
	private String extractType(String in) {
		if(in == null) {
			return "";
		}
		int start = in.indexOf("<");
		int end = in.indexOf(">") + 1;
		in = in.substring(start, end);
		String[] parts = in.split("/");
		in = parts[parts.length-1];
		in = in.replace(";", "");
		return "<" + in;
	}
}
