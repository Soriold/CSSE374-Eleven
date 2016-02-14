package src.problem.asm;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

	private volatile static DesignParser instance;

	private ArrayList<String> reqArgs;
	private HashMap<String, IPhase> phaseExecutables;

	private DesignParser() {
		this.phaseExecutables = new HashMap<>();
		this.phaseExecutables.put("DOT-Generation", new DotGenerator());
		this.phaseExecutables.put("Decorator-Detection", new DecoratorDetector());
		this.phaseExecutables.put("Composite-Detection", new CompositeDetector());
		this.phaseExecutables.put("Singleton-Detection", new SingletonDetector());
		this.phaseExecutables.put("Adapter-Detection", new AdapterDetector());

		this.reqArgs = new ArrayList<String>();
		this.reqArgs.add("Output-Directory");
		this.reqArgs.add("Dot-Path");
		this.reqArgs.add("Phases");
	}

	public static DesignParser getInstance() {
		if (instance == null) {
			synchronized (DesignParser.class) {
				if (instance == null) {
					instance = new DesignParser();
				}
			}
		}
		return instance;
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
		DesignParser p = DesignParser.getInstance();
//		System.out.println(Arrays.toString(
//				findClasses("S:\\GitHub\\CSSE374-Eleven\\CSSE374-Eleven\\CSSE374-Eleven\\asm-all-5.0.4.jar")));
		p.run(defaultProps);
	}

	public void run(Properties prop) throws Exception {
		String[] inputClasses = {};
		inputClasses = getInput(prop);

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
			this.phaseExecutables.get(phases[i].trim()).executeOn(model, prop);
		}

	}

	private String[] getInput(Properties prop) throws IOException, Exception {

		String[] inputClasses = null;

		if (!prop.keySet().containsAll(this.reqArgs)) {
			System.out.println(this.reqArgs.toString());
			System.out.println(prop.keySet().toString());
			throw new IllegalArgumentException("Incomplete arguments");

		} else if (!prop.keySet().contains("Input-Classes") && prop.keySet().contains("Input-Folder")) {
			String inputPath = prop.getProperty("Input-Folder");
			inputClasses = getClassesFromInputFolder(inputPath);

		} else if (!prop.keySet().contains("Input-Folder") && prop.keySet().contains("Input-Classes")) {
			inputClasses = prop.getProperty("Input-Classes").split(",");

		} else if (prop.keySet().contains("Input-Classes") && prop.keySet().contains("Input-Folder")) {
			String inputPath = prop.getProperty("Input-Folder");
			ArrayList<String> temp = new ArrayList<String>();
			temp.addAll(Arrays.asList(getClassesFromInputFolder(inputPath)));
			String[] cl = prop.getProperty("Input-Classes").split(",");
			temp.addAll(Arrays.asList(cl));

		} else {
			throw new IllegalArgumentException("Incomplete arguments");
		}

		return inputClasses;
	}

	private String[] getClassesFromInputFolder(String inputPath) throws IOException, Exception {
		String[] inputClasses;
		System.out.println(inputPath);
		if (inputPath != null) {
			System.out.println("copying");
			//Files.copy(Paths.get(inputPath), Paths.get("currentSrc\\"), REPLACE_EXISTING);
		}
		inputClasses = findClasses("currentSrc");
		return inputClasses;
	}

	private String[] findClasses(String path) throws Exception {
		List<String> classNames = null;
		System.out.println("PATH: " + path);
		if (path.endsWith(".jar")) {
			classNames = findClassesFromJar(path);
		} else {
			classNames = findClassesFromFolder(path);
		}

		String[] ret = new String[classNames.size()];
		int c = 0;
		for (String s : classNames) {
			ret[c] = s;
			c++;
		}
		return ret;
	}

	private List<String> findClassesFromFolder(String path) {
		System.out.println("path " + path);
		File folder = new File(path);
		for (File fileEntry : folder.listFiles()) {
			System.out.println(fileEntry.getName());
		}
		return new ArrayList<String>();
	}

	private List<String> findClassesFromJar(String path) throws FileNotFoundException, IOException {
		List<String> classNames;
		classNames = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream(new FileInputStream(path));
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
			if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
				String className = entry.getName().replace('/', '.');
				classNames.add(className.substring(0, className.length() - ".class".length()));
			}
		}
		zip.close();
		return classNames;
	}

	private IClass parse(String args, IModel model) throws IOException {
		IClass clazz = new Class();

		ClassReader reader = new ClassReader(args);
		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, clazz, model);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, clazz, model);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		return clazz;
	}
	
	private void generateGV(String arg) {
		String path = "C:\\Users\\howtc\\Desktop\\graphviz\\release\\bin\\temp.dot";
		
		try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8, StandardOpenOption.CREATE);)
		{
			writer.write(arg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessBuilder pb = new ProcessBuilder("C:\\Users\\howtc\\Desktop\\graphviz\\release\\bin\\dot.exe", "-Tpng", "C:\\Users\\howtc\\Desktop\\graphviz\\release\\bin\\temp.dot", "-o", "C:\\Users\\howtc\\Desktop\\out.png");
		try {
			File log = new File("C:\\Users\\howtc\\Desktop\\errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			Process p = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}