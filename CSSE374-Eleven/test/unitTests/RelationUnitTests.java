package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import src.problem.components.Relation;

public class RelationUnitTests {
	
	@Test
	public void testEqualsSrc() {
		Relation r1 = new Relation("Class1", "Class2", "IMPLEMENTS");
		Relation r2 = new Relation("Class1", "Class2", "IMPLEMENTS");
		Relation r3 = new Relation("Class3", "Class2", "IMPLEMENTS");
		assertTrue(r1.equals(r2));
		assertFalse(r1.equals(r3));
	}
	
	@Test
	public void testEqualsDest() {
		Relation r1 = new Relation("Class1", "Class2", "EXTENDS");
		Relation r2 = new Relation("Class1", "Class2", "EXTENDS");
		Relation r3 = new Relation("Class1", "Class3", "EXTENDS");
		assertTrue(r1.equals(r2));
		assertFalse(r1.equals(r3));
	}
	
	@Test
	public void testEqualsType() {
		Relation r1 = new Relation("Class1", "Class2", "EXTENDS");
		Relation r2 = new Relation("Class1", "Class2", "EXTENDS");
		Relation r3 = new Relation("Class1", "Class2", "IMPLEMENTS");
		assertTrue(r1.equals(r2));
		assertFalse(r1.equals(r3));
	}

	@Test
	public void testEqualsAssociationEqUses() {
		Relation r1 = new Relation("Class1", "Class2", "ASSOCIATION");
		Relation r2 = new Relation("Class1", "Class2", "USES");
		assertTrue(r1.equals(r2));
	}
	
	@Test
	public void testEqualsChangesR1TypeToAssociation() {
		Relation r1 = new Relation("Class1", "Class2", "USES");
		Relation r2 = new Relation("Class1", "Class2", "ASSOCIATION");
		assertTrue(r1.equals(r2));
		assertEquals(r1.getType(), "ASSOCIATION");
	}

}
