package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.problem.asm.ClassFieldVisitor;
import src.problem.components.*;
import src.problem.components.Class;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassFieldVisitorUnitTests {
	//access: 1 = public, 2 = private, 4 = protected, anything else = default

	@Test
	public void testBasicField() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, clazz, model);
		
		int access = 1;
		String name = "testField";
		String desc = "Ljava/lang/String;";
		String signature = null;
		Object value = null;
		
		cfv.visitField(access, name, desc, signature, value);
		assertEquals(clazz.getFields().size(), 1);
		IField addedField = clazz.getFields().get(0);
		assertEquals(addedField.getName(), "testField");
		assertEquals(addedField.getType(), "String");
		assertEquals(addedField.getVisibility(), "public");
		assertFalse(addedField.hasGenericType());	
	}
	
	@Test
	public void testFieldWithGenerics() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, clazz, model);
		
		int access = 2;
		String name = "testField";
		String desc = "Ljava/lang/List;";
		String signature = "Ljava/util/List<Lsrc/problem/components/IClass;>;";
		Object value = null;
		
		cfv.visitField(access, name, desc, signature, value);
		assertEquals(clazz.getFields().size(), 1);
		IField addedField = clazz.getFields().get(0);
		assertEquals(addedField.getName(), "testField");
		assertEquals(addedField.getType(), "IClass");
		assertEquals(addedField.getVisibility(), "private");
		assertTrue(addedField.hasGenericType());	
		assertEquals(addedField.getGenericType(), "IClass");
	}

}
