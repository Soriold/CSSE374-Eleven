package src.problem.components;

import java.util.ArrayList;
import java.util.List;

import src.problem.outputvisitor.IVisitor;

public class Field implements IField {

	private String name;
	private String type;
	private String visibility;
	private List<String> modifiers;
	private boolean hasGenericType;
	private String genericType;
	
	public Field() {
		this.modifiers = new ArrayList<String>();
		this.hasGenericType= false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getVisibility() {
		return visibility;
	}

	@Override
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public List<String> getModifiers() {
		return modifiers;
	}

	@Override
	public void addModifier(String modifier) {
		this.modifiers.add(modifier);
	}
	
	@Override
	public String getGenericType() {
		return this.genericType;
	}

	@Override
	public void setGenericType(String type) {
		this.genericType = type;
		
	}

	@Override
	public boolean hasGenericType() {
		return this.hasGenericType;
	}

	@Override
	public void setHasGenericType(boolean b) {
		this.hasGenericType = b;
	}

//	@Override
//	public String getGraphViz() {
//		StringBuilder ret = new StringBuilder();
//		
//		if (this.visibility.equals("public")) {
//			ret.append("+ ");
//		} else if (this.visibility.equals("private")) {
//			ret.append("- ");
//		} else if (this.visibility.equals("protected")) {
//			ret.append("# ");
//		}
//		
//		ret.append(this.name);
//		ret.append(" : ");
//		ret.append(this.type);
//		ret.append("\\l");
//		
//		return ret.toString();
//	}

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

}
