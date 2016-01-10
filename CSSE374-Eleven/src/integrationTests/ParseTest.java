package integrationTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;
import src.problem.components.Model;
import src.problem.components.IClass;
import src.problem.components.IModel;

public class ParseTest {

	@Test
	public void testFields() throws IOException {
		DesignParser dp = new DesignParser();
		IModel model = new Model();
		IClass clazz = dp.parse("tests.TestClass", model);

		String result = clazz.getGraphViz();
		assertTrue(result.contains("- testStringField : String"));
		assertTrue(result.contains("- testIntField : int"));
		assertTrue(result.contains("+ testListField : List"));
		assertTrue(result.contains("# testClassTwo : TestClassTwo"));
	}

	@Test
	public void testMethods() throws IOException {
		DesignParser dp = new DesignParser();
		IModel model = new Model();
		IClass clazz = dp.parse("tests.TestClass", model);

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
		IModel model = new Model();
		IClass clazz = dp.parse("tests.TestClass", model);

		String result = clazz.getGraphViz();
		assertTrue(result.contains("TestClass|"));
	}

	@Test
	public void testImplements() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestInterface", "tests.TestInterfaceImplementation" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nTestInterfaceImplementation -> TestInterface"));
	}

	@Test
	public void testInheritance() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestSuperClass", "tests.TestSubClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(
				result.contains("edge [ arrowhead = \"onormal\" style = \"solid\" ]\nTestSubClass -> TestSuperClass"));
	}

	@Test
	public void testInterfaceExtendsInterface() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestInterface", "tests.TestInterfaceTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result
				.contains("edge [ arrowhead = \"onormal\" style = \"solid\" ]\nTestInterfaceTwo -> TestInterface"));

	}

	@Test
	public void testAssociation() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestClass", "tests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\" ]\nTestClass -> TestClassTwo"));
	}

	@Test
	public void testAssociationInArrayList() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestClass", "tests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\" ]\nTestClassTwo -> TestClass"));
	}

	@Test
	public void testUsesReturn() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestClass", "tests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClass -> TestClassTwo"));
	}

	@Test
	public void testUsesArg() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestClass", "tests.TestSuperClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClass -> TestSuperClass"));
	}

	@Test
	public void testUsesEdgeCase() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "tests.TestClassTwo", "tests.TestSuperClass", "tests.TestSubClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClassTwo -> TestSuperClass"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClassTwo -> TestSubClass"));
	}

	// Testing tricky factory cases
	@Test
	public void testFactoryBasicClass() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains(
				"NYPizzaIngredientFactory [label = \"{NYPizzaIngredientFactory||+ \\<init\\>() : void\\l+ createDough() : Dough\\l+ createSauce() : Sauce\\l+ createCheese() : Cheese\\l+ createVeggies() : Veggies[]\\l+ createPepperoni() : Pepperoni\\l+ createClam() : Clams\\l+ createVeggies() : Veggies[]\\l+ createPepperoni() : Pepperoni\\l}\"]"));
	}
	
	@Test
	public void testFactoryBasicExtension() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nMozzarellaCheese -> Cheese"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nPlumTomatoSauce -> Sauce"));
	}
	
	@Test
	public void testFactoryBasicUsesReturn() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> Cheese"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> Sauce"));
	}
	
	@Test
	public void testFactoryBasicUsesInstantiation() throws IOException {
		DesignParser dp = new DesignParser();
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = dp.parse(className, model);
			model.addClass(clazz);
		}

		String result = model.getGraphViz();
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> ReggianoCheese"));
		assertFalse(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> MozzarellaCheese"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> PlumTomatoSauce"));
		assertFalse(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> MarinaraSauce"));
	}
}
