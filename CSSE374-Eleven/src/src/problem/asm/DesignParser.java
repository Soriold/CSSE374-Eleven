package src.problem.asm;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

	private static String[] reqArgs = { "Output-Directory", "Dot-Path", "Phases" };
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
		System.out.println(Arrays.toString(findClasses("S:\\GitHub\\CSSE374-Eleven\\CSSE374-Eleven\\CSSE374-Eleven\\asm-all-5.0.4.jar")));
		run(defaultProps);
	}

	public static void run(Properties prop) throws Exception {
		String[] inputClasses = {};
		if (!prop.keySet().containsAll(Arrays.asList(reqArgs))) {
			throw new IllegalArgumentException("Incomplete arguments");
		} else if (!prop.keySet().contains("Input-Classes") && prop.keySet().contains("Input-Folder")) {
			String inputPath = prop.getProperty("Input-Folder");
			if (inputPath != null) {
				Files.copy(Paths.get(inputPath), Paths.get("currentSrc"), REPLACE_EXISTING);
			}
			inputClasses = findClasses("currentSrc");
		} else if (!prop.keySet().contains("Input-Folder") && prop.keySet().contains("Input-Classes")) {
			inputClasses = prop.getProperty("Input-Classes").split(",");
		}

		String[] phases = prop.getProperty("Phases").split(",");

		if (phases.length == 0 || !phases[0].equals("Class-Loading")) {
			throw new IllegalArgumentException("Invalid order of phases. Class-Loading must happen first.");
		}

		Model model = new Model();

		for (String className : inputClasses) {
			IClass clazz = parse(className.trim(), model);
			model.addClass(clazz);
		}

		for (int i = 1; i < phases.length; i++) {
			phaseExecutables.get(phases[i].trim()).executeOn(model, prop);
		}

	}

	private static String[] findClasses(String path) throws Exception {
		List<String> classNames = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
		    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
		        // This ZipEntry represents a class. Now, what class does it represent?
		        String className = entry.getName().replace('/', '.'); // including ".class"
		        classNames.add(className.substring(0, className.length() - ".class".length()));
		    }
		}
		String[] ret = new String[classNames.size()];
		int c = 0;
		for(String s : classNames) {
			ret[c] = s;
			c++;
		}
		zip.close();
		return ret;
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