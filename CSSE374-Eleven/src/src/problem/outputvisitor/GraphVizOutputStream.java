package src.problem.outputvisitor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import src.problem.components.Class;
import src.problem.components.Field;
import src.problem.components.IModel;
import src.problem.components.Method;
import src.problem.components.Model;
import src.problem.components.Parameter;
import src.problem.components.PatternType;
import src.problem.components.Relation;

public class GraphVizOutputStream extends FilterOutputStream {

	private final IVisitor visitor;


	public GraphVizOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		this.setupVisitors();
	}

	private void setupVisitors() {
		this.setupPreVisitModel();
		this.setupPostVisitModel();
		this.setupPreVisitClass();
		this.setupVisitClass();
		this.setupPostVisitClass();
		this.setupVisitField();
		this.setupPreVisitMethod();
		this.setupPostVisitMethod();
		this.setupVisitParameter();
		this.setupVisitRelationship();
	}
	
	private void write(String m) {
		try {
			super.write(m.getBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void write(IModel m) {
		ITraverser t = (ITraverser) m;
		t.accept(this.visitor);
	}

	private void setupPreVisitModel() {
		this.visitor.addVisit(VisitType.PreVisit, Model.class, (ITraverser t) -> {
			this.write("digraph G {fontname = \"Bitstream Vera Sans\" fontsize = 8\nnode [fontname ="
					+ "\"Bitstream Vera Sans\" fontsize = 8 shape = \"record\"] edge [fontname = "
					+ "\"Bitstream Vera Sans\" fontsize = 8]");
		});
	}

	private void setupPostVisitModel() {
		this.visitor.addVisit(VisitType.PostVisit, Model.class, (ITraverser t) -> {
			this.write("\n}");
		});

	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, Class.class, (ITraverser t) -> {
			Class c = (Class) t;
			StringBuilder ret = new StringBuilder();
			ret.append(c.getName());
			ret.append("[");
			ret.append(this.getColor(c.getPattern()));
			ret.append(" label = \"{");
			if (c.getIsInterface()) {
				ret.append("\\<\\<interface\\>\\>");
				ret.append("\\n");
			}
			ret.append(c.getName());
			ret.append(this.getStereotype(c.getStereotype()));
			ret.append("|");

			this.write(ret.toString());
		});

	}

	private Object getColor(PatternType pattern) {
		switch(pattern) {
		case SINGLETON:
			return "color=blue";
		case DECORATOR:
			return "color=green";
		case ADAPTER:
			return "color=red";
		default:
			return "";
		}
	}

	private String getStereotype(String string) {
		System.out.println("Spotted pattern " + string);
		if(string == null) {
			return "";
		} 
		return "\\n\\<\\<" + string + "\\>\\>";
	}

	private void setupVisitClass() {
		this.visitor.addVisit(VisitType.Visit, Class.class, (ITraverser t) -> {
			this.write("|");
		});

	}

	private void setupPostVisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, Class.class, (ITraverser t) -> {
			this.write("}\"]\n");
		});

	}

	private void setupVisitField() {
		this.visitor.addVisit(VisitType.Visit, Field.class, (ITraverser t) -> {
			Field c = (Field) t;
			StringBuilder ret = new StringBuilder();
	
			if (c.getVisibility().equals("public")) {
				ret.append("+ ");
			} else if (c.getVisibility().equals("private")) {
				ret.append("- ");
			} else if (c.getVisibility().equals("protected")) {
				ret.append("# ");
			}
	
			ret.append(c.getName());
			ret.append(" : ");
			ret.append(c.getType());
			ret.append("\\l\n");
			String mod = ret.toString().replace("<", "\\<");
			mod = mod.replace(">", "\\>");
			this.write(mod);
		});
	
	}

	private void setupPreVisitMethod() {
		this.visitor.addVisit(VisitType.PreVisit, Method.class, (ITraverser t) -> {
			Method c = (Method) t;
			StringBuilder ret = new StringBuilder();

			if (c.getVisibility().equals("public")) {
				ret.append("+ ");
			} else if (c.getVisibility().equals("private")) {
				ret.append("- ");
			} else if (c.getVisibility().equals("protected")) {
				ret.append("# ");
			}

			ret.append(c.getName());
			ret.append("(");
			String mod = ret.toString().replace("<", "\\<");
			mod = mod.replace(">", "\\>");
			this.write(mod);
		});
	}

	private void setupPostVisitMethod() {
		this.visitor.addVisit(VisitType.PostVisit, Method.class, (ITraverser t) -> {
			Method c = (Method) t;
			StringBuilder ret = new StringBuilder();
			ret.append(") : ");
			ret.append(c.getReturnType());
			ret.append("\\l\n");
			
			String mod = ret.toString().replace("<", "\\<");
			mod = mod.replace(">", "\\>");
			this.write(mod);
		});

	}

	private void setupVisitRelationship() {
		this.visitor.addVisit(VisitType.Visit, Relation.class, (ITraverser t) -> {
			Relation c = (Relation) t;
			switch (c.getType()) {
				case EXTENDS:
					this.write("edge [ arrowhead = \"onormal\" style = \"solid\" ]\n" + c.getSrc() + " -> " + c.getDest()
							+ "\n");
					break;
				case IMPLEMENTS:
					this.write("edge [ arrowhead = \"onormal\" style = \"dashed\" ]\n" + c.getSrc() + " -> " + c.getDest()
							+ "\n");
					break;
				case ASSOCIATION:
					this.write(
							"edge [ arrowhead = \"vee\" style = \"solid\" ]\n" + c.getSrc() + " -> " + c.getDest() + "\n");
					break;
				case USES:
					this.write(
							"edge [ arrowhead = \"vee\" style = \"dashed\" ]\n" + c.getSrc() + " -> " + c.getDest() + "\n");
					break;
			}
		});
	}

	private void setupVisitParameter() {
		this.visitor.addVisit(VisitType.Visit, Parameter.class, (ITraverser t) -> {
			Parameter c = (Parameter) t;
			String mod = c.getType().toString().replace("<", "\\<");
			mod = mod.replace(">", "\\>");
			this.write(mod + " ");
		});
	}

}
