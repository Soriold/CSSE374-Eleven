package integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import src.problem.visible.DesignParser;

public class GVOSIntegrationTests {

	private DesignParser p;

	public GVOSIntegrationTests() {
		p = DesignParser.getInstance();
	}

	@Test
	public void testFields() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFields.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();

		String result = b.toString();

		assertTrue(result.contains("- testStringField : String"));
		assertTrue(result.contains("- testIntField : int"));
		assertTrue(result.contains("+ testListField : List"));
		assertTrue(result.contains("# testClassTwo : TestClassTwo"));
	}

	@Test
	public void testMethods() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testMethods.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();

		String result = b.toString();

		assertTrue(result.contains("- testPrivateMethod(int ) : void"));
		assertTrue(result.contains("# testProtectedMethod(int double ) : void"));
		assertTrue(result.contains("+ testPublicMethod(String ) : void"));
		assertTrue(result.contains("+ testGetIntField() : int"));
		assertTrue(result.contains("+ testGetStringField() : String"));
	}

	@Test
	public void testClass() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testClass.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();

		String result = b.toString();

		assertTrue(result.contains("TestClass|"));
	}

	@Test
	public void testImplements() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testImplements.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();
		
		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = dashed color=black label=\"\"] TestInterfaceImplementation -> TestInterface"));
	}

	@Test
	public void testInheritance() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testInheritance.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = solid color=black label=\"\"] TestSubClass -> TestSuperClass"));
	}

	@Test
	public void testInterfaceExtendsInterface() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testInterfaceExtendsInterface.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = solid color=black label=\"\"] TestInterfaceTwo -> TestInterface"));

	}

	@Test
	public void testAssociation() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testAssociation.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();
		
		assertTrue(result
				.contains("edge [ arrowhead = vee style = solid color=black label=\"\"] TestClass -> TestClassTwo"));
	}

	@Test
	public void testAssociationInArrayList() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testAssociationInArrayList.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result
				.contains("edge [ arrowhead = vee style = solid color=black label=\"\"] TestClassTwo -> TestClass"));
	}

	@Test
	public void testUsesReturn() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testUsesReturn.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result
				.contains("edge [ arrowhead = vee style = dashed color=black label=\"\"] TestClass -> TestClassThree"));
	}

	@Test
	public void testUsesArg() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testUsesArg.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result
				.contains("edge [ arrowhead = vee style = dashed color=black label=\"\"] TestClass -> TestSuperClass"));
	}

	@Test
	public void testUsesEdgeCaseInstanceCreation() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testUsesEdgeCaseInstanceCreation.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();
		
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] TestClassTwo -> TestSuperClass"));
		assertTrue(result
				.contains("edge [ arrowhead = vee style = dashed color=black label=\"\"] TestClassTwo -> TestSubClass"));
	}

	@Test
	public void testAssociationTakesPriorityOverUses() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testAssociationTakesPriorityOverUses.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result
				.contains("edge [ arrowhead = vee style = solid color=black label=\"\"] TestClass -> TestClassTwo"));
		assertFalse(result
				.contains("edge [ arrowhead = \"vee\" style = \"dashed\" color=black label=\"\"]\nTestClass -> TestClassTwo"));
	}

	// Testing tricky factory cases
	@Test
	public void testFactoryBasicClass() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFactoryBasicClass.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();
		
		System.out.println(result);
		
		assertTrue(result.contains(
				"NYPizzaIngredientFactory[color=black label = \"{NYPizzaIngredientFactory||+ \\<init\\>() : void\\l+ createDough() : Dough\\l+ createSauce() : Sauce\\l+ createCheese() : Cheese\\l+ createVeggies() : Veggies[]\\l+ createPepperoni() : Pepperoni\\l+ createClam() : Clams\\l}\"]"));
	}

	@Test
	public void testFactoryBasicExtension() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFactoryBasicExtension.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = dashed color=black label=\"\"] NYPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = dashed color=black label=\"\"] ChicagoPizzaIngredientFactory -> PizzaIngredientFactory"));
		assertTrue(result.contains(
				"edge [ arrowhead = onormal style = dashed color=black label=\"\"] MozzarellaCheese -> Cheese"));
		assertTrue(result
				.contains("edge [ arrowhead = onormal style = dashed color=black label=\"\"] PlumTomatoSauce -> Sauce"));
	}

	@Test
	public void testFactoryBasicUsesReturn() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFactoryBasicExtension.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] NYPizzaIngredientFactory -> Cheese"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] ChicagoPizzaIngredientFactory -> Sauce"));
	}

	@Test
	public void testFactoryBasicUsesInstantiation() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFactoryBasicUsesInstantiation.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] NYPizzaIngredientFactory -> ReggianoCheese"));
		assertFalse(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] NYPizzaIngredientFactory -> MozzarellaCheese"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] ChicagoPizzaIngredientFactory -> PlumTomatoSauce"));
		assertFalse(result.contains(
				"edge [ arrowhead = vee style = dashed color=black label=\"\"] ChicagoPizzaIngredientFactory -> MarinaraSauce"));
	}

	// Testing singleton detection
	@Test
	public void testDesktopSingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testDesktopSingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();
		
		assertTrue(result.contains("Desktop\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testRuntimeSingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testRuntimeSingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("Runtime\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testCalendarNotSingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testCalendarNotSingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertFalse(result.contains("Calendar\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testFilterInputStreamNotSingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testFilterInputStreamNotSingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertFalse(result.contains("FilterInputStream\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testEagerSingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testEagerSingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("TestEagerSingleton\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testLazySingleton() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testLazySingleton.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("TestLazySingleton\\n\\<\\<Singleton\\>\\>"));
	}

	@Test
	public void testDecoratorLab2_1() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testDecoratorLab2_1.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("DecryptionInputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FilterInputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("InputStream\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = solid color=black label=\"decorates\" ] FilterInputStream -> InputStream"));

		assertTrue(result.contains("EncryptionOutputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FilterOutputStream\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("OutputStream\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = solid color=black label=\"decorates\" ] FilterOutputStream -> OutputStream"));
	}

	@Test
	public void testAdapterLab5_1() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testAdapterLab5_1.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("ArrayListAdapter\\n\\<\\<adapter\\>\\>"));
		assertTrue(result.contains("Enumeration\\n\\<\\<target\\>\\>"));
		assertTrue(result.contains("Iterator\\n\\<\\<adaptee\\>\\>"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = solid color=black label=\"adapts\" ] ArrayListAdapter -> Iterator"));
	}

	@Test
	public void testNotAdapterMouseAdapter() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testNotAdapterMouseAdapter.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertFalse(result.contains("\\n\\<\\<adapter\\>\\>"));
	}

	@Test
	public void testDecoratorInputStreamReader() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testDecoratorInputStreamReader.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("InputStreamReader\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("BufferedReader\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("Reader\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = solid color=black label=\"decorates\" ] BufferedReader -> Reader"));
	}

	@Test
	public void testDecoratorOutputStreamWriter() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testDecoratorOutputStreamWriter.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("OutputStreamWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("FileWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("PrintWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("BufferedWriter\\n\\<\\<decorator\\>\\>"));
		assertTrue(result.contains("Writer\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains(
				"edge [ arrowhead = vee style = solid color=black label=\"decorates\" ] BufferedWriter -> Writer"));
	}

	@Test
	public void testCompositeLab7_2() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testCompositeLab7_2.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

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
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testCompositeAWT.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("Component\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("Container\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("Canvas\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("List\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("Scrollbar\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("Button\\n\\<\\<leaf\\>\\>"));
	}

	@Test
	public void testCompositeSwing() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testCompositeSwing.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("Component\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("Container\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JLabel\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JPanel\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("JComponent\\n\\<\\<composite\\>\\>"));
	}

	@Test
	public void testCompositeTestClasses() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("testing\\testing-input\\testCompositeTestClasses.properties"));
		prop.load(fis);
		fis.close();
		try {
			p.run(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(new FileReader("testing\\testing-output\\GVOutput.txt"));
        StringBuilder b = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
        	b.append(line);
        }
        reader.close();
        
		String result = b.toString();

		assertTrue(result.contains("TestCompositeComponent\\n\\<\\<component\\>\\>"));
		assertTrue(result.contains("TestCompositeComposite1\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("TestCompositeComposite2\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("TestCompositeComposite3\\n\\<\\<composite\\>\\>"));
		assertTrue(result.contains("TestCompositeLeaf1\\n\\<\\<leaf\\>\\>"));
		assertTrue(result.contains("TestCompositeLeaf2\\n\\<\\<leaf\\>\\>"));
	}
}
