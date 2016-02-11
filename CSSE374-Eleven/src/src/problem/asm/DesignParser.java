package src.problem.asm;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.commands.AdapterDetector;
import src.problem.commands.CompositeDetector;
import src.problem.commands.DecoratorDetector;
import src.problem.commands.DotGenerator;
import src.problem.commands.IPhase;
import src.problem.commands.SingletonDetector;
import src.problem.components.Class;
import src.problem.components.IClass;
import src.problem.components.IModel;
import src.problem.components.Model;

public class DesignParser {

	private static String[] reqArgs = { "Input-Classes", "Output-Directory", "Dot-Path", "Phases" };
	private static HashMap<String, IPhase> phaseExecutables;

	static {
		phaseExecutables = new HashMap<>();
		phaseExecutables.put("DOT-Generation", new DotGenerator());
		phaseExecutables.put("Decorator-Detection", new DecoratorDetector());
		phaseExecutables.put("Composite-Detection", new CompositeDetector());
		phaseExecutables.put("Singleton-Detection", new SingletonDetector());
		phaseExecutables.put("Adapter-Detection", new AdapterDetector());
	}

	/**
	 * Reads in a list of Java Classes and reverse engineers their design.
	 *
	 * @param args:
	 *            the names of the classes, separated by spaces. For example:
	 *            java DesignParser java.lang.String
	 *            edu.rosehulman.csse374.ClassFieldVisitor java.lang.Math
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Properties defaultProps = new Properties();
		FileInputStream in = new FileInputStream("default.properties");
		defaultProps.load(in);
		in.close();
		run(defaultProps);
	}

	public static void run(Properties prop) throws Exception {
		if (!prop.keySet().containsAll(Arrays.asList(reqArgs))) {
			throw new IllegalArgumentException("Incomplete arguments");
		}
		String[] inputClasses = prop.getProperty("Input-Classes").split(",");
		String inputPath = prop.getProperty("Input-Folder");
		Files.copy(Paths.get(inputPath), Paths.get("currentSrc"), REPLACE_EXISTING);
		String[] phases = prop.getProperty("Phases").split(",");

		if (!phases[0].equals("Class-Loading")) {
			throw new IllegalArgumentException("Invalid order of phases. Class-Loading must happen first.");
		}

		Model model = new Model();

		for (String className : inputClasses) {
			IClass clazz = parse(className, model);
			model.addClass(clazz);
		}

		for (int i = 1; i < phases.length; i++) {
			phaseExecutables.get(phases[i]).executeOn(model, prop);
		}

	}

	public static IClass parse(String args, IModel model) throws IOException {
		IClass clazz = new Class();
		
		ClassReader reader = new ClassReader(args);
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, clazz, model);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, clazz, model);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		return clazz;
	}
}