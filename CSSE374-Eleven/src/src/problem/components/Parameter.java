package src.problem.components;

public class Parameter implements IParameter{
	
	private String name;
	private String type;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

}
