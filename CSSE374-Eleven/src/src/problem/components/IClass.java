package src.problem.components;

import java.util.List;

public interface IClass {
	
	public String getName();
	public void setName(String name);
	public List<IField> getFields();
	public void addField(IField field);
	public List<IMethod> getMethods();
	public void addMethod(IMethod method);

}
