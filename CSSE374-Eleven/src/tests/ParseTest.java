package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;

public class ParseTest {

	@Test
	public void testOneClass() throws IOException {
		DesignParser dp = new DesignParser();		
		dp.parse("TestClass");
	}
	
	@Test
	public void testOneInterface() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInheritance() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testImplements() {
		fail("Not yet implemented");
	}

}
