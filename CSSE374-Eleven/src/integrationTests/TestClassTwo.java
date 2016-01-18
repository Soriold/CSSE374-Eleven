package integrationTests;

import java.util.ArrayList;

public class TestClassTwo {
	private ArrayList<TestClass> testClasses;
	
	public TestSuperClass testUsesEdgeCase() {
		TestSuperClass sub = new TestSubClass();
		return sub;
	}
	
	public int methodDepth1() {
		TestClassThree tc3 = new TestClassThree();
		tc3.methodDepth2();
		return 5;
	}
	
	public void methodDepth5() {
		return;
	}

}
