package src.problem.visible;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import src.problem.asm.ClassDeclarationVisitor;
import src.problem.asm.ClassFieldVisitor;
import src.problem.asm.ClassMethodVisitor;
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
import src.problem.components.Relation;

public class DesignParser {

	private volatile static DesignParser instance;

	private ArrayList<String> reqArgs;
	private HashMap<String, IPhase> phaseExecutables;
	private IModel model;
	private String currentParseClass;
	private String currentPhase;

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
		this.reqArgs.add("Pattern-Types");
		this.reqArgs.add("Relation-Types");
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

	public void run(Properties prop) throws Exception {

		checkParameters(prop);

		String[] inputClasses = {};
		List<byte[]> importedClasses = new ArrayList<>();
		if (prop.keySet().contains("Input-Classes")) {
			inputClasses = prop.getProperty("Input-Classes").split(",");
		}
		if (prop.keySet().contains("Input-Folder")) {
			importedClasses = FileImporter.getClassesFromInputFolder(prop.getProperty("Input-Folder"));
		}

		String[] phases = prop.getProperty("Phases").split(",");

		if (phases.length == 0 || !phases[0].trim().equals("Class-Loading")) {
			throw new IllegalArgumentException("Invalid order of phases. Class-Loading must happen first.");
		}

		String patternPath = prop.getProperty("Pattern-Types", null);
		Class.setPatternTypes(EnumExtractor.extractKeys(patternPath));

		String relationPath = prop.getProperty("Relation-Types", null);
		Relation.setRelationTypes(EnumExtractor.extractKeys(relationPath));

		this.model = new Model();

		currentPhase = phases[0];

		for (String className : inputClasses) {
			currentParseClass = className;
			IClass clazz = parse(className.trim(), model);
			model.addClass(clazz);
		}

		for (byte[] clazz : importedClasses) {
			IClass pClass = parse(clazz, model);
			currentParseClass = pClass.getName();
			model.addClass(pClass);
		}

		currentParseClass = null;

		for (int i = 1; i < phases.length; i++) {
			currentPhase = phases[i];
			this.phaseExecutables.get(phases[i].trim()).executeOn(model, prop);
		}

		currentPhase = null;

	}

	public IClass parse(String args, IModel model) throws IOException {
		ClassReader reader = new ClassReader(args);
		return parse(reader, model);
	}

	public IClass parse(byte[] args, IModel model) throws IOException {
		ClassReader reader = new ClassReader(args);
		return parse(reader, model);

	}

	private IClass parse(ClassReader reader, IModel model) {
		IClass clazz = new Class();

		ClassVisitor decVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, clazz, model);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, decVisitor, clazz, model);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, clazz, model);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		return clazz;
	}

	private void checkParameters(Properties prop) {
		Set<Object> keys = prop.keySet();
		for (String s : this.reqArgs) {
			if (!keys.contains(s)) {
				throw new IllegalArgumentException("Incomplete Arguments : You must include " + s);
			}
		}
		if (!(keys.contains("Input-Classes") || keys.contains("Input-Folder"))) {
			throw new IllegalArgumentException(
					"Incomplete Arguments : You must include at least one of Input-Classes or Input-Folder");
		}
	}

	@SuppressWarnings("unused")
	public static void generateGV(String arg) {
		String path = "temp.dot";

		try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			writer.write(arg);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessBuilder pb = new ProcessBuilder("graphviz-2.38\\release\\bin\\dot.exe", "-Tpng", "temp.dot", "-o",
				"input-output\\uml.png");
		try {
			File log = new File("errorLog.txt");
			pb.redirectErrorStream(true);
			pb.redirectOutput(Redirect.appendTo(log));
			Process p = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addPhaseExecutable(String callName, IPhase phase) {
		this.phaseExecutables.put(callName, phase);
	}

	public IModel getModel() {
		return this.model;
	}

	public String getCurrentPhase() {
		return currentPhase;
	}

	public String getCurrentParseClass() {
		return currentParseClass;
	}

	public HashMap<String, IPhase> getPhaseExecutables() {
		return phaseExecutables;
	}
}