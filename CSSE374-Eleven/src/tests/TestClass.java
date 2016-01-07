package tests;

import java.util.List;

public class TestClass {
	private String testStringField;
	private int[] testIntField;
	public List<String> testListField;
	
	private void testPrivateMethod(int testArg) {
		
	}
	
	protected void testProtectedMethod(int testArg1, double testArg2) {
		
	}
	
	public void testPublicMethod(String testArg) {

	}
	
	public int[] testGetIntField() {
		return testIntField;
	}
	
	public String testGetStringField() {
		return testStringField;
	}
}
