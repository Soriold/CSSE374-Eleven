package integrationTests;

import java.util.ArrayList;

public class TestClassTwo {
	private ArrayList<TestClass> testClasses;
	
	public TestSuperClass testUsesEdgeCase() {
		TestSuperClass sub = new TestSubClass();
		return sub;
	}

}
