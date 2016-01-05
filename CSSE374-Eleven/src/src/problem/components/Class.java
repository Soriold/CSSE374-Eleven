package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class Class implements IClass {
	
	private String name;
	private List<IField> fields;
	private List<IMethod> methods;
	
	public Class() {
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
	}
	
	@Override
	public String getName() {
		return name;
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


}
