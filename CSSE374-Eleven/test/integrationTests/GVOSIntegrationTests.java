package integrationTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import src.problem.asm.DesignParser;
import src.problem.components.Model;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.patternrecognition.PatternRecognizer;
import src.problem.components.IClass;
import src.problem.components.IModel;

public class GVOSIntegrationTests {

	@Test
	public void testFields() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("testClasses.TestClass", model);
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
		IClass clazz = DesignParser.parse("testClasses.TestClass", model);
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
		IClass clazz = DesignParser.parse("testClasses.TestClass", model);
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
		String[] args = new String[] { "testClasses.TestInterface",
				"testClasses.TestInterfaceImplementation" };

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
		
		//System.out.println(result);

		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\"  label=\"\"]\nTestInterfaceImplementation -> TestInterface"));
	}

	@Test
	public void testInheritance() throws IOException {
		String[] args = new String[] { "testClasses.TestSuperClass", "testClasses.TestSubClass" };

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
				result.contains("edge [ arrowhead = \"onormal\" style = \"solid\"  label=\"\"]\nTestSubClass -> TestSuperClass"));
	}

	@Test
	public void testInterfaceExtendsInterface() throws IOException {
		String[] args = new String[] { "testClasses.TestInterface", "testClasses.TestInterfaceTwo" };

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
				.contains("edge [ arrowhead = \"onormal\" style = \"solid\"  label=\"\"]\nTestInterfaceTwo -> TestInterface"));

	}

	@Test
	public void testAssociation() throws IOException {
		String[] args = new String[] { "testClasses.TestClass", "testClasses.TestClassTwo" };

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
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"\"]\nTestClass -> TestClassTwo"));
	}

	@Test
	public void testAssociationInArrayList() throws IOException {
		String[] args = new String[] { "testClasses.TestClass", "testClasses.TestClassTwo" };

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

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"\"]\nTestClassTwo -> TestClass"));
	}

	@Test
	public void testUsesReturn() throws IOException {
		String[] args = new String[] { "testClasses.TestClass", "testClasses.TestClassThree" };

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
		
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nTestClass -> TestClassThree"));
	}

	@Test
	public void testUsesArg() throws IOException {
		String[] args = new String[] { "testClasses.TestClass", "testClasses.TestSuperClass" };

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

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nTestClass -> TestSuperClass"));
	}

	@Test
	public void testUsesEdgeCaseInstanceCreation() throws IOException {
		String[] args = new String[] { "testClasses.TestClassTwo", "testClasses.TestSuperClass",
				"testClasses.TestSubClass" };

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

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nTestClassTwo -> TestSuperClass"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nTestClassTwo -> TestSubClass"));
	}
	
	@Test
	public void testAssociationTakesPriorityOverUses() throws IOException {
		String[] args = new String[] { "testClasses.TestClass", "testClasses.TestClassTwo" };

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

		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"\"]\nTestClass -> TestClassTwo"));
		assertFalse(result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nTestClass -> TestClassTwo"));
	}

	// Testing tricky factory cases
	@Test
	public void testFactoryBasicClass() throws IOException {
		String[] args = new String[] { "lab2_3.NYPizzaIngredientFactory"};

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
		
//		System.out.println(result);
		
		assertTrue(result.contains(
				"NYPizzaIngredientFactory[ label = \"{NYPizzaIngredientFactory||+ \\<init\\>() : void\\l\n+ createDough() : Dough\\l\n+ createSauce() : Sauce\\l\n+ createCheese() : Cheese\\l\n+ createVeggies() : Veggies[]\\l\n+ createPepperoni() : Pepperoni\\l\n+ createClam() : Clams\\l\n}\"]"));
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
				"edge [ arrowhead = \"onormal\" style = \"dashed\"  label=\"\"]\nNYPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains(
				"edge [ arrowhead = \"onormal\" style = \"dashed\"  label=\"\"]\nChicagoPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\"  label=\"\"]\nMozzarellaCheese -> Cheese"));
		assertTrue(result.contains("edge [ arrowhead = \"onormal\" style = \"dashed\"  label=\"\"]\nPlumTomatoSauce -> Sauce"));
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
		//System.out.println(result);
		assertTrue(
				result.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nNYPizzaIngredientFactory -> Cheese"));
		assertTrue(result
				.contains("edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nChicagoPizzaIngredientFactory -> Sauce"));
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
				"edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nNYPizzaIngredientFactory -> ReggianoCheese"));
		assertFalse(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nNYPizzaIngredientFactory -> MozzarellaCheese"));
		assertTrue(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nChicagoPizzaIngredientFactory -> PlumTomatoSauce"));
		assertFalse(result.contains(
				"edge [ arrowhead = \"vee\" style = \"dashed\"  label=\"\"]\nChicagoPizzaIngredientFactory -> MarinaraSauce"));
	}
	
	//Testing singleton detection
	@Test
	public void testDesktopSingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.awt.Desktop", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		//System.out.println(result);
		assertTrue(result.contains("Desktop\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testRuntimeSingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.lang.Runtime", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		assertTrue(result.contains("Runtime\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testCalendarNotSingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.util.Calendar", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		assertFalse(result.contains("Calendar\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testFilterInputStreamNotSingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("java.io.FilterInputStream", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		assertFalse(result.contains("FilterInputStream\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testEagerSingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("testClasses.TestEagerSingleton", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
				
		assertTrue(result.contains("TestEagerSingleton\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testLazySingleton() throws IOException {
		IModel model = new Model();
		IClass clazz = DesignParser.parse("testClasses.TestLazySingleton", model);
		model.addClass(clazz);
		PatternRecognizer.recognize((Model) model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		assertTrue(result.contains("TestLazySingleton\\n\\<\\<Singleton\\>\\>"));
	}
	
	@Test
	public void testDecoratorLab2_1() throws IOException {
		String[] args = new String[] { "lab2_1.DecryptionInputStream", "lab2_1.EncryptionOutputStream", "lab2_1.IDecryption", "lab2_1.IEncryption", "lab2_1.SubstitutionCipher", "lab2_1.TextEditorApp", "java.io.FilterInputStream", "java.io.FilterOutputStream", "java.io.InputStream", "java.io.OutputStream" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
				
		assertTrue(result.contains("DecryptionInputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FilterInputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("InputStream\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"decorates\" ]\nFilterInputStream -> InputStream"));

		assertTrue(result.contains("EncryptionOutputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FilterOutputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("OutputStream\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"decorates\" ]\nFilterOutputStream -> OutputStream"));
	}
	
	@Test
	public void testAdapterLab5_1() throws IOException {
		String[] args = new String[] { "lab5_1.App", "lab5_1.ArrayListAdapter", "lab5_1.LinearTransformer", "java.util.Enumeration", "java.util.Iterator" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
						
		assertTrue(result.contains("ArrayListAdapter\\n\\<\\<adapter\\>\\>"));
		assertTrue(result.contains("Enumeration\\n\\<\\<target\\>\\>"));
		assertTrue(result.contains("Iterator\\n\\<\\<adaptee\\>\\>"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"adapts\" ]\nArrayListAdapter -> Iterator"));
	}
	
	@Test
	public void testNotAdapterMouseAdapter() throws IOException {
		String[] args = new String[] { "java.awt.event.MouseAdapter", "java.awt.event.MouseEvent", "java.awt.event.MouseListener", "java.awt.event.MouseMotionListener", "java.awt.event.MouseWheelListener", "java.util.EventListener", "javax.swing.ToolTipManager", "javax.swing.event.MouseInputAdapter" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
						
		assertFalse(result.contains("\\n\\<\\<adapter\\>\\>"));
	}
	
	@Test
	public void testDecoratorInputStreamReader() throws IOException {
		String[] args = new String[] { "java.io.InputStreamReader", "java.io.Reader", "java.io.InputStream", "java.io.FileReader", "java.io.BufferedReader" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
						
		assertTrue(result.contains("InputStreamReader\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("BufferedReader\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("Reader\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"decorates\" ]\nBufferedReader -> Reader"));
	}
	
	@Test
	public void testDecoratorOutputStreamWriter() throws IOException {
		String[] args = new String[] { "java.io.OutputStreamWriter", "java.io.OutputStream", "java.io.Writer", "java.io.FileWriter", "java.io.PrintWriter", "java.io.BufferedWriter" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
								
		assertTrue(result.contains("OutputStreamWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FileWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("PrintWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("BufferedWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("Writer\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("edge [ arrowhead = \"vee\" style = \"solid\"  label=\"decorates\" ]\nBufferedWriter -> Writer"));
	}
	
	@Test
	public void testCompositeLab7_2() throws IOException {
		String[] args = new String[] { "lab7_2.AbstractSprite", "lab7_2.AnimationPanel", "lab7_2.AnimatorApp", "lab7_2.CircleSprite", "lab7_2.CompositeSprite", "lab7_2.CompositeSpriteIterator", "lab7_2.CrystalBall", "lab7_2.ISprite", "lab7_2.MainWindow", "lab7_2.NullSpriteIterator", "lab7_2.RectangleSprite", "lab7_2.RectangleTower", "lab7_2.SpriteFactory" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		//System.out.println(result);
						
		assertTrue(result.contains("ISprite\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("AbstractSprite\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("CompositeSprite\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("RectangleTower\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("CrystalBall\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("RectangleSprite\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("CircleSprite\\n\\<\\<leaf\\>\\>"));
	}
	
	@Test
	public void testCompositeAWT() throws IOException {
		String[] args = new String[] { "java.awt.Container", "java.awt.Component", "java.awt.Canvas", "java.awt.List", "java.awt.Scrollbar", "java.awt.Button" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
						
		assertTrue(result.contains("Component\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("Container\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("Canvas\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("List\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("Scrollbar\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("Button\\n\\<\\<leaf\\>\\>"));
	}
	
	@Test
	public void testCompositeSwing() throws IOException {
		String[] args = new String[] { "java.awt.Container", "java.awt.Component", "javax.swing.JLabel", "javax.swing.JPanel", "javax.swing.JComponent" };

		Model model = new Model();

		for (String className : args) {
			IClass clazz = DesignParser.parse(className, model);
			model.addClass(clazz);
		}

		PatternRecognizer.recognize(model);
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		GraphVizOutputStream gvos = new GraphVizOutputStream(resultStream);
		gvos.write(model);
		gvos.close();
		String result = resultStream.toString();
		
		System.out.println(result);
		
		assertTrue(result.contains("Component\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("Container\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JLabel\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JPanel\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JComponent\\n\\<\\<composite\\>\\>"));
	}
}
