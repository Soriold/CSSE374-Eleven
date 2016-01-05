package src.problem.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllClasses implements IGraphVizComponent {
	private List<IClass> classes;

	public AllClasses() {
		this.classes = new ArrayList<>();
	}

	public void addClass(IClass clazz) {
		this.classes.add(clazz);
	}

	public List<IClass> getClasses() {
		return this.classes;
	}

	@Override
	public String getGraphViz() {
		StringBuilder ret = new StringBuilder();
		ret.append("digraph G {fontname = \"Bitstream Vera Sans\" fontsize = 8\nnode [fontname ="
				+ "\"Bitstream Vera Sans\" fontsize = 8 shape = \"record\"] edge [fontname = "
				+ "\"Bitstream Vera Sans\" fontsize = 8]");
		for(IClass clazz : this.classes) {
			ret.append(clazz.getGraphViz());
		}
		return null;
	}
}
