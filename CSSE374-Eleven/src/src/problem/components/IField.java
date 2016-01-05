package src.problem.components;

import java.util.List;

public interface IField {
	public String getName();
	public String getType();
	public String getVisibility();
	public List<String> getModifiers();
}
