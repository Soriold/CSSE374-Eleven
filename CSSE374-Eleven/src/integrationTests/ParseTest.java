package integrationTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;
import src.problem.components.Model;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.components.IClass;
import src.problem.components.IModel;

public class ParseTest {

	@Test
	public void testFields() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("integrationTests.TestClass", model);
		model.addClass(clazz);

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("- testStringField : String"));
		assertTrue(result.contains("- testIntField : int"));
		assertTrue(result.contains("+ testListField : List"));
		assertTrue(result.contains("# testClassTwo : TestClassTwo"));
	}

	@Test
	public void testMethods() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("integrationTests.TestClass", model);
		model.addClass(clazz);

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("- testPrivateMethod(int ) : void"));
		assertTrue(result.contains("# testProtectedMethod(int double ) : void"));
		assertTrue(result.contains("+ testPublicMethod(String ) : void"));
		assertTrue(result.contains("+ testGetIntField() : int"));
		assertTrue(result.contains("+ testGetStringField() : String"));
	}

	@Test
	public void testClass() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("integrationTests.TestClass", model);
		model.addClass(clazz);

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("TestClass|"));
	}

	@Test
	public void testImplements() throws IOException {
		String[] args = new String[] { "integrationTests.TestInterface",
				"integrationTests.TestInterfaceImplementation" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nTestInterfaceImplementation -> TestInterface"));
	}

	@Test
	public void testInheritance() throws IOException {
		String[] args = new String[] { "integrationTests.TestSuperClass", "integrationTests.TestSubClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(
				result.contains("edge [ arrowhead = \"onormal\" style = \"solid\" ]\nTestSubClass -> TestSuperClass"));
	}

	@Test
	public void testInterfaceExtendsInterface() throws IOException {
		String[] args = new String[] { "integrationTests.TestInterface", "integrationTests.TestInterfaceTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result
				.contains("edge [ arrowhead = \"onormal\" style = \"solid\" ]\nTestInterfaceTwo -> TestInterface"));

	}

	@Test
	public void testAssociation() throws IOException {
		String[] args = new String[] { "integrationTests.TestClass", "integrationTests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\" ]\nTestClass -> TestClassTwo"));
	}

	@Test
	public void testAssociationInArrayList() throws IOException {
		String[] args = new String[] { "integrationTests.TestClass", "integrationTests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\" ]\nTestClassTwo -> TestClass"));
	}

	@Test
	public void testUsesReturn() throws IOException {
		String[] args = new String[] { "integrationTests.TestClass", "integrationTests.TestClassThree" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClass -> TestClassThree"));
	}

	@Test
	public void testUsesArg() throws IOException {
		String[] args = new String[] { "integrationTests.TestClass", "integrationTests.TestSuperClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClass -> TestSuperClass"));
	}

	@Test
	public void testUsesEdgeCaseInstanceCreation() throws IOException {
		String[] args = new String[] { "integrationTests.TestClassTwo", "integrationTests.TestSuperClass",
				"integrationTests.TestSubClass" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClassTwo -> TestSuperClass"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClassTwo -> TestSubClass"));
	}
	
	@Test
	public void testAssociationTakesPriorityOverUses() throws IOException {
		String[] args = new String[] { "integrationTests.TestClass", "integrationTests.TestClassTwo" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\" ]\nTestClass -> TestClassTwo"));
		assertFalse(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nTestClass -> TestClassTwo"));
	}

	// Testing tricky factory cases
	@Test
	public void testFactoryBasicClass() throws IOException {
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains(
				"NYPizzaIngredientFactory [label = \"{NYPizzaIngredientFactory||+ \\<init\\>() : void\\l\n+ createDough() : Dough\\l\n+ createSauce() : Sauce\\l\n+ createCheese() : Cheese\\l\n+ createVeggies() : Veggies[]\\l\n+ createPepperoni() : Pepperoni\\l\n+ createClam() : Clams\\l\n+ createVeggies() : Veggies[]\\l\n+ createPepperoni() : Pepperoni\\l\n}\"]"));
	}

	@Test
	public void testFactoryBasicExtension() throws IOException {
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nMozzarellaCheese -> Cheese"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\nPlumTomatoSauce -> Sauce"));
	}

	@Test
	public void testFactoryBasicUsesReturn() throws IOException {
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(
				result.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> Cheese"));
		assertTrue(result
				.contains("edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> Sauce"));
	}

	@Test
	public void testFactoryBasicUsesInstantiation() throws IOException {
		String[] args = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams",
				"lab2_3.Dough", "lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce",
				"lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory", "lab2_3.NYPizzaStore",
				"lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> ReggianoCheese"));
		assertFalse(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\" ]\nNYPizzaIngredientFactory -> MozzarellaCheese"));
		assertTrue(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> PlumTomatoSauce"));
		assertFalse(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\" ]\nChicagoPizzaIngredientFactory -> MarinaraSauce"));
	}
}
