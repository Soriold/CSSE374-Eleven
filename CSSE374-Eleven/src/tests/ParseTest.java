package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;
import src.problem.components.AllClasses;
import src.problem.components.IClass;

public class ParseTest {
	
	@Test
	public void testFields() throws IOException {
		DesignParser dp = new DesignParser();		
		IClass clazz = dp.parse("tests.TestClass");
		
		String result = clazz.getGraphViz();
		assertTrue(result.contains("- testStringField : String"));
		assertTrue(result.contains("- testIntField : int"));
		assertTrue(result.contains("+ testListField : List"));
	}
	
	@Test
	public void testMethods() throws IOException {
		DesignParser dp = new DesignParser();		
		IClass clazz = dp.parse("tests.TestClass");
		
		String result = clazz.getGraphViz();
		System.out.println(result);
		assertTrue(result.contains("- testPrivateMethod(int) : void"));
		assertTrue(result.contains("# testProtectedMethod(int, double) : void"));
		assertTrue(result.contains("+ testPublicMethod(String) : void"));
		assertTrue(result.contains("+ testGetIntField() : int"));
		assertTrue(result.contains("+ testGetStringField() : String"));
	}

	@Test
	public void testClass() throws IOException {
		DesignParser dp = new DesignParser();		
		IClass clazz = dp.parse("tests.TestClass");
		
		String result = clazz.getGraphViz();
		assertTrue(result.contains("TestClass|"));
	}
	
	@Test
	public void testImplements() throws IOException {
		DesignParser dp = new DesignParser();		
		String[] args = new String[]{"tests.TestInterface", "tests.TestInterfaceImplementation" };
		
		AllClasses allClasses = new AllClasses();
		
		for (String className : args) {	
			IClass clazz = dp.parse(className);
			allClasses.addClass(clazz);
		}
		
		String result = allClasses.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]"));
	}
	
	@Test
	public void testInheritance() throws IOException {
		DesignParser dp = new DesignParser();		
		String[] args = new String[]{"tests.TestSuperClass", "tests.TestSubClass" };
		
		AllClasses allClasses = new AllClasses();
		
		for (String className : args) {	
			IClass clazz = dp.parse(className);
			allClasses.addClass(clazz);
		}
		
		String result = allClasses.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"solid\" ]"));
	}

}
