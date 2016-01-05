package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class Class implements IClass {

	private String name;
	private List<IField> fields;
	private List<IMethod> methods;
	private boolean isInterface;
	private List<String> interfaces;
	private String superClass;

	public Class() {
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.interfaces = new ArrayList<String>();
		this.isInterface = false;
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
	public String getGraphViz() {
		StringBuilder ret = new StringBuilder();
		ret.append(this.name);
		ret.append(" [label = \"{");
		if (this.isInterface) {
			ret.append("<<interface>>");
			ret.append("\\n");
		}
		ret.append(this.name);
		ret.append("|");
		for (IField curField : this.fields) {
			ret.append(curField.getGraphViz());
		}
		ret.append("|");
		for (IMethod curMethod : this.methods) {
			ret.append(curMethod.getGraphViz());
		}
		ret.append("}\"]");
		return ret.toString();
	}

}
