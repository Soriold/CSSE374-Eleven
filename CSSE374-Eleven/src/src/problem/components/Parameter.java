package src.problem.components;

public class Parameter implements IParameter, IGraphVizComponent {

	private String type;

	public Parameter() {

	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getGraphViz() {
		return this.getType();
	}

}
