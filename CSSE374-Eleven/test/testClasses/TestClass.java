package testClasses;

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
	
	public void methodDepth0() {
		TestClassTwo tc2 = new TestClassTwo();
		int tempInt = tc2.methodDepth1();
	}
	
	public String methodDepth4(String s) {
		TestClassTwo tc2 = new TestClassTwo();
		tc2.methodDepth5();
		return "yay!";
	}
}
