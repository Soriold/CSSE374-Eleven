package integrationTests;

public class TestClassThree {

	public TestClassThree() {
		
	}
	
	public void methodDepth2() {
		methodDepth3();
	}
	
	public void methodDepth3() {
		TestClass tc = new TestClass();
		String tempString = tc.methodDepth4("potato");
		
	}

}
