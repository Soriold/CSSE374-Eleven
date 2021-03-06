 package src.problem.outputvisitor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import src.problem.components.Class;
import src.problem.components.IMethod;
import src.problem.components.IMethodCall;
import src.problem.components.IParameter;
import src.problem.components.Method;
import src.problem.components.Model;
import src.problem.components.Parameter;

public class SDEditOutputStream extends FilterOutputStream {

	private final IVisitor visitor;
	private StringBuilder classDeclarations;
	private StringBuilder methodCalls;
	private Map<Method, Method> methods;
	private Set<String> clazzes;
	private Map<IMethod, ArrayList<IMethod>> methodMapping;

	public SDEditOutputStream(OutputStream out) {
		super(out);
		this.visitor = new Visitor();
		this.classDeclarations = new StringBuilder();
		this.methodCalls = new StringBuilder();
		this.methods = new TreeMap<Method, Method>();
		this.clazzes = new HashSet<String>();
		this.methodMapping = new TreeMap<>();
		this.setupVisitors();
	}

	private void setupVisitors() {
		this.setupVisitClass();
		this.setupVisitMethod();
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
		IMethod methodToSequence = findMethod(methodQualifier);
		//System.out.println("Accessing methods TreeMap");
		methodToSequence = this.methods.get(methodToSequence);
		this.writeMethodHelper(methodToSequence, depth);
		writeClasses();
		this.write("\n");
		this.write(this.methodCalls.toString());
	}

	private IMethod findMethod(String methodSignature) {
		IMethod method = new Method();
		method.setOwner(this.findClass(methodSignature));
		method.setName(this.findMethodName(methodSignature));
		for (IParameter p : this.findParameters(methodSignature)) {
			method.addParameter(p);
		}
		return method;
	}

	private ArrayList<IParameter> findParameters(String methodSignature) {
		ArrayList<IParameter> ret = new ArrayList<>();
		String params = methodSignature.substring(methodSignature.indexOf("("), methodSignature.indexOf(")") + 1);
		String newChar = "";
		params = params.replace("(", newChar).replace(",", " ").replace(")", newChar);
		String[] paramArray = params.split(" ");
		for(int i = 0; i < paramArray.length; i += 2) {
			if (!paramArray[i].trim().equals("")) {
				IParameter p = new Parameter();
				//System.out.println("user input params: " + paramArray[i] + "blah");
				p.setType(paramArray[i].trim());
				ret.add(p);
			}
		}
		//System.out.println(methodSignature + "-->>>" + ret.toString());
		return ret;
	}

	private void writeClasses() {
		for(String c : this.clazzes) {
			this.write(c + ":" + c);
			this.write("\n");
		}
	}

	private void writeMethodHelper(IMethod key, int depth) {
		//System.out.println("***parsing body of: " + key.getName() + "(" + key.getParameters().toString()+")*** at depth: "+depth);
		this.clazzes.add(key.getOwner());
		if(this.methodMapping.get(key) == null) {
			//System.out.println(key.getOwner() + "."+ key.getName() + " body is empty");
			return;
		}
		for (IMethod m : this.methodMapping.get(key)) {
			this.clazzes.add(m.getOwner());
			if (depth <= 1) {
				this.makeMethodArrow(key, m);
			} else {
				this.makeMethodArrow(key, m);
				if (! m.equals(key)) {
					this.writeMethodHelper(m, depth - 1);
				} else {
					//System.out.println("same");
				}
			}
		}
	}

	private void makeMethodArrow(IMethod key, IMethod m) {
		//System.out.println("Drawing arrow for " + key.getOwner() + "." + key.getName() + "'s " + "call on " + m.getOwner() + "." + m.getName());
		String app = key.getOwner() + ":" + m.getReturnType() + "=" + m.getOwner() + "." + m.getName() + "(";
		for(IParameter p : m.getParameters()){
			app += p.getType() + " ";
		}
		app += ")\n";
		this.methodCalls.append(app);
	}

	private String findMethodName(String methodSignature) {
		String[] splits = methodSignature.split("\\.");
		String ret = splits[splits.length - 1];
		ret = ret.substring(0, ret.indexOf('('));
		return ret.trim();
	}

	private String findClass(String methodSignature) {
		String[] splits = methodSignature.split("\\.");
		return splits[splits.length - 2].trim();
	}

	public void writeMethod(Model m, String methodQualifier) {
		this.writeMethod(m, methodQualifier, 5);
	}

	private void write(Model m) {
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
			this.methods.put(c, c);
			List<IMethodCall> methodCalls = c.getMethodCalls();
			for (IMethodCall mc : methodCalls) {
				this.insertMapping(mc.getSourceMethod(), mc.getDestMethod());
			}
		});

	}

	private void insertMapping(IMethod source, IMethod dest) {
		if (this.methodMapping.containsKey(source)) {
			ArrayList<IMethod> toInsert = this.methodMapping.get(source);
			toInsert.add(dest);
			this.methodMapping.remove(source);
			this.methodMapping.put(source, toInsert);
		} else {
			ArrayList<IMethod> toInsert = new ArrayList<>();
			toInsert.add(dest);
			this.methodMapping.put(source, toInsert);
		}
	}
}
