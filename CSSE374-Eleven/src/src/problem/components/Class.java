package src.problem.components;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.problem.outputvisitor.ITraverser;
import src.problem.outputvisitor.IVisitor;

public class Class implements IClass {

	private String name;
	private List<IField> fields;
	private List<IMethod> methods;
	private boolean isInterface;
	private List<String> interfaces;
	private String superClass;
	private String pattern;
	private String stereotype;
	private Set<String> patternTypes;

	public Class() {
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.interfaces = new ArrayList<String>();
		this.isInterface = false;
		patternTypes = new HashSet<String>();
		try {
			loadPatternTypes();
		} catch (IOException e) {
			System.out.println("Error loading pattern type configuration.");
		}
		this.pattern = "NONE";
	}

	private void loadPatternTypes() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("patternTypesConfig.txt"));
        String line = "";
        while ((line = in.readLine()) != null) {
        	String[] current = line.split("-");
        	patternTypes.add(current[0]);
        }
        in.close();
	}

	@Override
	public String getName() {
		return name.replace("$", "");
	}

	@Override
	public List<IField> getFields() {
		return fields;
	}

	@Override
	public List<IMethod> getMethods() {
		return methods;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addField(IField field) {
		this.fields.add(field);
	}

	@Override
	public void addMethod(IMethod method) {
		this.methods.add(method);
	}

	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}

	@Override
	public void addInterface(String impl) {
		this.interfaces.add(impl);
	}

	@Override
	public String getSuperClass() {
		return this.superClass;
	}

	@Override
	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	@Override
	public boolean getIsInterface() {
		return this.isInterface;
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	@Override
	public boolean equals(Object obj) {
		IClass c = (Class)obj;
		return this.name.equals(c.getName());
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for(IField f : this.fields) {
			ITraverser t = (ITraverser) f;
			t.accept(v);
		}
		v.visit(this);
		for (IMethod m : this.methods) {
			ITraverser t = (ITraverser) m;
			t.accept(v);
		}
		v.postVisit(this);		
	}
	
	public String getPattern() {
		return this.pattern;
	}

	@Override
	public void setStereotype(String string) {
		this.stereotype = string;
	}

	@Override
	public String getStereotype() {
		return this.stereotype;
	}

	@Override
	public void setPattern(String pattern) {
		if(patternTypes.contains(pattern)) {
			this.pattern = pattern;
		}
	}

	@Override
	public Set<String> getPatternTypes() {
		return patternTypes;
	}
}
