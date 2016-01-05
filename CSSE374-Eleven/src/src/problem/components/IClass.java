package src.problem.components;

import java.util.List;

public interface IClass extends IGraphVizComponent {

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

}
