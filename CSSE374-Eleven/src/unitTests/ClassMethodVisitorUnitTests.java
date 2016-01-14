package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.problem.asm.ClassFieldVisitor;
import src.problem.asm.ClassMethodVisitor;
import src.problem.components.*;
import src.problem.components.Class;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassMethodVisitorUnitTests {
	//access: 1 = public, 2 = private, 4 = protected, anything else = default

	@Test
	public void testBasicMethod() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, clazz, model);
		
		int access = 2;
		String name = "testMethod";
		String desc = "()Ljava/util/TestClassTwo;";
		String signature = null;
		String[] exceptions = null;
		
		cmv.visitMethod(access, name, desc, signature, exceptions);
		assertEquals(clazz.getMethods().size(), 1);
		IMethod addedMethod = clazz.getMethods().get(0);
		assertEquals(addedMethod.getName(), "testMethod");
		assertEquals(addedMethod.getReturnType(), "TestClassTwo");
		assertEquals(addedMethod.getVisibility(), "private");
	}
	
	@Test
	public void testBasicMethodWithParameters() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, clazz, model);
		
		int access = 1;
		String name = "testMethod";
		String desc = "(LintegrationTests/TestClassThree;ILjava/lang/String;)Ljava/util/TestClassTwo;";
		String signature = null;
		String[] exceptions = null;
		
		cmv.visitMethod(access, name, desc, signature, exceptions);
		assertEquals(clazz.getMethods().size(), 1);
		IMethod addedMethod = clazz.getMethods().get(0);
		assertEquals(addedMethod.getName(), "testMethod");
		assertEquals(addedMethod.getReturnType(), "TestClassTwo");
		assertEquals(addedMethod.getVisibility(), "public");
		assertEquals(addedMethod.getParameters().size(), 3);
		assertEquals(addedMethod.getParameters().get(0).getType(), "TestClassThree");
		assertEquals(addedMethod.getParameters().get(1).getType(), "int");
		assertEquals(addedMethod.getParameters().get(2).getType(), "String");
	}
	
	@Test
	public void testMethodVoidReturn() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, clazz, model);
		
		int access = 4;
		String name = "testMethod";
		String desc = "(LintegrationTests/TestClassThree;ILjava/lang/String;)V";
		String signature = null;
		String[] exceptions = null;
		
		cmv.visitMethod(access, name, desc, signature, exceptions);
		assertEquals(clazz.getMethods().size(), 1);
		IMethod addedMethod = clazz.getMethods().get(0);
		assertEquals(addedMethod.getName(), "testMethod");
		assertEquals(addedMethod.getReturnType(), "void");
		assertEquals(addedMethod.getVisibility(), "protected");
		assertEquals(addedMethod.getParameters().size(), 3);
		assertEquals(addedMethod.getParameters().get(0).getType(), "TestClassThree");
		assertEquals(addedMethod.getParameters().get(1).getType(), "int");
		assertEquals(addedMethod.getParameters().get(2).getType(), "String");
	}

}
