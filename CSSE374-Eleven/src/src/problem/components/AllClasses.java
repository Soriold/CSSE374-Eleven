package src.problem.components;

import java.util.HashMap;
import java.util.Map;

public class AllClasses implements IGraphVizComponent {
	private Map<String, IClass> classes;

	public AllClasses() {
		this.classes = new HashMap<>();
	}

	public void addClass(IClass clazz) {
		this.classes.put(clazz.getName(), clazz);
	}

	public Map<String, IClass> getClasses() {
		return this.classes;
	}

	@Override
	public String getGraphViz() {
		// TODO Auto-generated method stub
		return null;
	}
}
