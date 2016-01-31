package src.problem.components;

public class ClassDecorator extends Class {
	
	private IClass clazz;
	private PatternType pattern;
	private String stereotype;
	
	public ClassDecorator(IClass clazz) {
		this.clazz = clazz;
		this.pattern = PatternType.NONE;
		this.stereotype = "";
	}
	
	public PatternType getPattern() {
		return this.pattern;
	}

	public void setStereotype(String string) {
		this.stereotype = string;
	}

	public String getStereotype() {
		return this.stereotype;
	}

	public void setPattern(PatternType pattern) {
		this.pattern = pattern;
	}
}
