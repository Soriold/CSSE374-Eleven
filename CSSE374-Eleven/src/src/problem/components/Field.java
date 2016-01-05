package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class Field implements IField, IGraphVizComponent {

	private String name;
	private String type;
	private String visibility;
	private List<String> modifiers;
	
	public Field() {
		this.modifiers = new ArrayList<String>();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Override
	public void addModifier(String modifier) {
		this.modifiers.add(modifier);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getVisibility() {
		return visibility;
	}

	@Override
	public List<String> getModifiers() {
		return modifiers;
	}

	@Override
	public String getGraphViz() {
		// TODO Auto-generated method stub
		return null;
	}

}
