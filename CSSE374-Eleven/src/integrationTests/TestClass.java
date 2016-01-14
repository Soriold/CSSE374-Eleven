package integrationTests;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
	private String testStringField;
	private int[] testIntField;
	public List<String> testListField;
	protected TestClassTwo testClassTwo;
	
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
		TestClassTwo tct = new TestClassTwo();
		return testStringField;
	}
	
	public TestClassThree testUsesReturn() {
		return new TestClassThree();
	}
	
	public void testUsesArg(TestSuperClass tsc) {
		
	}
	
	public ArrayList<String> method() {
		return new ArrayList<String>();
	}
}
