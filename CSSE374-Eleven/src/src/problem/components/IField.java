package src.problem.components;

import java.util.List;

public interface IField {
	public String getName();
	public void setName(String name);
	public String getType();
	public void setType(String type);
	public String getVisibility();
	public void setVisibility(String visibility);
	public List<String> getModifiers();
	public void addModifier(String modifier);
}
