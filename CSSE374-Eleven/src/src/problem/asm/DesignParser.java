package src.problem.asm;

import java.io.FileOutputStream;
import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.components.*;
import src.problem.components.Class;
import src.problem.outputvisitor.GraphVizOutputStream;
import src.problem.outputvisitor.SDEditOutputStream;

public class DesignParser {
	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Model model = new Model();
		GraphVizOutputStream gvos = new GraphVizOutputStream(new FileOutputStream("result.txt"));
		SDEditOutputStream sdeos = new SDEditOutputStream(new FileOutputStream("result2.txt"));
		
		// Used to generate UML for Lab 1-3 code
		String[] m1 = new String[] { "lab1_3.AppLauncher", "lab1_3.EntryDeleteObserver", "lab1_3.EntryModifyObserver",
				"lab1_3.HtmlCreateObserver", "lab1_3.Observer", "lab1_3.PngCreateObserver", "lab1_3.Subject",
				"lab1_3.TxtCreateObserver" };
		
		String[] m2 = new String[] { "lab2_3.Cheese", "lab2_3.ChicagoPizzaIngredientFactory", "lab2_3.Clams", "lab2_3.Dough",
				"lab2_3.FreshClams", "lab2_3.FrozenClams", "lab2_3.MarinaraSauce", "lab2_3.MozzarellaCheese", "lab2_3.NYPizzaIngredientFactory",
				"lab2_3.NYPizzaStore", "lab2_3.PizzaIngredientFactory", "lab2_3.PlumTomatoSauce", "lab2_3.ReggianoCheese", "lab2_3.Sauce",
				"lab2_3.ThickCrustDough", "lab2_3.ThinCrustDough", "lab2_3.Pizza" };
		
		String [] m3 = new String[] {"src.problem.components.Model", "src.problem.components.IModel", "src.problem.components.Class", 
				"src.problem.components.IClass", "src.problem.components.Field",  "src.problem.components.IField", 
				"src.problem.components.Method", "src.problem.components.IMethod", "src.problem.components.Parameter", 
				"src.problem.components.IParameter", 
				"src.problem.components.IRelation", "src.problem.components.Relation", "src.problem.components.RelationType", 
				"src.problem.outputvisitor.GraphVizOutputStream", "src.problem.outputvisitor.ITraverser", 
				"src.problem.outputvisitor.IVisitMethod", "src.problem.outputvisitor.IVisitor", 
				"src.problem.outputvisitor.LookupKey", "src.problem.outputvisitor.Visitor", "src.problem.outputvisitor.VisitType"};
		
		
		args = new String[]{"integrationTests.TestClass", "integrationTests.TestClassTwo"};

		for (String className : m3) {
			IClass clazz = parse(className, model);
			model.addClass(clazz);
		}
		sdeos.write(model);
		sdeos.close();
	}

	public static IClass parse(String args, IModel model) throws IOException {
		IClass clazz = new Class();

		// ASM's ClassReader does the heavy lifting of parsing the compiled
		// Java class
		ClassReader reader = new ClassReader(args);
		// make class declaration visitor to get superclass and interfaces
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		// DECORATE declaration visitor with field visitor
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, clazz, model);
		// DECORATE field visitor with method visitor
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, clazz, model);
		// TODO: add more DECORATORS here in later milestones to accomplish
		// specific tasks
		// Tell the Reader to use our (heavily decorated) ClassVisitor to
		// visit the class
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);

		return clazz;
	}
}