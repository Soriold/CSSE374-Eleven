package integrationTests;

public class TestLazySingleton {
	private static volatile TestLazySingleton instance;

	private TestLazySingleton() {

	}

	public static TestLazySingleton getInstance() {
		if (instance == null) {
			synchronized (TestLazySingleton.class) {
				if (instance == null) {
					instance = new TestLazySingleton();
				}
			}
		}

		return instance;
	}
}
