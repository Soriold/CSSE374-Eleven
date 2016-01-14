package src.problem.outputvisitor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

	public SDEditOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		this.classDeclarations = new StringBuilder();
		this.methodCalls = new StringBuilder();
		this.methods = new HashSet<String>();
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
	
	public void write(Model m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	private void setupVisitClass() {
		this.visitor.addVisit(VisitType.Visit, Class.class, (ITraverser t) -> {
			Class c = (Class)t;
			this.classDeclarations.append(c.getName() + ":" + c.getName());
			this.classDeclarations.append("\n");
		});

	}

	private void setupVisitMethod() {
		this.visitor.addVisit(VisitType.Visit, Method.class, (ITraverser t) -> {
			Method c = (Method) t;
			this.methods.add(c.getName());
			List<Pair<String, String>> methodCalls = c.getMethodCalls();
			for(Pair<String, String> pair : methodCalls) {
				if(methods.contains(pair.getLeft())) {
					System.out.println("here");
					this.methodCalls.append(c.getOwner() + ":" + pair.getRight() + "." + pair.getLeft());
					this.methodCalls.append("\n");
				}
			}
		});

	}
	
	private void setupPostVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, Model.class, (ITraverser t) -> {
			this.write(this.classDeclarations.toString());
			this.write("\n");
			this.write(this.methodCalls.toString());
		});
	}
}
