package src.problem.outputvisitor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.problem.asm.Pair;
import src.problem.components.Class;
import src.problem.components.Method;
import src.problem.components.Model;

public class SDEditOutputStream extends FilterOutputStream {

	private final IVisitor visitor;
	private StringBuilder classDeclarations;
	private StringBuilder methodCalls;
	private Set<String> methods;
	private Set<String> clazzes;
	private HashMap<Pair<String, String>, ArrayList<Pair<String, String>>> methodMapping;
	private String clazz;

	public SDEditOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		this.classDeclarations = new StringBuilder();
		this.methodCalls = new StringBuilder();
		this.methods = new HashSet<String>();
		this.clazzes = new HashSet<String>();
		this.methodMapping = new HashMap<>();
		this.setupVisitors();
	}

	private void setupVisitors() {
		this.setupVisitClass();
		this.setupVisitMethod();
		this.setupPostVisitModel();
	}

	private void write(String m) {
		try {
			super.write(m.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeMethod(Model m, String methodQualifier, int depth) {
		this.write(m);
		String clazz = this.findClass(methodQualifier);
		String method = this.findMethod(methodQualifier);
		Pair<String, String> methodToSequence = new Pair<String, String>(clazz, method);
		this.writeMethod(methodToSequence, depth);
		writeClasses();
		this.write("\n");
		this.write(this.methodCalls.toString());
	}

	private void writeClasses() {
		for(String c : this.clazzes) {
			this.write(c + ":" + c);
			this.write("\n");
		}
	}

	private void writeMethod(Pair<String, String> key, int depth) {
		this.clazzes.add(key.getLeft());
		if(this.methodMapping.get(key) == null) {
			return;
		}
		System.out.println(key);
		for (Pair<String, String> p : this.methodMapping.get(key)) {
			this.clazzes.add(p.getLeft());
			if (depth == 1) {
				this.methodCalls.append(key.getLeft() + ":" + p.getLeft() + "." + p.getRight());
				this.methodCalls.append("\n");
			} else {
				this.methodCalls.append(key.getLeft() + ":" + p.getLeft() + "." + p.getRight());
				this.methodCalls.append("\n");
				this.writeMethod(p, depth - 1);
			}
		}
	}

	private String findMethod(String methodQualifier) {
		String[] splits = methodQualifier.split("\\.");
		String ret = splits[splits.length - 1];
		ret = ret.substring(0, ret.indexOf('('));
		return ret;
	}

	private String findClass(String methodQualifier) {
		String[] splits = methodQualifier.split("\\.");
		return splits[splits.length - 2];
	}

	public void writeMethod(Model m, String methodQualifier) {
		this.writeMethod(m, methodQualifier, 5);
	}

	public void write(Model m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	private void setupVisitClass() {
		this.visitor.addVisit(VisitType.Visit, Class.class, (ITraverser t) -> {
			Class c = (Class) t;
			this.classDeclarations.append(c.getName() + ":" + c.getName());
			this.classDeclarations.append("\n");
		});

	}

	private void setupVisitMethod() {
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			Method c = (Method) t;
			this.methods.add(c.getName());
			List<Pair<String, String>> methodCalls = c.getMethodCalls();
			for (Pair<String, String> pair : methodCalls) {
				this.insertMapping(c.getOwner(), c.getName(), pair.getRight(), pair.getLeft());
				// if (methods.contains(pair.getLeft())) {
				// System.out.println("here");
				// this.methodCalls.append(c.getOwner() + ":" + pair.getRight()
				// + "." + pair.getLeft());
				// this.methodCalls.append("\n");
				// }
			}
		});

	}

	private void insertMapping(String owner, String name, String callClass, String callMethod) {
		Pair<String, String> key = new Pair<String, String>(owner, name);
		Pair<String, String> value = new Pair<String, String>(callClass, callMethod);
		if (this.methodMapping.containsKey(key)) {
			ArrayList<Pair<String, String>> toInsert = this.methodMapping.get(key);
			toInsert.add(value);
			this.methodMapping.remove(key);
			this.methodMapping.put(key, toInsert);
		} else {
			ArrayList<Pair<String, String>> toInsert = new ArrayList<>();
			toInsert.add(value);
			this.methodMapping.put(key, toInsert);
		}
	}

	private void setupPostVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, Model.class, (ITraverser t) -> {
			// this.write(this.classDeclarations.toString());
			// this.write("\n");
			// this.write(this.methodCalls.toString());
		});
	}
}
