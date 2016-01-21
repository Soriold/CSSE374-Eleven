package src.problem.asm;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.components.Class;
import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.components.Model;
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
		GraphVizOutputStream gvos = new GraphVizOutputStream(new FileOutputStream("GVOutput.txt"));
		SDEditOutputStream sdeos = new SDEditOutputStream(new FileOutputStream("SDEditOutput.txt"));

		Scanner scanner = new Scanner(new File(args[0]));
		scanner.useDelimiter("\r\n");
		ArrayList<String> argumentsAL = new ArrayList<String>();
		while (scanner.hasNext()) {
			argumentsAL.add(scanner.next());
		}
		scanner.close();

		String[] arguments = new String[] {};
		arguments = argumentsAL.toArray(arguments);

		for (String className : arguments) {
			IClass clazz = parse(className, model);
			model.addClass(clazz);
		}
		gvos.write(model);
		if (Boolean.parseBoolean(args[1])) {
			sdeos.writeMethod(model, "src.problem.outputvisitor.SDEditOutputStream.writeMethod(Model String int)", Integer.valueOf(args[3]));
		}
		sdeos.close();
		gvos.close();
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