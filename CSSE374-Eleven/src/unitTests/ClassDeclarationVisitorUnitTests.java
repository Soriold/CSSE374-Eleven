package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.problem.asm.ClassDeclarationVisitor;
import src.problem.components.*;
import src.problem.components.Class;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitorUnitTests {

	@Test
	public void testBasicParsing() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		
		int version = 1;
		int access = 1;
		String name = "src/package/folder/TestClass";
		String signature = null;
		String superName = "java/lang/Object";
		String[] interfaces = {};
		
		cdv.visit(version, access, name, signature, superName, interfaces);
		assertEquals(clazz.getName(), "TestClass");
		assertEquals(clazz.getSuperClass(), "Object");
		assertEquals(clazz.getInterfaces().size(), 0);
		assertFalse(clazz.getIsInterface());
	}
	
	@Test
	public void testParsingCustomSuperclass() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		
		int version = 1;
		int access = 1;
		String name = "src/package/folder/TestClass";
		String signature = null;
		String superName = "src/package/folder/TestSuperclass";
		String[] interfaces = {};
		
		cdv.visit(version, access, name, signature, superName, interfaces);
		assertEquals(clazz.getName(), "TestClass");
		assertEquals(clazz.getSuperClass(), "TestSuperclass");
		assertEquals(clazz.getInterfaces().size(), 0);
		assertFalse(clazz.getIsInterface());
	}
	
	@Test
	public void testParsingIsInterface() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		
		int version = 1;
		//512 is access code for interface
		int access = 512;
		String name = "src/package/folder/TestClass";
		String signature = null;
		String superName = "java/lang/Object";
		String[] interfaces = {};
		
		cdv.visit(version, access, name, signature, superName, interfaces);
		assertEquals(clazz.getName(), "TestClass");
		assertEquals(clazz.getSuperClass(), "Object");
		assertEquals(clazz.getInterfaces().size(), 0);
		assertTrue(clazz.getIsInterface());
	}
	
	@Test
	public void testImplementsAnInterface() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		
		int version = 1;
		int access = 1;
		String name = "src/package/folder/TestClass";
		String signature = null;
		String superName = "java/lang/Object";
		String[] interfaces = { "src/package/folder/ITestClass" };
		
		cdv.visit(version, access, name, signature, superName, interfaces);
		assertEquals(clazz.getName(), "TestClass");
		assertEquals(clazz.getSuperClass(), "Object");
		assertEquals(clazz.getInterfaces().size(), 1);
		assertTrue(clazz.getInterfaces().contains("ITestClass"));
		assertFalse(clazz.getIsInterface());
	}
	
	@Test
	public void testImplementsMultipleInterfaces() {
		IModel model = new Model();
		IClass clazz = new Class();
		ClassVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		
		int version = 1;
		int access = 1;
		String name = "src/package/folder/TestClass";
		String signature = null;
		String superName = "java/lang/Object";
		String[] interfaces = { "src/package/folder/ITestClass", "java/magic/stuff/IMagical" , "interfaces/are/cool/ICool" };
		
		cdv.visit(version, access, name, signature, superName, interfaces);
		assertEquals(clazz.getName(), "TestClass");
		assertEquals(clazz.getSuperClass(), "Object");
		assertEquals(clazz.getInterfaces().size(), 3);
		assertTrue(clazz.getInterfaces().contains("ITestClass"));
		assertTrue(clazz.getInterfaces().contains("IMagical"));
		assertTrue(clazz.getInterfaces().contains("ICool"));
		assertFalse(clazz.getIsInterface());
	}

}
