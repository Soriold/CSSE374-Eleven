package src.problem.outputvisitor;

import src.problem.components.IClass;

/*import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;*/

import src.problem.components.IField;
import src.problem.components.IMethod;
import src.problem.components.IParameter;

public class GraphVizOutputStream /* extends FilterOutputStream */ {

	private final IVisitor visitor;
	private StringBuilder output;

	public GraphVizOutputStream(/* OutputStream out */) {
		// super(out);
		this.visitor = new Visitor();
		this.output = new StringBuilder();
		this.setupVisitors();
	}

	private void setupVisitors() {
		this.setupPreVisitClass();
		this.setupVisitClass();
		this.setupPostVisitClass();
		this.setupVisitField();
		this.setupPreVisitMethod();
		this.setupPostVisitMethod();
		this.setupVisitParameter();
		this.setupVisitRelationship();
	}

	private void setupPreVisitClass() {
		this.visitor.addVisit(VisitType.PreVisit, IClass.class, (ITraverser t) -> {
			IClass c = (IClass) t;
			StringBuilder ret = new StringBuilder();
			ret.append(c.getName());
			ret.append(" [label = \"{");
			if (c.getIsInterface()) {
				ret.append("<<interface>>");
				ret.append("\\n");
			}
			ret.append(c.getName());
			ret.append("|");
	
			this.output.append(ret.toString());
		});
		
	}

	private void setupVisitClass() {
		this.visitor.addVisit(VisitType.Visit, IClass.class, (ITraverser t) -> {
			this.output.append("|");
		});
	
	}

	private void setupPostVisitClass() {
		this.visitor.addVisit(VisitType.PostVisit, IClass.class, (ITraverser t) -> {
			this.output.append("}\"]");
		});
		
	}

	private void setupPreVisitMethod() {
		this.visitor.addVisit(VisitType.PreVisit, IMethod.class, (ITraverser t) -> {
			IMethod c = (IMethod) t;
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
			this.output.append(ret.toString());
		});
	}

	private void setupPostVisitMethod() {
		this.visitor.addVisit(VisitType.PostVisit, IMethod.class, (ITraverser t) -> {
			IMethod c = (IMethod) t;
			this.output.append(") : ");
			this.output.append(c.getReturnType());
			this.output.append("\\l");
		});
		
	}

	private void setupVisitField() {
		this.visitor.addVisit(VisitType.Visit, IField.class, (ITraverser t) -> {
			IField c = (IField) t;
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
			ret.append("\\l");
	
			this.output.append(ret.toString());
		});
	
	}

	private void setupVisitRelationship() {
		// TODO Auto-generated method stub

	}

	private void setupVisitParameter() {
		this.visitor.addVisit(VisitType.Visit, IParameter.class, (ITraverser t) -> {
			IParameter c = (IParameter) t;
			this.output.append(c.getType());
		});
	}


	private void write(String m) {
		/*
		 * try { super.write(m.getBytes()); } catch (IOException e) { throw new
		 * RuntimeException(e); }
		 */
	}

}
