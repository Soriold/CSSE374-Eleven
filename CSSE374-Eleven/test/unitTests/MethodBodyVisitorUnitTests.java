package unitTests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import src.problem.asm.ClassFieldVisitor;
import src.problem.asm.ClassMethodVisitor;
import src.problem.asm.MethodBodyVisitor;
import src.problem.components.*;
import src.problem.components.Class;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.InstructionAdapter;

public class MethodBodyVisitorUnitTests {
	//access: 1 = public, 2 = private, 4 = protected, anything else = default

	@Test
	public void testInitInstructionCreatesUsesRelation() {
		IModel model = new Model();
		IClass clazz = new Class();
		clazz.setName("Clazz");
		IMethod method = new Method();
		MethodVisitor placeHolder = new ClassMethodVisitor(Opcodes.ASM5, clazz, model).visitMethod(1, "test", "()V", "()V", null);
		MethodVisitor mbv = new MethodBodyVisitor(Opcodes.ASM5, placeHolder, clazz, model, method);
				
		int opcode = 1;
		String owner = "TestClass";
		String name = "<init>";
		String desc = "()V";
		boolean itf = false;
		
		mbv.visitMethodInsn(opcode, owner, name, desc, itf);
		assertEquals(model.getRelations().size(), 2);
		Iterator<IRelation> it = model.getRelations().iterator();
		it.next();
		IRelation rel = it.next();
		assertEquals(rel.getSrc(), "Clazz");
		assertEquals(rel.getDest(), "TestClass");
		assertEquals(rel.getType(), "USES");
	}
}
