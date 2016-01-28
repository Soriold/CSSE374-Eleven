package src.problem.components;

import java.util.List;

import src.problem.outputvisitor.ITraverser;

public interface IClass extends ITraverser{

	public String getName();

	public void setName(String name);

	public List<IField> getFields();

	public void addField(IField field);

	public List<IMethod> getMethods();

	public void addMethod(IMethod method);

	public List<String> getInterfaces();

	public void addInterface(String impl);

	public String getSuperClass();

	public void setSuperClass(String superClass);

	public boolean getIsInterface();

	public void setIsInterface(boolean isInterface);

	public void setStereotype(String string);
	
	public String getStereotype();
	
	public void setPattern(PatternType pattern);
	
	public PatternType getPattern();

}
