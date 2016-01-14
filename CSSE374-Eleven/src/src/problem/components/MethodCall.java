package src.problem.components;

public class MethodCall implements IMethodCall {

	// private String sourceClass;
	// private String sourceMethod;
	//
	// private String destClass;
	// private String destMethod;

	private IMethod sourceMethod;
	private IMethod destMethod;

	// private ArrayList<String> destParameters;
	// private ArrayList<String> sourceParameters;

	public MethodCall(String sourceClass, String sourceMethodName, String destClass, String destMethod) {
		this.sourceMethod = new Method();
		this.destMethod = new Method();
		this.setSourceClass(sourceClass);
		this.setSourceMethod(sourceMethodName);
		this.setDestinationClass(destClass);
		this.setDestinationMethod(destMethod);
		// this.destParameters = new ArrayList<>();
		// this.sourceParameters = new ArrayList<>();
	}
	
	public MethodCall(IMethod sourceMethod, String destClass, String destMethod) {
		this.sourceMethod = new Method();
		this.destMethod = new Method();
		this.setSourceMethod(sourceMethod);
		this.setDestinationClass(destClass);
		this.setDestinationMethod(destMethod);
		// this.destParameters = new ArrayList<>();
		// this.sourceParameters = new ArrayList<>();
	}

	// @Override
	// public String getSourceClass() {
	// return this.sourceClass;
	// }

	@Override
	public IMethod getSourceMethod() {
		return this.sourceMethod;
	}

	// @Override
	// public String getDestinationClass() {
	// return this.destClass;
	// }
	//
	// @Override
	// public String getDestinationMethod() {
	// return this.destMethod;
	// }
	//
	// @Override
	// public List<String> getDestParameters() {
	// return this.destParameters;
	// }

	@Override
	public void setSourceClass(String clazz) {
		this.sourceMethod.setOwner(clazz);
	}

	@Override
	public void setSourceMethod(String method) {
		this.sourceMethod.setName(method);
	}

	@Override
	public void setDestinationClass(String clazz) {
		this.destMethod.setOwner(clazz);
	}

	@Override
	public void setDestinationMethod(String method) {
		this.destMethod.setName(method);
	}

	@Override
	public void addDestParameter(String p) {
		Parameter pp = new Parameter();
		pp.setType(p);
		this.destMethod.addParameter(pp);

	}

	// @Override
	// public List<String> getSourceParameters() {
	// return this.sourceParameters;
	// }

	@Override
	public void addSourceParameter(String p) {
		Parameter pp = new Parameter();
		pp.setType(p);
		this.sourceMethod.addParameter(pp);
	}

	@Override
	public IMethod getDestMethod() {
		return this.destMethod;
	}
	
	public void setSourceMethod(IMethod sourceMethod) {
		this.sourceMethod = sourceMethod;
	}

	public void setDestMethod(IMethod destMethod) {
		this.destMethod = destMethod;
	}

	@Override
	public void setDestReturnType(String t) {
		this.destMethod.setReturnType(t);
		
	}

}
