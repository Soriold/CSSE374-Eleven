package src.problem.outputvisitor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import src.problem.asm.Pair;
import src.problem.components.Class;
import src.problem.components.Field;
import src.problem.components.IModel;
import src.problem.components.Method;
import src.problem.components.Model;
import src.problem.components.Parameter;
import src.problem.components.Relation;

public class SDEditOutputStream extends FilterOutputStream {

	private final IVisitor visitor;
	private StringBuilder output;

	public SDEditOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		this.output = new StringBuilder();
		this.setupVisitors();
	}

	private void setupVisitors() {
		this.setupVisitClass();
		this.setupPostVisitClass();
		this.setupVisitMethod();
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
			System.out.println("here");
			Class c = (Class)t;
			this.write(c.getName() + ":" + c.getName());
			this.write("\n");
		});

	}
	
	private void setupPostVisitClass() {
		this.visitor.addVisit(VisitType.Visit, Class.class, (ITraverser t) -> {
			this.write("\n");
		});
	}

	private void setupVisitMethod() {
		this.visitor.addVisit(VisitType.PostVisit, Method.class, (ITraverser t) -> {
			Method c = (Method) t;
			List<Pair<String, String>> methodCalls = c.getMethodCalls();
			for(Pair<String, String> pair : methodCalls) {
				this.write(c.getOwner() + ":" + pair.getRight() + "." + pair.getLeft());
				this.write("\n");
			}
		});

	}
}
