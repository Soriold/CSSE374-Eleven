package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import src.problem.components.Relation;
import src.problem.components.Relation.RelationType;

public class RelationUnitTests {

	@Test
	public void testEquals() {
		Relation r1 = new Relation("Class1", "Class2", RelationType.ASSOCIATION);
		Relation r2 = new Relation("Class1", "Class2", RelationType.USES);
		assertTrue(r1.equals(r2));
	}
	
	@Test
	public void testEquals2() {
		Relation r1 = new Relation("Class1", "Class2", RelationType.USES);
		Relation r2 = new Relation("Class1", "Class2", RelationType.ASSOCIATION);
		assertTrue(r1.equals(r2));
		assertEquals(r1.getType(), RelationType.ASSOCIATION);
	}

}
