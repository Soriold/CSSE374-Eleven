package testClasses;

public class TestEagerSingleton {
	
	private static TestEagerSingleton instance = new TestEagerSingleton();

	private TestEagerSingleton() {
		
	}
	
	public static TestEagerSingleton getInstance() {
		return instance;
	}

}
